
public class data {
    public char board[][];
    public int shape[][];
    public String dir;
    public data start, restart;
    public data next = null;

    void data1(char bd[][], int sd[][], String d) {
        int i, j;
        board = new char[22][22];
        shape = new int[4][2];
        dir = new String(d);
        for (i = 0; i < 4; i++) {
            shape[i][0] = sd[i][0];
            shape[i][1] = sd[i][1];
        }
        for (i = 0; i < 22; i++) {
            for (j = 0; j < 22; j++) {
                board[i][j] = bd[i][j];
            }
        }
    }

    void printll() {
        data start1 = start;
        while (start1 != null) {
            for (int i = 0; i < 4; i++)
                System.out.println(start1.shape[i][0] + " " + start1.shape[i][1]);
            start1 = start1.next;
        }
    }

    void push(char bd[][], int sd[][], String d) {
        data n = new data();
        n.data1(bd, sd, d);
        n.next = null;
        if (start == null) {
            start = n;
        } else {
            data ptr = start;
            while (ptr.next != null) {
                ptr = ptr.next;
            }
            ptr.next = n;
        }
    }

    data pop() {
        data ptr = start, pre = ptr;
        if (start == null) {
            return null;
        }
        if (start.next == null) {
            start = null;
            return ptr;
        } else {
            while (ptr.next != null) {
                pre = ptr;
                ptr = ptr.next;
            }
            pre.next = null;
            // data start1 = pre;
            // for (int i = 0; i < 4; i++)
            // System.out.println(start1.shape[i][0] + "* " + start1.shape[i][1]);
            return pre;
        }
    }
}
