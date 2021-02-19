package com.company;

import java.io.*;
import java.util.*;

// Name: Jerry Zhang
// Date: Feb.10 2021 -
// Project name: 2048 with file io
// purpose: make an unbreakable 2048 game!
public class _2048 {
    static String user_name = "";
    public static void main(String[] args) throws IOException {
        int[][] grid = new int [4][4];
        User_Access();
        String filename = "C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt";
        ArrayList<String> users = Read_user(filename);
        for (int i = 0; i < users.size(); i++) {
            if (user_name.equals(users.get(i))) {
                System.out.println("Username exists");
            } else {
                System.out.println("New User Added");
                new_user(filename);
            }
            break;
        }
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

        FileReader fr = new FileReader("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt");
        BufferedReader in = new BufferedReader(fr);
        String line = in.readLine();
        int i = 0;
       ArrayList<String> userList;
       userList = new ArrayList<>();
        while(line != null && i<i+1 && i<25){
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

    public static void check_user_file_exist() throws IOException {
        Scanner sc = new Scanner(System.in);
        FileReader uf = new FileReader("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\grid.txt");
        BufferedReader in = new BufferedReader(uf);
        String line = in.readLine();
        int i = 0;
        ArrayList<String> userGrid;
        userGrid = new ArrayList<>();
        while(line != null && i<i+1 && i<25) {
            if (userGrid.get(i).contains(user_name)) {
                System.out.println("Previous saved grid found. \n Would you like to contiune from previous saved grid? " +
                        "\n Yes to contiune, No if you want to start again");
                boolean flag_cont = false;
                while (!flag_cont) {
                    try {
                        String start_from_saved = sc.next();
                        if (!start_from_saved.equals("Yes")||!start_from_saved.equals("No")){
                            Exception UserinputIsInvalid = new Exception();
                        }
                        flag_cont = true;
                        System.out.println("Loading saved game...");

                    } catch (InputMismatchException e) {
                        System.out.println("Please input a string");
                    }
                    catch (Exception UserinputException){
                        System.out.println("Please input 'Yes' to load from saved game, 'No' to start from a new grid");
                    }
                }
                i++;
            }
        }
    }
    public static void write_highest_score(){

    }
    public static void save_highest_score(){

    }
}
