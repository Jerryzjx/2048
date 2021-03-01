package com.company;
import java.io.*;
import java.util.*;
// Name: Jerry Zhang
// Date: Feb.10 2021 - Feb.28 2021
// Project name: _2048 with file io
// purpose: make an unbreakable 2048 game!
public class _2048 {
    public static final String ANSI_RESET = "\u001B[0m"; //from      https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    static String user_name = "";
    static int userline = 0;
    static int[][] grid = new int[4][4];
    static int tileint[] = {0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
    static int score = 0;

    public static void main(String[] args) throws IOException {
        User_Access();
        String filename = "C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt";
        ArrayList<String> users = Read_user(filename);
        String gridfile = "C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\" + (user_name) + "_grid.txt";
        for (int i = 0; i < users.size(); i++) {
            if (user_name.equals(users.get(i))) {
                System.out.println("Username exists");
                break;
            } else if (i == users.size() - 1) {
                System.out.println("New User Added");
                new_user(filename);
                Random_Tile();
            }
        }
        score = read_user_score(grid,gridfile);
        startGame(grid = check_user_file_exist(gridfile), gridfile);
    }
    public static void User_Access() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to 2048 game, please type in your username below");
        boolean flag_user = false;
        while (!flag_user) {
            try {
                user_name = sc.next();
                flag_user = true;
                System.out.println(user_name + " Welcome to the 2048 game");

            } catch (InputMismatchException e) {
                System.out.println("Please input a string");
            }
        }
    }
    public static ArrayList<String> Read_user(String filename) throws IOException {
        try {
            File users = new File("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt");
            users.createNewFile();
        } catch (IOException FileNotFoundException) {

        }
        FileReader fr = new FileReader("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt");
        BufferedReader in = new BufferedReader(fr);


        String line = in.readLine();

        int i = 0;
        ArrayList<String> userList;
        userList = new ArrayList<>();
        while (line != null && i < i + 1 && i < 25) {
            userList.add(in.readLine());
            //System.out.println(userList.get(i));
            i++;
        }
        in.close();
        fr.close();
        return userList;

    }

    public static void new_user(String filename) throws IOException {

        BufferedWriter output = new BufferedWriter(new FileWriter("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt", true));
        output.append("\n").append(user_name);
        output.close();
    }

