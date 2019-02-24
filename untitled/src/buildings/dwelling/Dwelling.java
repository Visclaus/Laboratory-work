package buildings.dwelling;

import Exeptions.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Dwelling implements Building, Serializable,Cloneable {

    protected Floor[] arr;

    protected int cnt;

    public class dwellingFloorIterator implements Iterator<Floor> {
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < cnt && arr[currentIndex] != null;
        }

        public Floor next() {
            return arr[currentIndex++];
        }
    }

    public Dwelling(){}

    public Dwelling(int[] spacesCount) {
        cnt = spacesCount.length;
        arr = new Floor[cnt];
        for (int i = 0; i < cnt; i++) {
            arr[i] = new DwellingFloor(spacesCount[i]);
        }
    }

    public Dwelling(Floor[] arr) {
        this.cnt = arr.length;
        this.arr = arr;
    }

    public Floor[] getBuilding() {
        return arr;
    }

    public Floor getFloor(int number) throws FloorIndexOutOfBoundsException {
        if (number > cnt | number < 0) throw new FloorIndexOutOfBoundsException("Такого этажа не существует");
        return arr[number];
    }

    public void setFloor(int number, Floor obj) throws FloorIndexOutOfBoundsException {
        if (number > cnt | number < 0) throw new FloorIndexOutOfBoundsException("Такого этажа не существует");
        arr[number] = obj;
    }

    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt() - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такой квартиры");
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += arr[i].getCnt();
        }
        if (c > number) {
            i--;
            return arr[i].getSpace(arr[i].getCnt() - c + number);
        }
        return arr[i].getSpace(0);
    }

    public void setSpace(int number, Space obj) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt() - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такой квартиры");
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += arr[i].getCnt();
        }
        if (c > number) {
            i--;
            arr[i].setSpace(arr[i].getCnt() - c + number, obj);
        }
        if (c == number) arr[i].setSpace(0, obj);
    }

    public void addSpace(int number, Space obj) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt()  | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такой квартиры");
        if (number == getSpaceCnt()) {
            arr[cnt-1].addSpace(arr[cnt-1].getCnt(), obj);
            return;
        }
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += arr[i].getCnt();
        }
        if (c > number) {
            i--;
            arr[i].addSpace(arr[i].getCnt() - c + number, obj);
        }
        if (c == number) arr[i].addSpace(0, obj);
    }

    public void delSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt()  | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такой квартиры");
        if (number == getSpaceCnt()) {
            arr[cnt-1].delSpace(arr[cnt-1].getCnt());
            return;
        }
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += arr[i].getCnt();
        }
        if (c > number) {
            i--;
            arr[i].delSpace(arr[i].getCnt() - c + number);
        }
        if (c == number) arr[i].delSpace(0);
    }

    public double getSq() {
        int c = 0;
        for (int i = 0; i < cnt; i++) {
            c += arr[i].getSq();
        }
        System.out.println("Общая площадь всех квартир:" + c);
        return c;
    }

    public int getRn() {
        int c = 0;
        for (int i = 0; i < cnt; i++) {
            c += arr[i].getRn();
        }
        System.out.println("Общее количество всех комнат:" + c);
        return c;
    }

    public int getSpaceCnt() {
        int c = 0;
        for (int i = 0; i < cnt; i++) {
            c += arr[i].getCnt();
        }
        System.out.println("Общее количество квартир в доме:");
        System.out.println(c);
        return c;
    }

    public Space getBestSpace() {
        Space max = arr[0].getSpace(0);
        for (int i = 1; i < cnt; i++) {
            if (arr[i].getBestSpace().getSq() > max.getSq()) {
                max = arr[i].getBestSpace();
            }
        }
        return max;
    }

    public Space[] getSorted() {
        int size = getSpaceCnt();
        Space[] sorted = new Space[size];
        for (int i = 0; i < size; i++) {
            sorted[i] = getSpace(i + 1);
        }
        boolean swap = false;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (sorted[j].getSq() < sorted[j + 1].getSq()) {
                    swap = true;
                    Space h = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = h;
                }
            }
            if (!swap) break;
        }
        return sorted;
    }

    public int getCnt() {
        return cnt;
    }

    public String toString() {
        StringBuilder info = new StringBuilder("Жилое здание ( " + cnt + " | ");
        for (int i = 0; i < cnt; i++) {
            info.append(arr[i].toString()).append(" ");
        }
        info.append(")");
        return info.toString();
    }

    public boolean equals (Object object){
        if (object == this) {
            return true;
        }
        if (!(object instanceof Dwelling)) {
            return false;
        }
        Dwelling building = (Dwelling) object;
        return Arrays.deepEquals(this.arr, building.arr);
    }

    public int hashCode(){
        int c =0;
        for(Floor i : arr ){
            c^= i.hashCode();
        }
        return c;
    }

    public Object clone(){
        Object tmp = null;
        try {
            tmp = super.clone();
            ((Dwelling)tmp).arr = this.arr.clone();
            for (int i = 0; i < cnt ; i++) {
                ((Dwelling)tmp).arr[i] = (Floor) this.arr[i].clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public Iterator<Floor> iterator() {
        return new dwellingFloorIterator();
    }
}



