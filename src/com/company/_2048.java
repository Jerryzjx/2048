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
        User_Access();
        String filename = "C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt";
        Read_user(filename);

    }
    public static void User_Access() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to 2048 game, please type in your username below");
        boolean flag_user = false;
        while (flag_user == false) {
            try {
                user_name = sc.next();
                flag_user = true;
            System.out.println(user_name + " Welcome to the 2048 game");

            } catch (InputMismatchException e) {
                System.out.println("Please input a string");
            }
        }
    }
    public static void Read_user (String filename) throws IOException {

        FileReader fr = new FileReader("C:\\Users\\Leonard\\IdeaProjects\\2048\\src\\com\\company\\users.txt");
        BufferedReader in = new BufferedReader(fr);
        String line = in.readLine();
        int i = 0;
        String[] users = new String[20];
        while(line != null){
            users[i] = String.valueOf(line.split(" "));
            if(users[i].equals(user_name)){
                System.out.println("Username exists!");
                break;
            }
        }
    }
}
