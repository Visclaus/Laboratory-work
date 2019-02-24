package buildings.interfaces;

import java.util.Iterator;

public interface Building extends Cloneable, Iterable <Floor>{

    int getCnt();

    int getSpaceCnt();

    int getRn();

    double getSq();

    Floor[] getBuilding();

    Floor getFloor(int number);

    void setFloor(int number, Floor floor);

    Space getSpace(int number);

    void setSpace(int number, Space space);

    void addSpace(int number, Space space);

    void delSpace(int number);

    Space getBestSpace();

    Space[] getSorted();

    Object clone();

    Iterator<Floor> iterator();
}
