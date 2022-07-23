package dev.hellojava;

//главный игровой класс

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private int sizeX;
    private int sizeY;
    private int amountOfEnemies;
    private int transistorsNeeded;
    private int transistorsGathered; //показывает сколько собрали цветов
    private int turnsLeft; // показывает сколько шагов осталось
    private Field field;
    private boolean isGameFinished=false;
    private int amountOfFlowers;
    private ArrayList<Flower>flowerArrayList = new ArrayList<Flower>(); //в массиве храним все цветки которые добыли
    private ArrayList<Enemy>enemyArrayList = new ArrayList<Enemy>(); //в массиве хранятся все чужие
    private Random randomNumber = new Random();
    Player player;
    private Scanner scanner = new Scanner(System.in);
    private Boolean isIncorrectCommand = true;
    private int triesToRegenerate = 10; //10 раз генерирует ход чужого, если не выходит то переход хода



    //создаем конструктор класса Game

    public Game(int sizeX, int sizeY, int amountOfEnemies, int transistorsNeeded, int turnsLeft,
                int amountOfFlowers) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.amountOfEnemies = amountOfEnemies;
        this.amountOfFlowers = amountOfFlowers;
        this.transistorsNeeded = transistorsNeeded;
        this.turnsLeft = turnsLeft;
        field = new Field(sizeX, sizeY);


    }

    public Field getField(){
        return this.field;
    }

    public ArrayList<Flower>getFlowerArrayList(){
        return this.flowerArrayList;
    }

    //метод прибавляющий транзистор
    public void setTransistorsGathered(int transistorsToAdd){
        this.transistorsGathered += transistorsToAdd;
    }

    //заполняем поле объектами обозначающие пустоту
    public void fillFieldWithEmptyObjects(){
        for (int i = 0; i<sizeX; i++){
            for (int j=0; j<sizeY; j++){
                field.setFieldable(i,j, new Empty());
            }
        }

    }
    //заготовка метода запуска игры
    public void startGame(){

        possesPlayer(); // генерация игрока
        possesEnemies(); //генерация чужих
        possesFlowers(); //начальная генерация цветков

        while (!isGameFinished){

            showField();
            playerTurn();
            if (isIncorrectCommand){
                incorrectCommand();
                continue;
            }
            computerTurn(); //генерирует ходы врагов и проверяет сколько цветков
            checkIfGameNotFinished();
        }

    }
    private void incorrectCommand(){
        System.out.println("\nYou have enter an incorrect command, please verify and try again.\n");
    }

    // генерация игрока
    private void possesPlayer(){
        int playerRowPosition = randomNumber.nextInt(sizeX); // генерируем где находтся цветок
        int playerColumnPosition = randomNumber.nextInt(sizeY);

        player = new Player(playerRowPosition,playerColumnPosition,this);
    }

    // метод генерации Чужих
    private void possesEnemies(){

        generateEnemies();

    }

    private void generateEnemies(){

        //создаем цикл для генерации Чужих
        for (int i = amountOfEnemies - enemyArrayList.size(); i > 0;) {

            int enemyRowPosition = randomNumber.nextInt(sizeX); // генерируем где находтся enemy x
            int enemyColumnPosition = randomNumber.nextInt(sizeY); // генерируем где находтся enemy y


            // если при генерации enemy, попадает на пустое поле, добавили enemy в массив
            //если Чужой попадает на какойто объект, продолжаем генерить Чужого, пока не попадет на пустое поле
            if(field.getFieldable(enemyRowPosition, enemyColumnPosition)
                    instanceof Empty){

                Enemy enemy = new Enemy(enemyRowPosition, enemyColumnPosition);
                field.setFieldable(enemyRowPosition, enemyColumnPosition, enemy);
                enemyArrayList.add(enemy); //добавили enemy в массив
                i--;
            }

        }


    }

    //генерация flowers
    private void possesFlowers(){
        generateFlowers();} //начальная генерация цветков
    private void showField(){
        System.out.println("\n\nTurn left: "+turnsLeft
                        +", transistors gathered: "+ transistorsGathered
                +"/"+ transistorsNeeded);
        field.showField();
    }
    private void playerTurn(){
        System.out.println("Please enter your command and press Enter: ");
        String command = scanner.nextLine();
      isIncorrectCommand = player.makeMove(command);
    }
    private void computerTurn(){

        enemyMove(); //метод для создания движения чужих
        generateFlowers(); //регенирация сорванных цветков

        turnsLeft --;
    }
    private void generateFlowers() {
        //создаем цикл для генерации цветков
        for (int i = amountOfFlowers - flowerArrayList.size(); i > 0;) {
            int flowerAmountOfTransistors = randomNumber.nextInt(9) + 1; // кол во транзисторов в цветке 1-10
            int flowerRowPosition = randomNumber.nextInt(sizeX); // генерируем где находтся цветок
            int flowerColumnPosition = randomNumber.nextInt(sizeY);

            //проверяем, если при генерации, цветок попал на игрока, то прибавляем к текущему счету
            if (field.getFieldable(flowerRowPosition, flowerColumnPosition) instanceof Player){
                transistorsGathered = transistorsGathered + flowerAmountOfTransistors; // + транзисторы к текущему счету
            i--;
            }

            // если при генерации цветка, попадает на пустое поле, добавили цветок в массив
            else if(field.getFieldable(flowerRowPosition, flowerColumnPosition)
                    instanceof Empty){

                Flower flower = new Flower(flowerAmountOfTransistors ,flowerRowPosition, flowerColumnPosition);
                field.setFieldable(flowerRowPosition, flowerColumnPosition, flower);
                flowerArrayList.add(flower); //добавили цветок в массив
                i--;
            }

        }
    }

    private void enemyMove(){
        // начальные значения координат чужих
        int rowIndex = 0;
        int columnIndex = 0;
        int newRowIndex = 0;
        int newColumnIndex = 0;
        int regenerateIndex = 0;
        boolean isNeededToRegenerate = true; //смотрит может ли произвести ход чужой, за 10 регенираций ходов

        //цикл перебора всех чужих из массива
        for (Enemy enemy : enemyArrayList) {
            //получаем новые координаты чужих.
            rowIndex = enemy.getRowIndex();
            columnIndex = enemy.getColumnIndex();


            //цикл смотрящий, может ли произвести ход чужой, за 10 регенираций ходов, для каждого из чужих
            do {
                int deltaRow = randomNumber.nextInt(3)-1; //генерит ход от -1,0,+1 чужого по Row оси
                int deltaColumn = randomNumber.nextInt(3)-1; //генерит ход от -1,0,+1 чужого по Column оси
                newRowIndex = rowIndex + deltaRow;
                newColumnIndex = columnIndex + deltaColumn;

                if((newRowIndex<0) || (newColumnIndex<0) ||(newRowIndex>=field.getSizeX()) ||
                    (newColumnIndex>=field.getSizeY()) || field.getFieldable(newRowIndex, newColumnIndex) instanceof Player ||
                field.getFieldable(newRowIndex, newColumnIndex) instanceof Enemy){

                    regenerateIndex++;
                    isNeededToRegenerate = true;
                    }
                //если ниодно из услоыий из цикла не выполняется новые координаты находятся в пределах поля,
                // и клетка не занята не игроком не чужим.
              else {
                  //проверяем не находится на клетке цветок
                  if(field.getFieldable(newRowIndex, newColumnIndex) instanceof Flower){
                      Flower flower = (Flower)field.getFieldable(newRowIndex, newColumnIndex);
                      //если находится, то удаляем цветок из массива
                      flowerArrayList.remove(flower);

                      isNeededToRegenerate = swapEnemy(rowIndex, columnIndex, newRowIndex, newColumnIndex, enemy);
                  }
                  else{


                      isNeededToRegenerate = swapEnemy(rowIndex, columnIndex, newRowIndex, newColumnIndex, enemy);

                  }

                }

            } while (isNeededToRegenerate && regenerateIndex <= 10);

        }
    }

    private boolean swapEnemy(int rowIndex, int columnIndex, int newRowIndex, int newColumnIndex, Enemy enemy){
        field.setFieldable(newRowIndex, newColumnIndex, enemy);
        field.setFieldable(rowIndex, columnIndex, new Empty());
        enemy.setRowIndex(newRowIndex);
        enemy.setColumnIndex(newColumnIndex);
        return false;

    }

    private void checkIfGameNotFinished(){
        if (turnsLeft == 0){
            System.out.println("No more turns left, you lost!");
            isGameFinished = true;
        } else if (transistorsGathered >=100){
            System.out.println("you have gathered  the required "+
                    "number of transistors, you won!!");
            isGameFinished= true;
        }
    }

}
