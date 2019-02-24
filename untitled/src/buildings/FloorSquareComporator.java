package buildings;

import buildings.interfaces.Floor;

import java.util.Comparator;

public class FloorSquareComporator implements Comparator<Floor> {

    @Override
    public int compare(Floor o1, Floor o2) {
        return Double.compare(o2.getSq(), o1.getSq());
    }
}
