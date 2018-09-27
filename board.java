import javax.lang.model.util.ElementScanner6;

class board {
    public int h = 22, w = 22, score;
    public char a[][];
    public int lines[], pre[][];

    void boundries() {
        a = new char[h][w];
        lines = new int[h - 1];
        score = 0;
        pre = new int[4][2];
        int i, j;
        for (i = 0; i < h; i++) {
            for (j = 0; j < w; j++) {
                if (i == h - 1)
                    a[i][j] = '-';
                else if (j == w - 1 || j == 0)
                    a[i][j] = '|';
                else
                    a[i][j] = ' ';
            }
        }
    }

    void clean_board(int s[][]) {
        int i, j;
        for (i = 0; i < h; i++) {
            for (j = 0; j < w; j++) {
                if (a[i][j] == '#') {
                    int k;
                    for (k = 0; k < 4; k++) {
                        if (i == s[k][0] && j == s[k][1]) {
                            break;
                        }
                    }
                    if (k == 4) {
                        a[i][j] = ' ';
                    }
                } else if (a[i][j] == '*' || a[i][j] == '|' || a[i][j] == '-') {

                } else {
                    a[i][j] = ' ';
                }
            }
        }
    }

    void shift_down(int row) {
        int i, j = 1;
        // System.out.println(row + "*");

        for (i = row; i != 0; i--) {
            for (j = 1; j < w - 1; j++) {
                // System.out.println(" " + i + " " + j);
                a[i][j] = a[i - 1][j];
                if (i == row && a[i][j] == '*') {
                    lines[row]++;
                }
            }

        }
    }

    int set_shape(int s[][], String move) {
        int i, j, flag = 0, d = 0;
        if (!move.equals("")) {
            for (i = 0; i < 4; i++) {
                a[pre[i][0]][pre[i][1]] = ' ';
            }
        }
        /*
         * for (i = 0; i < 4; i++) { if (move.equals("a")) { a[s[i][0]][s[i][1] + 1] = '
         * '; } else if (move.equals("d")) { a[s[i][0]][s[i][1] - 1] = ' '; } else if
         * (move.equals("s") && s[i][0] != 0) { a[s[i][0] - 1][s[i][1]] = ' '; } }
         */
        for (i = 0; i < 4; i++) {
            if (s[i][1] < 0 || s[i][0] < 0 || a[s[i][0]][s[i][1]] == '|' || a[s[i][0]][s[i][1]] == '*') {
                return 2;
            }
        }
        for (i = 0; i < 4; i++) {
            if (a[s[i][0] + 1][s[i][1]] == '-' || a[s[i][0] + 1][s[i][1]] == '*') {
                // System.out.println(s[i][0] + " " + s[i][1]);
                i = 0;
                while (i < 4) {
                    a[s[i][0]][s[i][1]] = '*';
                    lines[s[i][0]]++;
                    // System.out.println(s[i][0] + " " + s[i][1] + " " + lines[s[i][0]]);
                    if (s[i][0] == 4) {
                        return 3;
                    }
                    if (lines[s[i][0]] == 20) {
                        flag = 1;
                        d = i;
                    }
                    i++;
                }
                if (flag == 1) {
                    // print_board();
                    j = 1;
                    while (j < w - 1) {
                        a[s[d][0]][j] = ' ';
                        j++;
                    }
                    System.out.println(lines[s[d][0]]);
                    lines[s[d][0]] = 0;
                    shift_down(s[d][0]);
                    score++;
                }
                return 1;
            }

        }
        for (i = 0; i < 4; i++) {

            a[s[i][0]][s[i][1]] = '#';
            pre[i][0] = s[i][0];
            pre[i][1] = s[i][1];
        }

        return 0;
    }

    void print_board() {

        int i, j;
        for (i = 0; i < h; i++) {
            for (j = 0; j < w; j++) {
                if (a[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }
}