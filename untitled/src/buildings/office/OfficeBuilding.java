package buildings.office;

import Exeptions.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import buildings.*;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class OfficeBuilding implements Building, Serializable, Cloneable {

    private int cnt;
    private Node<Floor> Head;

    public class defaultOfficeFloorIterator implements Iterator<Floor> {

        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < cnt && getFloor(currentIndex) != null;
        }

        public Floor next() {
            return getFloor(currentIndex++);
        }
    }

    public OfficeBuilding( int[] arr) {
        this.cnt = arr.length;
        Head = new Node<>(new OfficeFloor(arr[0]));
        Node<Floor> circled = Head;
        for (int i = 1; i < cnt; i++) {
            Node<Floor> node = new Node<>(new OfficeFloor(arr[i]));
            node.setNext(Head);
            Head.setPrev(node);
            node.setPrev(Head);
            Head = node;
        }
        circled.setNext(Head);
        Head.setPrev(circled);
    }

    public OfficeBuilding(Floor[] floor) {
        this.cnt = floor.length;
        Head = new Node<>(floor[0]);
        Node<Floor> tmp = Head;
        for (int i = 1; i < cnt; i++) {
            Node<Floor> node = new Node<>(floor[i]);
            tmp.setNext(node);
            node.setPrev(tmp);
            tmp = node;
        }
        tmp.setNext(Head);
        Head.setPrev(tmp);
    }

    private Node<Floor> getNode(int number) throws FloorIndexOutOfBoundsException {
        if (number > cnt | number < 0) throw new FloorIndexOutOfBoundsException("Такого поля не существует");
        Node<Floor> node = Head;
        for (int i = 0; i < number; i++) {
            node = node.getNext();
        }
        return node;
    }

    @Override
    public Floor[] getBuilding() {
        Floor[] tmp = new Floor[getCnt()];
        Node<Floor> node = Head;
        for (int i = 0; i < getCnt(); i++) {
            tmp[i] = node.getField();
            node = node.getNext();
        }
        return tmp;
    }

    @Override
    public Floor getFloor(int number) throws FloorIndexOutOfBoundsException {
        try {
            return getNode(number).getField();
        } catch (FloorIndexOutOfBoundsException e) {
            throw new FloorIndexOutOfBoundsException("Нет такого этажа!");
        }
    }

    @Override
    public void setFloor(int number, Floor floor) throws FloorIndexOutOfBoundsException {
        try {
            getNode(number).setField(floor);
        } catch (FloorIndexOutOfBoundsException e) {
            throw new FloorIndexOutOfBoundsException("Нет такого этажа!");
        }
    }

    @Override
    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt() - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такого офиса");
        Node<Floor> node = Head;
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += node.getField().getCnt();
            node = node.getNext();
        }
        if (c > number) {
            i--;
            return getNode(i).getField().getSpace(getNode(i).getField().getCnt() - c + number);
        }
        return getNode(i).getField().getSpace(0);
    }

    @Override
    public void setSpace(int number, Space office) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt() - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такого офиса");
        Node<Floor> node = Head;
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += node.getField().getCnt();
            node = node.getNext();
        }
        if (c > number) {
            i--;
            getNode(i).getField().setSpace(getNode(i).getField().getCnt() - c + number, office);
        }
        if (c == number) getNode(i).getField().setSpace(0, office);
    }

    @Override
    public void addSpace(int number, Space office) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt() | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такого офиса");
        if (number == getSpaceCnt()) {
            getNode(cnt - 1).getField().addSpace(getNode(cnt - 1).getField().getCnt(), office);
            return;
        }
        Node<Floor> node = Head;
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += node.getField().getCnt();
            node = node.getNext();
        }
        if (c > number) {
            i--;
            getNode(i).getField().addSpace(getNode(i).getField().getCnt() - c + number, office);
        }
        if (c == number) getNode(i).getField().addSpace(0, office);
    }

    @Override
    public void delSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number > getSpaceCnt() - 1 | number < 0) throw new SpaceIndexOutOfBoundsException("Нет такого офиса");
        if (number == getSpaceCnt()){
            getNode(cnt - 1).getField().delSpace(getNode(cnt - 1).getField().getCnt());
            return;
        }
        Node<Floor> node = Head;
        int i, c = 0;
        for (i = 0; c < number; i++) {
            c += node.getField().getCnt();
            node = node.getNext();
        }
        if (c > number) {
            i--;
            getNode(i).getField().delSpace(getNode(i).getField().getCnt() - c + number);
        }
        if (c == number) getNode(i).getField().delSpace(0);
    }

    @Override
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

    @Override
    public Space getBestSpace() {
        Node<Floor> node = Head;
        Space max = node.getField().getBestSpace();
        for (int i = 0; i < cnt; i++) {
            if (node.getField().getBestSpace().getSq() > max.getSq()) {
                max = node.getField().getBestSpace();
            }
            node = node.getNext();
        }
        return max;
    }

    @Override
    public int getSpaceCnt() {
        Node<Floor> node = Head;
        int c = 0;
        for (int i = 0; i < cnt; i++) {
            c += node.getField().getCnt();
            node = node.getNext();
        }
        return c;
    }

    @Override
    public int getCnt() {
        return cnt;
    }

    @Override
    public double getSq() {
        Node<Floor> node = Head;
        double Sq = 0;
        for (int i = 0; i < cnt; i++) {
            Sq += node.getField().getSq();
            node = node.getNext();
        }
        return Sq;
    }

    @Override
    public int getRn() {
        Node<Floor> node = Head;
        int Rn = 0;
        for (int i = 0; i < cnt; i++) {
            Rn += node.getField().getRn();
            node = node.getNext();
        }
        return Rn;
    }

    @Override
    public String toString() {
        Node<Floor> tmp = Head;
        StringBuilder info = new StringBuilder("Офисное здание ( " + cnt + " | ");
        for (int i = 0; i < cnt; i++) {
            info.append(tmp.getField().toString()).append(" ");
            tmp = tmp.getNext();
        }
        info.append(")");
        return info.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof OfficeBuilding)) {
            return false;
        }
        OfficeBuilding building = (OfficeBuilding) object;
        return Arrays.deepEquals(this.getBuilding(), building.getBuilding());
    }

    @Override
    public int hashCode() {
        int c = 0;
        Node<Floor> node = Head;
        for (int i = 0; i < cnt; i++) {
            c ^= node.getField().hashCode();
            node = node.getNext();
        }
        return c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        Object tmp = null;
        try {
            tmp = super.clone();
            ((OfficeBuilding) tmp).Head = (Node<Floor>) this.Head.clone();
            Node<Floor> clone = ((OfficeBuilding) tmp).Head;
            Node<Floor> clone_1 = ((OfficeBuilding) tmp).Head;
            Node<Floor> original = this.Head;
            clone.setNext((Node<Floor>) original.getNext().clone());
            clone.setField((Floor)original.getField().clone());
            original = original.getNext();
            clone = clone.getNext();
            for (int i = 1; i < cnt - 1; i++) {
                clone.setNext((Node<Floor>) original.getNext().clone());
                clone.setPrev(clone_1);
                clone.setField((Floor)original.getField().clone());
                clone_1 = clone;
                original = original.getNext();
                clone = clone.getNext();
            }
            clone.setNext((((OfficeBuilding) tmp).Head));
            clone.setPrev(clone_1);
            clone.setField((Floor)original.getField().clone());
            ((OfficeBuilding) tmp).Head.setPrev(clone);
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return tmp;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new defaultOfficeFloorIterator();
    }

    private void addNode(int number) throws FloorIndexOutOfBoundsException {
        if (number > cnt | number < 1) throw new FloorIndexOutOfBoundsException("Такого поля не существует");
        cnt++;
        Node<Floor> node = new Node<Floor>(new OfficeFloor(3));
        Node<Floor> circled = Head;
        if (number == 1) {
            for (int i = 0; i < cnt - 2; i++) {
                circled.setNext(circled.getNext());
            }
            node.setNext(Head);
            Head = node;
            node.setPrev(circled);
            circled.setNext(Head);
        } else {
            Node<Floor> tmp = Head;
            for (int i = 0; i < number - 2; i++) {
                tmp.setNext(tmp.getNext());
            }
            node.setNext(tmp.getNext());
            node.setPrev(tmp);
            tmp.getNext().setPrev(node);
            tmp.setNext(node);
        }
    }

    private void delNode(int number) throws FloorIndexOutOfBoundsException {
        if (number > cnt | number < 1) throw new FloorIndexOutOfBoundsException("Такого поля не существует");
        Node<Floor> circled = Head;
        cnt--;
        if (number == 1) {
            for (int i = 0; i < cnt; i++) {
                circled.setNext(circled.getNext());
            }
            Head.setNext(Head.getNext());
            Head.setPrev(circled);
            circled.setNext(Head);
        } else {
            Node<Floor> tmp = Head;
            for (int i = 0; i < number - 2; i++) {
                tmp.setNext(tmp.getNext());
            }
            tmp.setNext(tmp.getNext().getNext());
            tmp.getNext().setPrev(tmp);
        }
    }
}
