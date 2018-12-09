import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

class load {
    Scanner sc;
    String move;
    board myboard;
    shapes active;
    int check_move = 0, flag_reverse = 0;
    data start, shift, undo;
    save myfile;

    // Declarations in constructor and then created different function
    // for different things
    load() {
        myfile = new save();
        sc = new Scanner(System.in);
        move = new String();
        myboard = new board();
        active = new shapes();
        check_move = 0;
        start = new data();
        shift = new data();
        undo = new data();
        System.out.println("\t\t=>TETRIS GAME<=\n\ts+enter=PLAY ZONE r+enter=resume saved\n");
        // intial generation of block
        myboard.boundries();
        myboard.print_board();
        move = sc.next();
        if (move.equals("r")) {
            String str = myfile.getfromfile(myboard.a);
            // System.out.print(str);
            String[] arrOfStr = str.split(" ");
            active.dir = new String(arrOfStr[0]);
            active.x = Integer.parseInt(arrOfStr[1]);
            active.y = Integer.parseInt(arrOfStr[2]);
            // System.out.println(active.dir + " " + active.x + " " + active.y);
            active.shape_maker();
        } else {
            active.generate();
        }
        myboard.set_shape(active.a, move);
        myboard.print_board();
        System.out.println("SCORE = " + myboard.score);
        flag_reverse = 0;
        start.push(myboard.a, active.a, active.dir, myboard.lines);
    }

    void move_maker() {
        if (move.equals("z")) {
            undo = new data();
            while (move.equals("z") || move.equals("x")) {
                if (move.equals("z")) {
                    shift = start.pop();
                }
                if (move.equals("x")) {
                    shift = undo.pop();
                }
                if (shift != null && move.equals("z")) {
                    copy(shift.shape, active.a);// keep a copy of dir also
                    copy1(shift.board, myboard.a);
                    copy2(shift.lines, myboard.lines);
                    active.dir = new String(shift.dir);
                    undo.push(myboard.a, active.a, active.dir, myboard.lines);
                }
                if (shift != null && move.equals("x")) {
                    copy(shift.shape, active.a);// keep a copy of dir also
                    copy1(shift.board, myboard.a);
                    copy2(shift.lines, myboard.lines);
                    active.dir = new String(shift.dir);
                    start.push(myboard.a, active.a, active.dir, myboard.lines);
                }
                System.out.print("\033[H\033[2J");
                System.out.println(
                        "\t\t=>TETRIS GAME<=\n\n\t\t->UNDO-REDO ZONE<-\npress z+enter=undo x+enter=redo any_other_key+enter=exit\n");
                myboard.print_board();
                System.out.println("SCORE = " + myboard.score + "\nenter your move");
                move = sc.next();
                flag_reverse = 1;
            }
        }
        if (move.equals("u") || move.equals("i")) {
            active.rotate(move);
        } else
            active.move(move);
        check_move = myboard.set_shape(active.a, move);
        if (flag_reverse == 1) {
            copy(myboard.pre, active.a);
            myboard.set_shape(active.a, "");
        }
        if (check_move == 1) {
            active.generate();
            myboard.set_shape(active.a, "");
        } else if (check_move == 2) {
            copy(myboard.pre, active.a);
            if (move.equals("u") || move.equals("i"))
                active.dir = new String(active.pre_dir);
            myboard.set_shape(active.a, "");
        }
        // push and print together
        if (!move.equals("z"))
            start.push(myboard.a, active.a, active.dir, myboard.lines);
        if (flag_reverse == 1) {
            myboard.clean_board(active.a);
            System.out.print("\033[H\033[2J");
        }
        System.out.println(
                "\t\t=>TETRIS GAME<=\n\n\t\t->PLAY ZONE<-\nd=left a=right s=down u=clockwise i=anticlockwise p=save q=exit\n\t\tz=UNDO-REDO ZONE\n");
        myboard.print_board();
    }

    public static void copy(int a[][], int b[][]) {
        for (int i = 0; i < 4; i++) {
            b[i][0] = a[i][0];
            b[i][1] = a[i][1];
        }
    }

    public static void copy1(char a[][], char b[][]) {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                b[i][j] = a[i][j];
            }
        }
    }

    public static void copy2(int a[], int b[]) {
        for (int i = 0; i < 21; i++) {
            b[i] = a[i];
        }
    }

    public static void print_move(int a[][]) {
        for (int i = 0; i < 4; i++) {
            System.out.println(a[i][0] + " " + a[i][1]);
        }
    }

    public static void main(String[] args) {

        load l = new load();
        int fast = 0, flag_s = 0;
        try {
            Game game = new Game();
            while (!l.move.equals("q")) {
                l.flag_reverse = 0;
                System.out.print("\033[H\033[2J");
                // handling of move in one function
                l.move_maker();
                if (l.check_move == 3) {
                    // System.out.print("\033[H\033[2J");
                    flag_s = 2;
                    throw (null);
                }
                System.out.println("SCORE = " + l.myboard.score + "\nenter your move");
                if (l.move.equals("s") && fast == 0) {
                    Thread.sleep(500);
                } else {
                    fast = 0;
                }
                KeyStroke k1 = game.getNonBlockingInput();
                if (k1 == null) {
                    l.move = new String("s");
                    // handle no input
                } else {
                    if (k1.getCharacter() == 'a') {
                        l.move = new String("a");
                    }
                    if (k1.getCharacter() == 's') {
                        l.move = new String("s");
                        l.move_maker();
                        l.move = new String("s");
                        fast = 1;
                    }
                    if (k1.getCharacter() == 'd') {
                        l.move = new String("d");
                    }
                    if (k1.getCharacter() == 'z') {
                        l.move = new String("z");
                    }
                    if (k1.getCharacter() == 'x') {
                        l.move = new String("x");
                    }
                    if (k1.getCharacter() == 'u') {
                        l.move = new String("u");
                    }
                    if (k1.getCharacter() == 'i') {
                        l.move = new String("i");
                    }
                    if (k1.getCharacter() == 'q') {
                        throw (null);
                    }
                    if (k1.getCharacter() == 'p') {
                        l.myfile.savetofile(l.myboard.a, l.active.dir, l.active.a[0][0], l.active.a[0][1]);
                        flag_s = 1;
                        throw (null);
                    }
                }
            }
        } catch (Exception e) {
            if (flag_s == 1)
                System.out.println("Your game has been saved");
            if (flag_s == 2)
                System.out.println("\t\tGAME OVER YOU LOSE\nSCORE = " + l.myboard.score);
            System.out.println("Press any key + enter to exit...");
            l.move = l.sc.next();
        }
        l.sc.close();
    }
}