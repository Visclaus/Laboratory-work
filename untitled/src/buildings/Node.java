package buildings;

import java.io.Serializable;

public class Node<T> implements Serializable, Cloneable {

    private T field;
    private Node<T> next = null;
    private Node<T> prev = null;

    public Node(T field) {
        this.field = field;
    }

    public T getField() {
        return field;
    }

    public void setField(T field) {
        this.field = field;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public Object clone() {
        Object tmp = null;
        try {
            tmp = super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return tmp;
    }
}
