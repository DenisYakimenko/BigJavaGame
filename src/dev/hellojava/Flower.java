package dev.hellojava;

import java.util.Objects;

public class Flower implements Fieldable{

    private int transistors;

    private int rowIndex;
    private int columnIndex; //создаем координаты цветка

    public int getTransistors() {

        return transistors;
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

    //присвоили значения транзисторов и координаты к цветку
    public Flower (int transistors, int rowIndex, int columnIndex){

        this.transistors = transistors;
        this.rowIndex=rowIndex;
        this.columnIndex=columnIndex;
    }

    @Override
    public String getSymbol() {
        return String.valueOf(" "+transistors+" ");
    }


    // заходим в generate -> equals() and hashCode() и выбираем объекты сравнения rowIndex & columnIndex
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return rowIndex == flower.rowIndex && columnIndex == flower.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }
}
