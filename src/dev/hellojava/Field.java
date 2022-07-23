package dev.hellojava;

// класс игрового поля

public class Field {

    private int row;
    private int columns;


    private Fieldable[][] field;

    public Field(int sizeX, int sizeY) {
        this.row = sizeX;
        this.columns = sizeY;
        field = new Fieldable[sizeX][sizeY];
    }

    //методы для получения размеров поля для передвижения игрока
    public int getSizeX() {
        return row;

    }

    public int getSizeY(){

        return columns;
    }

    //записеваем координаты
    public void setFieldable(int x, int y, Fieldable object){

        field[x][y] = object;
    }

    //извлекаем координаты
    public Fieldable getFieldable(int x, int y){

        return field[x][y];
    }

    //метод рисующий поле
    public void showField(){
        System.out.println();
        for (int i=0; i < row; i++) {
            System.out.println();


            for (int j=0; j<columns; j++){
                System.out.print(field[i][j].getSymbol());
            }
        }

        System.out.println();
    }

}

