package dev.hellojava;

import java.util.Scanner;

public class OptionsMenu {

   static Scanner scan = new Scanner(System.in);
   static int command;

    public static void showOptionsMenu(){
        do{
            System.out.println("\nSelect make your choice and press Enter\n"+
                    "\n1: Show current settings\n"+
                    "2: Change settings\n"+
                    "3: Exit");
            command = scan.nextInt();
            switch (command){
                case 1:
                    System.out.println("\n Current settings:\n"+
                            "\n rows: "+ Main.sizeX+
                            "\n columns: "+ Main.sizeY+
                            "\n enemies: "+Main.amountOfEnemies+
                            "\n transistors: "+ Main.transistorsNeeded+
                            "\n movies: "+ Main.moves+
                            "\n flowers: "+Main.getAmountOfFlowers);
                    break;
                case 2:
                    System.out.println("Enter a new value for rows: ");
                    Main.sizeX = scan.nextInt();
                    System.out.println("Enter a new values for columns: ");
                    Main.sizeY = scan.nextInt();
                    System.out.println("Enter a new values for enemies: ");
                    Main.amountOfEnemies = scan.nextInt();
                    System.out.println("Enter a new values for transistors: ");
                    Main.transistorsNeeded = scan.nextInt();
                    System.out.println("Enter a new values for movies: ");
                    Main.moves = scan.nextInt();
                    System.out.println("Enter a new values for flowers: ");
                    Main.getAmountOfFlowers = scan.nextInt();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Command not recognized, please try again!");
                    break;
            }
        }
        while(command !=3);
    }
}