    public static int[][] check_user_file_exist(String userfile) throws IOException {
        try {
            File users = new File(userfile);
            users.createNewFile();
        } catch (IOException FileNotFoundException) {

        }
        Scanner sc = new Scanner(System.in);
        FileReader uf = new FileReader(userfile);
        BufferedReader in = new BufferedReader(uf);
        String line = in.readLine();
        int i = 0;
        ArrayList<String> userGrid;
        userGrid = new ArrayList<>();
        while (line != null && i < i + 1 && i < 25) {
            if (line.equals(user_name)) {
                System.out.println("Previous saved grid found. \n Would you like to contiune from previous saved grid? " +
                        "\n Yes to contiune, No if you want to start again");
                boolean flag_cont = false;
                while (!flag_cont) {
                    try {
                        String start_from_saved = sc.next();
                        if (!start_from_saved.equals("Yes") && !start_from_saved.equals("No")) {
                            Exception UserinputIsInvalid = new Exception();
                            throw UserinputIsInvalid;
                        } else if (start_from_saved.equals("No")) {
                            //sc.close();
                            Random_Tile();
                            flag_cont = true;
                            break;
                        } else {
                            //  sc.close();
                            flag_cont = true;
                            userline = i;
                            read_user_grid(i, grid, userfile);
                            score = read_user_score(grid,userfile);
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please input a string");
                    } catch (Exception UserinputIsInvalid) {
                        System.out.println("Please input 'Yes' to load from saved game, 'No' to start from a new grid");
                    }
                }
                break;
            }
            i++;
        }
        uf.close();
        in.close();
        return grid;
    }

    public static void read_user_grid(int i, int[][] grid, String userfile) throws IOException {
        FileReader uf = new FileReader(userfile);
        BufferedReader in = new BufferedReader(uf);
        String usergrid = " ";
        System.out.println("Loading saved game...");
        for (int j = 0; j < 2; j++) {
            usergrid = in.readLine();
        }
        //System.out.println(usergrid);
        String[] usrgrid = usergrid.split(" ");
        int count = 0;
        for (int l = 0; l < 4; l++) {

            for (int m = 0; m < 4; m++) {
                usrgrid[count] = usrgrid[count].trim();
                grid[l][m] = Integer.parseInt((usrgrid[count]));
                count++;
            }
            count++;
        }
        in.close();
        uf.close();
    }
    public static int read_user_score(int[][]grid,String userfile) throws IOException {
        try {
            File users = new File(userfile);
            users.createNewFile();
        } catch (IOException FileNotFoundException) {

        }
        FileReader us = new FileReader(userfile);
        BufferedReader in = new BufferedReader(us);
        String userscore = " ";
        System.out.println("Loading score...");
        for (int j = 0; j < 3; j++) {
            userscore = in.readLine();
        }
        if(userscore != null){
            score = Integer.parseInt(userscore);
        }
        else{
            score = 0;
        }
        return score;
    }

    public static int[][] left(int[][] grid) {
        for (int x = 0; x < 4; x++) {
            for (int y = 1; y < 4 && y > -1; y++) {
                int i = x;
                int j = y;
                int a = 0;

                for (int z = 0; j > 0 && z == 0; j--) {
                    if (grid[i][j - 1] == tileint[0] && grid[i][j] != tileint[0]) {
                        grid[i][j - 1] = grid[i][j];
                        grid[i][j] = tileint[0];
                    } else if (grid[i][j - 1] == grid[i][j] && grid[i][j] != tileint[0]) {
                        a = Check_index(tileint, grid[i][j]);
                        score = score + tileint[a + 1];
                        grid[i][j - 1] = tileint[a + 1];
                        grid[i][j] = tileint[0];
                        z = 1;
                    } else if (grid[i][j - 1] != tileint[0] && grid[i][j] != grid[i][j - 1] && grid[i][j] != tileint[0]) {
                        z = 1;
                    } else if (grid[i][j] == tileint[0]) {
                        z = 1;
                    }
                }
            }
        }
        return grid;
    }

    public static int[][] right(int[][] grid) {
        for (int x = 0; x < 4; x++) {
            for (int y = 2; y < 3 && y > -1; y--) {
                int i = x;
                int j = y;
                int a = 0;
                //loop for check back
                for (int z = 0; j > -1 && z == 0 && j < 3; j++) {
                    if (grid[i][j + 1] == tileint[0] && grid[i][j] != tileint[0]) {
                        grid[i][j + 1] = grid[i][j];
                        grid[i][j] = tileint[0];
                    } else if (grid[i][j + 1] == grid[i][j] && grid[i][j] != tileint[0]) {
                        a = Check_index(tileint, grid[i][j]);
                        score = score + tileint[a + 1];
                        grid[i][j + 1] = tileint[a + 1];
                        grid[i][j] = tileint[0];
                        z = 1;
                    } else if (grid[i][j + 1] != tileint[0] && grid[i][j] != grid[i][j + 1] && grid[i][j] != tileint[0]) {
                        z = 1;
                    } else if (grid[i][j] == tileint[0]) {
                        z = 1;
                    }
                }
            }
        }
        return grid;
    }

    public static int[][] up(int[][] grid) {
        for (int y = 0; y < 4; y++) {
            for (int x = 1; x < 4 && x > -1; x++) {
                int i = x;
                int j = y;
                int a = 0;
                //loop for check back
                for (int z = 0; i > 0 && z == 0; i--) {
                    if (grid[i - 1][j] == tileint[0] && grid[i][j] != tileint[0]) {
                        grid[i - 1][j] = grid[i][j];
                        grid[i][j] = tileint[0];
                    } else if (grid[i - 1][j] == grid[i][j] && grid[i][j] != tileint[0]) {
                        a = Check_index(tileint, grid[i][j]);
                        score = score + tileint[a + 1];
                        grid[i - 1][j] = tileint[a + 1];
                        grid[i][j] = tileint[0];
                        z = 1;
                    } else if (grid[i - 1][j] != tileint[0] && grid[i][j] != grid[i - 1][j] && grid[i][j] != tileint[0]) {
                        z = 1;
                    } else if (grid[i][j] == tileint[0]) {
                        z = 1;
                    }
                }
            }
        }
        return grid;
    }

    public static int[][] down(int[][] grid) {
        for (int y = 3; y > -1; y--) {
            for (int x = 2; x > -1; x--) {
                int i = x;
                int j = y;
                int a = 0;
                //loop for check back
                for (int z = 0; i < 3 && z == 0; i++) {
                    if (grid[i + 1][j] == tileint[0] && grid[i][j] != tileint[0]) {
                        grid[i + 1][j] = grid[i][j];
                        grid[i][j] = tileint[0];
                    } else if (grid[i + 1][j] == grid[i][j] && grid[i][j] != tileint[0]) {
                        a = Check_index(tileint, grid[i][j]);
                        score = score + tileint[a + 1];
                        grid[i + 1][j] = tileint[a + 1];
                        grid[i][j] = tileint[0];
                        z = 1;
                    } else if (grid[i + 1][j] != tileint[0] && grid[i][j] != grid[i + 1][j] && grid[i][j] != tileint[0]) {
                        z = 1;
                    } else if (grid[i][j] == tileint[0]) {
                        z = 1;
                    }
                }
            }
        }
        return grid;
    }

    public static int Check_index(int[] tileint, int element) {
        for (int i = 0; i < tileint.length; i++) {
            if (tileint[i] == element) {
                return i;
            }
        }
        return 0;
    }

    public static int[][] move(int[][] grid, int direction) {
        if (direction == 0) {
            grid = left(grid);

        } else if (direction == 1) {
            grid = down(grid);
        } else if (direction == 2) {
            grid = right(grid);
        } else if (direction == 3) {
            grid = up(grid);
        }
        return grid;
    }

    public static void Random_Tile() {
        if (!check_Full(grid)) {
            Random it = new Random();
            Random jt = new Random();
            int i = it.nextInt(4);
            int j = jt.nextInt(4);
            while (grid[i][j] != 0) {
                i = it.nextInt(4);
                j = jt.nextInt(4);
            }
            int num = new Random().nextInt(2) + 4;
            if (num % 2 == 0) {
                grid[i][j] = 2;
            } else {
                grid[i][j] = 4;
            }
        } else {

        }
    }

    public static boolean check_Win(int[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 2048) {
                    System.out.println("Congratulations! You have won the game!");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean check_Full(int[][] grid) {
        boolean avaliable_space = true;
        boolean merge_col = true;
        boolean merge_row = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 0) {
                    avaliable_space = false;
                    break;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == grid[i][j + 1]) {
                    merge_col = false;
                    break;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == grid[i + 1][j]) {
                    merge_row = false;
                    break;
                }
            }
        }
        if (avaliable_space && merge_col && merge_row == true) {
            return true;
        }
        return false;
    }

