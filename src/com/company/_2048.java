package com.company;
import java.io.*;
import java.util.*;
// Name: Jerry Zhang
// Date: Feb.10 2021 -
// Project name: 2048 with file io
// purpose: make an unbreakable 2048 game!
public class _2048 {
    public static final String ANSI_RESET = "\u001B[0m"; //from      https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    static String user_name = "";
    static int [][] grid = new int [4][4];
    public static void main(String[] args) throws IOException {
        User_Access();
        String filename = "C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt";
        ArrayList<String> users = Read_user(filename);
        for (int i = 0; i < users.size(); i++) {
            if (user_name.equals(users.get(i))) {
                System.out.println("Username exists");
            } else {
                System.out.println("New User Added");
                new_user(filename);
                Random_Start_Grid(grid);
            }
            break;
        }
        grid = check_user_file_exist();
        print_grid(grid);
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
    public static ArrayList <String> Read_user (String filename) throws IOException {
        try {
            File users = new File("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt");
           users.createNewFile();
        }catch(IOException FileNotFoundException ){

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

        return userList;
    }
    public static void new_user (String filename) throws IOException {

        BufferedWriter output = new BufferedWriter(new FileWriter("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt", true));
        output.append("\n").append(user_name);
       output.close();
    }

    public static int[][] check_user_file_exist() throws IOException {
        Scanner sc = new Scanner(System.in);
        FileReader uf = new FileReader("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\grid.txt");
        BufferedReader in = new BufferedReader(uf);
        String line = in.readLine();
        int i = 0;
        ArrayList<String> userGrid;
        userGrid = new ArrayList<>();
        while(line != null && i<i+1 && i<25) {
            if (line.equals(user_name)) {
                System.out.println("Previous saved grid found. \n Would you like to contiune from previous saved grid? " +
                        "\n Yes to contiune, No if you want to start again");
                boolean flag_cont = false;
                while (!flag_cont) {
                    try {
                        String start_from_saved = sc.next();
                        if (!start_from_saved.equals("Yes")&&!start_from_saved.equals("No")){
                           Exception UserinputIsInvalid = new Exception();
                            throw UserinputIsInvalid;
                        }
                        else if(start_from_saved.equals("No")){
                            sc.close();
                            flag_cont = true;
                            Random_Start_Grid(grid);
                            break;
                        }
                        else{
                            sc.close();
                            flag_cont = true;
                            read_user_grid(i,grid);
                            break;
                        }

                    } catch (InputMismatchException e) {
                        System.out.println("Please input a string");
                    }
                    catch ( Exception UserinputIsInvalid){
                        System.out.println("Please input 'Yes' to load from saved game, 'No' to start from a new grid");
                    }
                }
                break;
            }
            i++;
        }
     return grid;
    }
    public static int[][] read_user_grid (int i, int[][] grid) throws IOException {
        FileReader uf = new FileReader("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\grid.txt");
        BufferedReader in = new BufferedReader(uf);
        String usergrid = " ";
        System.out.println("Loading saved game...");
        for(int j=0; j<i+2; j++) {
            usergrid = in.readLine();
        }
        System.out.println(usergrid);
        String[] usrgrid = usergrid.split(" ");
        int count = 0;
            for(int l=0; l<4; l++){

                for(int m =0; m<4;m++) {
                   usrgrid[count] = usrgrid[count].trim();
                    grid[l][m] = Integer.parseInt((usrgrid[count]));
                    count++;
                }
                count++;
            }
        return grid;
    }
    public static int[][] Random_Start_Grid(int [][] grid){
        // 2 tiles were spawned randomly
    Random rand = new Random();
    int init_tile1 = rand.nextInt(7-0)+0;
    int init_tile2 = rand.nextInt(15-8)+8;

        int col =0;
        int row=0;
        if(init_tile1 >=3){
            row = 1;
            col = init_tile1-3;
        }else{
            col= init_tile1;
        }
        grid[row][col]=2;
        if(init_tile2 >=11){
            row = 3;
            col = init_tile2-11;
        }else{
            row = 2;
            col= init_tile2-7;
        }
        grid[row][col]=2;
        return grid;
    }
   // public static int[][] rotate(int[][]grid){

 //   }
  //  public static int[][] move(int[][]grid,int direction){

    //}
   // public static int[][] Random_Tile(){

   // }
    public static void print_grid(int[][]grid){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(grid[i][j]>10 && grid[i][j]<100){
                    System.out.print(" "+grid[i][j]+"   ");
                }else if(grid[i][j]>100 && grid[i][j]<1000){
                    System.out.print(" "+grid[i][j]+"  ");
                }else if(grid[i][j]>1000){
                    System.out.print(grid[i][j]+"  ");
                }else{
                    System.out.print("  "+grid[i][j]+"   ");
                }
            }
            System.out.println();
        }
    }

    public static void write_highest_score(){

    }
   public static void load_leaderboard(){

   }
   public static void startGame(int[][]grid){
        System.out.println("Welcome to the 2048 game with file.io \n Please select a color for the gameboard: \n " +
                "0 for default, 1 for Green, 2 for Cyan");
        System.out.println("How To Play 2048: \n Use A for left, W for up, S for down and D for right; press X to quit the game");
        print_grid(grid);
   }
}
