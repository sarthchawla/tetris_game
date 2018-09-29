
class shapes {
    public String dir;
    public char arr[], pre_dir[];
    public int x;
    public int y;
    public int shape_id;
    public int a[][];

    void shape_maker() {
        a[0][0] = x;
        a[0][1] = y;
        int i = 0, j = 1, flag = 0;
        while (i < dir.length()) {
            flag = 0;
            if (dir.charAt(i) == 's')
                x = x + 1;
            if (dir.charAt(i) == 'w')
                x = x - 1;
            if (dir.charAt(i) == 'a')
                y--;
            if (dir.charAt(i) == 'd')
                y++;
            if (dir.charAt(i) == 'r') {
                i++;
                if (dir.charAt(i) == 's')
                    x = x + 1;
                if (dir.charAt(i) == 'w')
                    x = x - 1;
                if (dir.charAt(i) == 'a')
                    y--;
                if (dir.charAt(i) == 'd')
                    y++;
                flag = 1;
            }
            if (flag == 0) {
                a[j][0] = x;
                a[j][1] = y;
                j++;
            }
            i++;
        }
    }

    void move(String move) {
        int i, x, y;
        for (i = 0; i < 4; i++) {
            x = a[i][0];
            y = a[i][1];
            if (move.equals("a")) {
                y--;
            } else if (move.equals("d")) {
                y++;
            } else if (move.equals("s")) {
                x++;
            }
            a[i][0] = x;
            a[i][1] = y;
        }
    }

    void rotate(String move) {
        if (shape_id == 4)
            return;
        pre_dir = dir.toCharArray();
        arr = dir.toCharArray();
        int i = 0;
        while (i < dir.length()) {
            if (arr[i] == 'd') {
                if (move.equals("u"))
                    arr[i] = 's';
                else
                    arr[i] = 'w';
            } else if (arr[i] == 'a') {
                if (move.equals("u"))
                    arr[i] = 'w';
                else
                    arr[i] = 's';
            } else if (arr[i] == 's') {
                if (move.equals("u"))
                    arr[i] = 'a';
                else
                    arr[i] = 'd';
            } else if (arr[i] == 'w') {
                if (move.equals("u"))
                    arr[i] = 'd';
                else
                    arr[i] = 'a';
            }
            i++;
        }
        dir = new String(arr);
        x = a[0][0];
        y = a[0][1];
        shape_maker();
    }

    // random shape generator
    void generate() {
        shape_id = (int) (Math.random() * ((19 - 1) + 1)) + 1;
        // rotate_id = (int) (Math.random() * ((2 - 1) + 1)) + 1;
        x = 0;
        y = (int) (Math.random() * ((16 - 4) + 1)) + 4;
        a = new int[4][2];
        // shape_id = 19;
        switch (shape_id) {
        case 1:
            dir = new String("sss");// i
            break;
        case 2:
            dir = new String("ssd");// l
            break;
        case 3:
            dir = new String("ssa");// j
            break;
        case 4:
            dir = new String("sdw");// o
            break;
        case 5:
            dir = new String("dsd");// z
            break;
        case 6:
            dir = new String("asa");// s
            break;
        case 7:
            dir = new String("drasrwa");// t
            break;
        case 8:
            dir = new String("ddd");// i
            break;
        case 9:
            dir = new String("aas");// l
            break;
        case 10:
            dir = new String("dds");// j
            break;
        case 11:
            dir = new String("sas");// z
            break;
        case 12:
            dir = new String("sds");// s
            break;
        case 13:
            x++;
            dir = new String("ardwrss");// t
            break;
        case 14:
            dir = new String("dss");// l
            break;
        case 15:
            dir = new String("ass");// j
            break;
        case 16:
            x++;
            dir = new String("drasrww");// t
            break;
        case 17:
            dir = new String("saa");// l
            break;
        case 18:
            dir = new String("sdd");// j
            break;
        case 19:
            x++;
            dir = new String("drawrsa");// t
            break;
        default:
            System.out.println("WRONG shape id");
            break;
        }
        // get_rotation();
        shape_maker();
    }
}