    public static void print_grid(int[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] > 10 && grid[i][j] < 100) {
                    System.out.print("  " + grid[i][j] + "   ");
                } else if (grid[i][j] > 100 && grid[i][j] < 1000) {
                    System.out.print(" " + grid[i][j] + " ");
                } else if (grid[i][j] > 1000) {
                    System.out.print(grid[i][j] + "   ");
                } else {
                    System.out.print("   " + grid[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }

    public static void load_leaderboard() {

    }

    public static int Direction(String key) {
        switch (key) {
            case "A": {
                return 0;
            }
            case "S": {
                return 1;
            }
            case "D": {
                return 2;
            }
            case "W": {
                return 3;
            }
            case "X": {
                return 4;
            }
            default:
                return 4;
        }
    }

    public static void startGame(int[][] grid, String userfile) throws IOException {
        Scanner key = new Scanner(System.in);
        System.out.println("Welcome to the 2048 game with file.io \n Please select a color for the gameboard: \n " +
                "0 for default, 1 for Cyan");

        String user_key = "X";
        do {
            print_grid(grid);
            System.out.println("Current Score:" + score);
            System.out.println("Use A for left, W for up, S for down and D for right; press X to quit the game");
            boolean flag = false;
            while (!flag) {
                if (key.hasNext()) {
                    try {
                        user_key = key.next().toUpperCase();

                        if (user_key.equals(null) || !user_key.equals("A") && !user_key.equals("W") && !user_key.equals("S") && !user_key.equals("D") && !user_key.equals("X")) {
                            Exception userinputisinvalid = new Exception();
                            throw userinputisinvalid;
                        }
                        flag = true;
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please input a string");
                    } catch (Exception userinputisinvalid) {
                        System.out.println("Use A for left, W for up, S for down and D for right; press X to quit the game");
                    }
                }
            }
            int direction = Direction(user_key);
            grid = move(grid, direction);
            Random_Tile();
            check_Win(grid);
            if (check_Full(grid) || direction == 4) {
                check_Win(grid);
                System.out.println("Game Over :(");
                System.out.println("Score :" + score);
                save_user_grid(userline, grid, userfile,score);
                save_score(score);
                break;
            }

        } while (!check_Full(grid));

    }

    public static void save_score(int score) throws IOException {
        String user_score = Integer.toString(score);
        BufferedWriter output = new BufferedWriter(new FileWriter("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\scores.txt", true));
        output.append("\n").append(user_name);
        output.append("\n").append(user_score);
        output.close();
    }

    public static void save_user_grid(int i, int[][] grid, String userfile, int score) throws IOException {
        FileWriter uf = new FileWriter(userfile);
        BufferedWriter in = new BufferedWriter(uf);

        System.out.println("Saving game...");

        //System.out.println(usergrid);
        String[] usrgrid = new String[16];
        int count = 0;
        StringBuffer sb = new StringBuffer();
        for (int l = 0; l < 4; l++) {

            for (int m = 0; m < 4; m++) {
                if (count < 16) {
                    sb.append(grid[l][m] + " ");
                    count++;
                }

            }
            count++;
        }
        String usergrid = sb.toString();
        String userscore = Integer.toString(score);
        in.write(user_name);
        in.newLine();
        in.write(usergrid);
        in.newLine();
        in.write(userscore);
        in.close();
        uf.close();
    }
}