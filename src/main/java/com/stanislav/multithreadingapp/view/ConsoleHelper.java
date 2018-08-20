package main.java.com.stanislav.multithreadingapp.view;

import main.java.com.stanislav.multithreadingapp.filesparser.DirectoryParser;
import main.java.com.stanislav.multithreadingapp.exceptions.DirectoryIsEmptyException;

import java.io.File;
import java.util.Scanner;

public class ConsoleHelper {
    Scanner scanner = new Scanner(System.in);
    public  void mainMenu(){
        String command = "";
        while (!command.equals("close programm")){
            System.out.println("You are in main menu");
            System.out.println("You need enter path to directory");
            System.out.println("For to close programm enter command \"close programm\"");
            command = scanner.nextLine().toLowerCase().trim();
            if(!command.equals("close programm")){
                File directory = new File(command);
                if(directory.isDirectory() && directory.exists()){
                    try {
                        new DirectoryParser(directory).parseFiles();
                    } catch (DirectoryIsEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else{
                    System.out.println("Entered directory is not exists");
                }
            }
        }

    }
}
