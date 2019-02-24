package buildings.dwelling.hotel;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.dwelling.Dwelling;

import java.util.Arrays;

public class Hotel extends Dwelling implements Building {

    public Hotel (Floor[] floors){
        super(floors);
    }

    public Hotel (int[] spacesCounts){
        cnt = spacesCounts.length;
        arr = new Floor[cnt];
        for (int i = 0; i < cnt; i++) {
            arr[i] = new HotelFloor(spacesCounts[i]);
        }
    }

    public int hotelStarCnt() {
        int max = 0;
        for (int i = 0; i < getCnt(); i++) {
            if (getFloor(i) instanceof HotelFloor && ((HotelFloor) getFloor(i)).getStarCnt() > max)
                max = ((HotelFloor) getFloor(i)).getStarCnt();
        }
        return max;
    }

    public Space getBestSpace() {
        Space max = null;
        double max_1 = 0;
        for (int i = 0; i < getCnt(); i++) {
            if (getFloor(i) instanceof HotelFloor) {
                for (int j = 0; j < getFloor(i).getCnt(); j++) {
                    if (getFloor(i).getSpace(j).getSq() * ((HotelFloor) getFloor(i)).coeff() > max_1) {
                        max = getFloor(i).getSpace(j);
                        max_1 = getFloor(i).getSpace(j).getSq() * ((HotelFloor) getFloor(i)).coeff();
                    }
                }
            }
        }
        return max;
    }

    public String toString(){
        StringBuilder info = new StringBuilder("Отель ( " + hotelStarCnt()+ ", " + getCnt() + " | ");
        for (int i = 0; i < getCnt(); i++) {
            info.append(getBuilding()[i].toString()).append(" ");
        }
        info.append(")");
        return info.toString();
    }

    public boolean equals (Object object){
        if (object == this) {
            return true;
        }
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel building = (Hotel) object;
        return Arrays.deepEquals(getBuilding(), building.getBuilding());
    }

    public int hashCode(){
        int c =0;
        for(Floor i : getBuilding() ){
            c^= i.hashCode();
        }
        c^= getCnt();
        return c;
    }
}
