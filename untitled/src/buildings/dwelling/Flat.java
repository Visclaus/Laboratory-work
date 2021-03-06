package buildings.dwelling;

import Exeptions.*;
import java.io.Serializable;
import java.util.Random;

import buildings.interfaces.Space;

public class Flat implements Space, Serializable, Cloneable {

    private double square;

    private int rn;

    public static final double SQUARE = 50;

    public static final int RN = 2;

    public Flat() {
        Random rand = new Random();
        square = rand.nextInt(300) + 1;
        rn = rand.nextInt(7) + 1;
    }

    public Flat(double square) throws InvalidSpaceAreaException {
        if (square < 0) throw new InvalidSpaceAreaException("Неправильная площадь, S>0");
        this.square = square;
        rn = RN;
    }

    public Flat(double square, int rn) throws InvalidSpaceAreaException, InvalidRoomsCountException {
        if (square < 0) throw new InvalidSpaceAreaException("Неправильная площадь, S>0");
        if (rn < 0) throw new InvalidRoomsCountException("Неправильное количество комнат, RN>0");
        this.square = square;
        this.rn = rn;
    }

    public double getSq() {
        return square;
    }

    public void setSq(double square) throws InvalidSpaceAreaException {
        if (square < 0) throw new InvalidSpaceAreaException("Неправильная площадь, S>0");
        this.square = square;
    }

    public int getRn() {
        return rn;
    }

    public void setRn(int rn) throws InvalidRoomsCountException {
        if (rn < 0) throw new InvalidRoomsCountException("Неправильное количество комнат, RN>0");
        this.rn = rn;
    }

    @Override
    public String toString() {
        return "Квартира(" + rn + ", " + square + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Flat)) {
            return false;
        }
        Flat flat = (Flat) object;
        return (this.rn == flat.rn) && (this.square == flat.square);
    }

    @Override
    public int hashCode(){
        return rn^(int)square;
    }

    @Override
    public Object clone() {
        Object tmp =null;
        try {
            tmp = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public int compareTo (Space o){
        return Double.compare(this.square, o.getSq());
    }
}
