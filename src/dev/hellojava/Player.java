package dev.hellojava;

public class Player implements Fieldable{

    private static final String MOVE_LEFT = "z";
    private static final String MOVE_RIGHT = "c";
    private static final String MOVE_UP = "s";
    private static final String MOVE_DOWN = "x";
    private static final String NO_MOVE = "q";
    private int rowIndex;
    private int columnIndex;
    private Field field;
    private Game game;

    @Override
    public String getSymbol(){
        return " @ ";
    }



    public Player(int rowIndex, int columnIndex, Game game){

        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.field = game.getField();
        this.game = game;
        field.setFieldable(rowIndex,columnIndex,this);


    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {

        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {

        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {

        this.columnIndex = columnIndex;
    }

    //метод реализующий движение игрока
    public Boolean makeMove(String command){

        Boolean isIncorrectMove = true;

        switch (command){

            case MOVE_LEFT:
                isIncorrectMove = movePlayer(0,-1);
                break;
            case MOVE_RIGHT:
                isIncorrectMove = movePlayer(0,1);
                break;
            case MOVE_UP:
                isIncorrectMove = movePlayer(-1,0);
                break;
            case MOVE_DOWN:
                isIncorrectMove = movePlayer(1,0);
                break;
            case NO_MOVE:
                isIncorrectMove = false;
                break;

            default:
                showError(command);
                break;


        }
        return isIncorrectMove;
    }

    private Boolean movePlayer(int deltaRowIndex, int deltaColumnIndex){

        int newRowIndex = rowIndex + deltaRowIndex;
        int newColumnIndex = columnIndex + deltaColumnIndex;

        //проверяем чтобы наши новые координаты находились внутри поля, и небыло на клетке противника(Enemy).

        if((newRowIndex>=0)&&(newRowIndex < field.getSizeX())
            && (newColumnIndex >= 0) &&(newColumnIndex < field.getSizeY())
                //  instanceof метод определяющий не является ли объект, объектом определенного типа (Enemy)
            && !((field.getFieldable(newRowIndex,newColumnIndex)) instanceof Enemy)){
            //далее проверяем не стоит ли в клетке цветок (Flower), если да, то засчитываем его игроку
            if (field.getFieldable(newRowIndex, newColumnIndex) instanceof Flower){

                //получили кол во транзисторов
                Flower flower = (Flower) field.getFieldable(newRowIndex, newColumnIndex);
                //прибавляем к текущему счету
                game.setTransistorsGathered(flower.getTransistors());
                //удаляем из арейлиста объект flower на основании сравнения с массивом
                game.getFlowerArrayList().remove(flower);
                //записываем в поле объект с новыми координатами
               // field.setFieldable(newColumnIndex,newRowIndex,this);
                //вписываем Empty на предыдущее место где был объект
               // field.setFieldable(columnIndex,rowIndex,new Empty());
               // rowIndex = newRowIndex;
               // columnIndex = newColumnIndex;
                //в этом методе все что находится сверху в коде
                swapPlayer(newRowIndex,newColumnIndex);

            }
            //если пустое поле
            if(field.getFieldable(newRowIndex, newColumnIndex) instanceof Empty){
            swapPlayer(newRowIndex, newColumnIndex);

            }
            return false;
        }
        else{
            return true;
        }
    }

    //делаем новый метод чтобы избежать повтора в коде
    private void swapPlayer(int newRowIndex,int newColumnIndex){
        field.setFieldable(newRowIndex,newColumnIndex,this);
        field.setFieldable(rowIndex,columnIndex,new Empty());
        rowIndex = newRowIndex;
        columnIndex = newColumnIndex;
    }

    private void showError(String command){
        System.out.println("Sorry, there is no "+command+" command, please verify and try again.");
    }
}
