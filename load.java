import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

class load {

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

        // Declarations
        try {
            Game game = new Game();
            System.out.println(
                    "\t\tTETRIS GAME\nd=left a=right s=down u=clockwise i=anticlockwise\n\t\t\tz=undo x=redo\n");

            Scanner sc = new Scanner(System.in);
            String move = new String();
            board myboard = new board();
            shapes active = new shapes();
            int check_move;
            data start = new data(), shift = new data(), undo = new data();
            // intial generation of block
            myboard.boundries();
            active.generate();// random shape generator
            myboard.set_shape(active.a, move);
            myboard.print_board();
            System.out.println("SCORE = " + myboard.score + "\nenter your move");
            move = sc.next();
            int m = 1, flag_reverse = 0;
            start.push(myboard.a, active.a, active.dir, myboard.lines);
            while (!move.equals("q")) {
                flag_reverse = 0;
                // System.out.print("\033[H\033[2J");// CLEAR SCREEN
                System.out.print("\033[H\033[2J");
                // System.out.flush();
                // game.terminal.clearScreen();
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
                // print_move(active.a);
                if (check_move == 1) {
                    active.generate();
                    myboard.set_shape(active.a, "");
                } else if (check_move == 2) {
                    copy(myboard.pre, active.a);
                    if (move.equals("u") || move.equals("i"))
                        active.dir = new String(active.pre_dir);
                    myboard.set_shape(active.a, "");
                }
                // print_move(active.a);
                if (!move.equals("z"))
                    start.push(myboard.a, active.a, active.dir, myboard.lines);
                // start.printll();
                if (flag_reverse == 1) {
                    myboard.clean_board(active.a);
                    System.out.print("\033[H\033[2J");
                }
                myboard.print_board();
                if (check_move == -1) {
                    // System.out.print("\033[H\033[2J");
                    System.out.println("GAME OVER\nSCORE = " + myboard.score);
                    break;
                }
                System.out.println("SCORE = " + myboard.score + "\nenter your move");
                m = 1;
                // if (move.equals("s")) {
                // try {
                // Thread.sleep(500);
                // } catch (InterruptedException e) {
                // }
                // }

                // try {
                // m = System.in.available();
                // } catch (IOException e) {

                // }
                // game = new Game();
                // game.terminal.flush();
                // KeyType key;
                if (move.equals("s")) {
                    // try {
                    Thread.sleep(700);
                    // } catch (InterruptedException e) {
                    // }
                }
                KeyStroke k1 = game.getNonBlockingInput();
                if (k1 == null) {
                    move = new String("s");
                    // handle no input
                } else {
                    if (k1.getCharacter() == 'a') {
                        move = new String("a");
                    }
                    if (k1.getCharacter() == 's') {
                        move = new String("s");
                    }
                    if (k1.getCharacter() == 'd') {
                        move = new String("d");
                    }
                    if (k1.getCharacter() == 'z') {
                        move = new String("z");
                    }
                    if (k1.getCharacter() == 'x') {
                        move = new String("x");
                    }
                    if (k1.getCharacter() == 'u') {
                        move = new String("u");
                    }
                    if (k1.getCharacter() == 'i') {
                        move = new String("i");
                    }
                }
                // System.out.println(check_move + "*" + m);

                // if (m != 0) {
                // move = sc.next();
                // // m=0;
                // }
                // if (m == 0) {
                // move = new String("s");
                // // break;
                // }
                // if (move.equals(""))
                // move = new String("s");
                // if (m != 0) {
            }
            sc.close();
            // game.terminal.stopScreen();
        } catch (Exception e) {

        }
        // Runtime.getRuntime().addShutdownHook(new Thread() {
        // public void run() {
        // try {
        // Game game = new Game();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // System.out.print("\033[H\033[2J");
    }
}