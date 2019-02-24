import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.*;
import com.sun.media.jfxmedia.events.BufferListener;


public class Main {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 2, 1};
        Building t = new Dwelling(arr);
        System.out.println(t.toString());
        System.out.println("------------");
        t.delSpace(5);
        System.out.println(t.toString());
    }
}
