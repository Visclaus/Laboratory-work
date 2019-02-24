package buildings.office;

import Exeptions.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import buildings.*;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class OfficeFloor implements Floor, Serializable, Cloneable {

    private int cnt;
    private Node<Space> Head;

    public class defaultOfficeIterator implements Iterator<Space> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < cnt && getSpace(currentIndex) != null;
        }

        @Override
        public Space next() {
            return getSpace(currentIndex++);
        }
    }

    public OfficeFloor(Space[] data) {
        this.cnt = data.length;
        Head = new Node<>(data[0]);
        Node<Space> tmp = Head;
        for (int i = 1; i < cnt; i++) {
            Node<Space> node = new Node<>(data[i]);
            tmp.setNext(node);
            tmp = node;
        }
        tmp.setNext(Head);
    }

    public OfficeFloor(int cnt) {
        this.cnt = cnt;
        Head = new Node<>(new Office());
        Node<Space> circled = Head;
        for (int i = 0; i < cnt - 1; i++) {
            Node<Space> node = new Node<>(new Office());
            node.setNext(Head);
            Head = node;
        }
        circled.setNext(Head);
    }

    private Node<Space> getNode(int number) throws SpaceIndexOutOfBoundsException {
        if (number > cnt | number < 0) throw new SpaceIndexOutOfBoundsException("Такого поля не существует");
        Node<Space> node = Head;
        for (int i = 0; i < number; i++) {
            node = node.getNext();
        }
        return node;
    }

    @Override
    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        try {
            return getNode(number).getField();
        } catch (SpaceIndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException("Нет такого офиса");
        }
    }

    @Override
    public void setSpace(int number, Space office) throws SpaceIndexOutOfBoundsException {
        try {
            getNode(number).setField(office);
        } catch (SpaceIndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException("Нельзя изменить несуществующий офис");
        }
    }

    @Override
    public void addSpace(int number, Space office) throws SpaceIndexOutOfBoundsException {
        try {
            if (number > cnt | number < 0) throw new SpaceIndexOutOfBoundsException("Невозможно добавить");
            cnt++;
            Node<Space> node = new Node<>(office);
            Node<Space> circled = Head;
            if (number == 0) {
                for (int i = 0; i < cnt - 2; i++) {
                    circled = circled.getNext();
                }
                node.setNext(Head);
                Head = node;
                circled.setNext(Head);

            } else {
                Node<Space> tmp = Head;
                for (int i = 0; i < number - 1; i++) {
                    tmp = tmp.getNext();
                }
                node.setNext(tmp.getNext());
                tmp.setNext(node);
            }
        } catch (SpaceIndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException("Такого места для офиса не существует");
        }
    }

    @Override
    public void delSpace(int number) throws SpaceIndexOutOfBoundsException {
        try {
            if (number > cnt | number < 0) throw new SpaceIndexOutOfBoundsException("Невозможно удалить");
            Node<Space> circled = Head;
            cnt--;
            if (number == 0) {
                for (int i = 0; i < cnt; i++) {
                    circled = circled.getNext();
                }
                Head = Head.getNext();
                circled.setNext(Head);
            } else {
                Node<Space> tmp = Head;
                for (int i = 0; i < number - 1; i++) {
                    tmp = tmp.getNext();
                }
                tmp.setNext(tmp.getNext().getNext());
            }
        } catch (SpaceIndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException("Попытка удалить несуществующий офис");
        }
    }

    @Override
    public Space[] getFloor() {
        Node<Space> node = Head;
        Space[] tmp = new Space[cnt];
        for (int i = 0; i < cnt; i++) {
            tmp[i] = node.getField();
            node = node.getNext();
        }
        return tmp;
    }

    @Override
    public Space getBestSpace() {
        Node<Space> node = Head;
        Space max = node.getField();
        for (int i = 0; i < cnt; i++) {
            if ((node.getField()).getSq() > max.getSq()) {
                max = node.getField();
            }
            node = node.getNext();
        }
        return max;
    }

    @Override
    public int getCnt() {
        return cnt;
    }

    @Override
    public int getRn() {
        Node<Space> node = Head;
        int Rn = 0;
        for (int i = 0; i < cnt; i++) {
            Rn += node.getField().getRn();
            node = node.getNext();
        }
        return Rn;
    }

    @Override
    public double getSq() {
        Node<Space> node = Head;
        double Sq = 0;
        for (int i = 0; i < cnt; i++) {
            Sq += node.getField().getSq();
            node = node.getNext();
        }
        return Sq;
    }

    @Override
    public String toString() {
        Node<Space> node = Head;
        StringBuilder info = new StringBuilder("Офисный этаж ( " + cnt + " | ");
        for (int i = 0; i < cnt; i++) {
            info.append(node.getField().toString()).append(" | ");
            node = node.getNext();
        }
        info.append(")");
        return info.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof OfficeFloor)) {
            return false;
        }
        OfficeFloor floor = (OfficeFloor) object;
        return Arrays.deepEquals(this.getFloor(), floor.getFloor());
    }

    @Override
    public int hashCode() {
        int c = 0;
        Node<Space> node = Head;
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
            ((OfficeFloor) tmp).Head = (Node<Space>) this.Head.clone();
            Node<Space> clone = ((OfficeFloor) tmp).Head;
            Node<Space> original = this.Head;
            for (int i = 0; i < cnt - 1; i++) {
                clone.setNext((Node<Space>) original.getNext().clone());
                clone.setField((Space) original.getField().clone());
                original = original.getNext();
                clone = clone.getNext();
            }
            clone.setNext((((OfficeFloor) tmp).Head));
            clone.setField((Space) original.getField().clone());
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return tmp;
    }

    @Override
    public int compareTo(Floor o) {
        return Integer.compare(this.cnt, o.getCnt());
    }

    @Override
    public Iterator<Space> iterator() {
        return new defaultOfficeIterator();
    }
}

