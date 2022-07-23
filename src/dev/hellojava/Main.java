package dev.hellojava;

import java.util.Scanner;

public class Main {

    public static int sizeX = 10;
    public static int sizeY = 12;
    public static int amountOfEnemies = 10; //кол-во чужих на поле
    public static int transistorsNeeded = 100;
    public static int moves = 40;
    public static int getAmountOfFlowers = 15; //кол-во цветков на поле по умолчанию

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int command;

        do{
            System.out.println("\nWelcome to BigJavaGame v 1.0. Please make your choice; \n");
            System.out.println("1: Start new game");
            System.out.println("2: Options");
            System.out.println("3: Credits");
            System.out.println("4: Exit");

            command = scan.nextInt();

            switch (command){
                case 1:
                    startNewGame();
                    break;
                case 2:
                    OptionsMenu.showOptionsMenu();
                    break;
                case 3:
                    showCredit();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Command not recognized, please try again!");



            }

        }
        while(command != 4);


    }

    private static void startNewGame() {

        Game game = new Game(sizeX, sizeY, amountOfEnemies, transistorsNeeded, moves, getAmountOfFlowers);

        game.fillFieldWithEmptyObjects();
        game.startGame();
    }

    private static void openOptionsMenu(){

    }

    private static void showCredit(){
        System.out.println("\nCreated by Denis Yakimenko\n");
    }
}
