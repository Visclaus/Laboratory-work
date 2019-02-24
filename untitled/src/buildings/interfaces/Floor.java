package buildings.interfaces;

import java.util.Iterator;

public interface Floor extends Cloneable, Iterable <Space>, Comparable<Floor> {

    int getCnt();

    double getSq();

    int getRn();

    Space[] getFloor();

    Space getSpace(int number);

    void setSpace(int number, Space space);

    void addSpace(int number, Space space);

    void delSpace(int number);

    Space getBestSpace();

    Object clone();

    Iterator<Space> iterator();
}
