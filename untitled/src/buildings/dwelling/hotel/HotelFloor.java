package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Arrays;

public class HotelFloor  extends DwellingFloor implements Floor {
    private static final int STAR = 1;
    private int starCnt;

    public HotelFloor(int cnt) {
        super(cnt);
        starCnt = STAR;
    }

    public HotelFloor(Space[] arr) {
        super(arr);
        starCnt = STAR;
    }

    public double coeff(){
        double arr_1 [ ]= {0.25,0.5,1,1.25,1.5};
        return arr_1[starCnt-1];
    }

    public int getStarCnt() {
        return starCnt;
    }

    public void setStarCnt(int starCnt) {
        this.starCnt = starCnt;
    }

    public String toString() {
        StringBuilder info = new StringBuilder("Этаж отеля ( " + starCnt+ ", " + getCnt() + " | ");
        for (int i = 0; i < getCnt(); i++) {
            info.append(getFloor()[i].toString()).append(" | ");
        }
        info.append(")");
        return info.toString();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof HotelFloor)) {
            return false;
        }
        HotelFloor floor = (HotelFloor) object;
        return Arrays.deepEquals(getFloor(),floor.getFloor());
    }

    public int hashCode(){
        int c =0;
        for(Space i : getFloor() ){
            c^= i.hashCode();
        }
        c = c^starCnt^getCnt();
        return c;
    }
}
