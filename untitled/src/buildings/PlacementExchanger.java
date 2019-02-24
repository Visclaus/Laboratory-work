package buildings;
import Exeptions.*;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class PlacementExchanger {

    static public boolean checkSpace(Space space1, Space space2) {
        return (space1.getSq() == space2.getSq() & space1.getRn() == space2.getRn());
    }

    static public boolean checkFloor(Floor floor1, Floor floor2) {
        return (floor1.getSq() == floor2.getSq() & floor1.getRn() == floor2.getRn());
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexChangeableSpacesException {
        try {
            if (checkSpace(floor1.getSpace(index1), floor2.getSpace(index2))) {
                Space tmp = floor1.getSpace(index1);
                floor1.setSpace(index1, floor2.getSpace(index2));
                floor2.setSpace(index2, tmp);
            } else throw new InexChangeableSpacesException("Невозможно поменять помещения");
        } catch (SpaceIndexOutOfBoundsException e) {
            e.getMessage();
        }
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        try {
            if (checkFloor(building1.getFloor(index1), building2.getFloor(index2))) {
                Floor tmp = building1.getFloor(index1);
                building1.setFloor(index1, building2.getFloor(index2));
                building2.setFloor(index2, tmp);
            } else throw new InexchangeableFloorsException("Невозможно поменять этажи");
        } catch (FloorIndexOutOfBoundsException e) {
            e.getMessage();
        }
    }
}
