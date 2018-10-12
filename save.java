import java.io.*;
import java.util.Scanner;

class save {
    void savetofile(char board[][], String dir, int xh, int yh) {
        try {
            FileWriter fw = new FileWriter("saveboard.txt");
            int i, j;
            for (i = 0; i < 22; i++) {
                for (j = 0; j < 22; j++) {
                    if (i == 21 || j == 0 || j == 21) {

                    } else {
                        fw.write(board[i][j]);
                    }
                }
            }
            fw.close();
            fw = new FileWriter("saveshape.txt");
            StringBuffer str = new StringBuffer();
            str.append(dir + " " + xh + " " + yh);
            String s = str.toString();
            fw.write(s);
            fw.close();
        } catch (IOException p) {

        }
    }

    String getfromfile(char board[][]) {
        String str = new String();
        try {
            Scanner sc = new Scanner(new File("saveboard.txt"));
            str = sc.nextLine();
            int i, j, k = 0;
            for (i = 0; i < 22; i++) {
                for (j = 0; j < 22; j++) {
                    if (i == 21 || j == 0 || j == 21) {

                    } else {
                        board[i][j] = str.charAt(k++);
                    }
                }
            }
            // System.out.print(str);
            sc.close();
            sc = new Scanner(new File("saveshape.txt"));
            str = new String();
            str = sc.nextLine();
            sc.close();
            return str;
        } catch (FileNotFoundException e) {

        }
        return str;
    }
}