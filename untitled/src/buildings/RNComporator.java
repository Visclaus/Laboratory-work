package buildings;
import buildings.interfaces.Space;

import java.util.Comparator;

public class RNComporator implements Comparator<Space> {

    @Override
    public int compare(Space o1, Space o2) {
        return Integer.compare(o2.getRn(), o1.getRn());
    }
}

