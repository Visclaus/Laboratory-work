package buildings.dwelling;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import Exeptions.*;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class DwellingFloor implements Floor, Serializable, Cloneable{

    private Space[] arr;

    private int cnt;

    public class flatIterator implements Iterator<Space> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < cnt && arr[currentIndex] != null;
        }

        @Override
        public Space next() {
            return arr[currentIndex++];
        }
    }

    public DwellingFloor(Space[] arr) {
        cnt = arr.length;
        this.arr = arr;
    }

    public DwellingFloor(int cnt) {
        this.cnt = cnt;
        arr = new Space[cnt];
        for (int i = 0; i < cnt; i++) {
            arr[i] = new Flat();
        }
    }

    public Space[] getFloor() {
        return arr;
    }

    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number > cnt - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Такой квартиры не существует");
        return arr[number];
    }

    public void setSpace(int number, Space obj) throws SpaceIndexOutOfBoundsException {
        if (number > cnt - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Такой квартиры не существует");
        arr[number] = obj;
    }

    public void addSpace(int number, Space obj) throws SpaceIndexOutOfBoundsException {
        if (number > cnt | number < 0) throw new SpaceIndexOutOfBoundsException("Такой квартиры не существует");
        cnt++;
        Space[] sample = new Space[cnt];
        for (int i = 0; i < number; i++) {
            sample[i] = arr[i];
        }
        for (int i = number + 1; i < cnt; i++) {
            sample[i] = arr[i - 1];
        }
        sample[number] = obj;
        arr = sample;
    }

    public void delSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number > cnt | number < 0) throw new SpaceIndexOutOfBoundsException("Такой квартиры не существует");
        int cnt_1 = cnt;
        cnt--;
        Space[] sample = new Space[cnt];
        for (int i = 0; i < number; i++) {
            sample[i] = arr[i];
        }
        for (int i = number + 1; i < cnt_1; i++) {
            sample[i - 1] = arr[i];
        }
        arr = sample;
    }

    public Space getBestSpace() {
        Space max = arr[0];
        for (int i = 1; i < cnt; i++) {
            if (arr[i].getSq() > max.getSq()) {
                max = arr[i];
            }
        }
        return max;
    }

    public int getCnt() {
        return cnt;
    }

    public int getRn() {
        int c = 0;
        for (Space i : arr) {
            c += i.getRn();
        }
        return c;
    }

    public double getSq() {
        double c = 0;
        for (Space i : arr) {
            c += i.getSq();
        }
        return c;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("Жилой этаж ( " + cnt + " | ");
        for (int i = 0; i < cnt; i++) {
            info.append(arr[i].toString()).append(" | ");
        }
        info.append(")");
        return info.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof DwellingFloor)) {
            return false;
        }
        DwellingFloor floor = (DwellingFloor) object;
        return Arrays.deepEquals(this.arr, floor.arr);
    }

    @Override
    public int hashCode() {
        int c = 0;
        for (Space i : arr) {
            c ^= i.hashCode();
        }
        return c;
    }

    @Override
    public Object clone() {
        Object tmp = null;
        try {
            tmp = super.clone();
            ((DwellingFloor) tmp).arr = this.arr.clone();
            for (int i = 0; i < cnt; i++) {
                ((DwellingFloor) tmp).arr[i] = (Space) this.arr[i].clone();
            }
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return tmp;
    }

    @Override
    public int compareTo (Floor o){
        return Integer.compare(this.cnt, o.getCnt());
    }

    public Iterator<Space> iterator() {
        return new flatIterator();
    }
}
