package buildings.threads;

import buildings.interfaces.Floor;

public class Cleaner extends  Thread {
    private Floor floor;

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run(){
        for (int i = 0; i <floor.getCnt() ; i++) {
            System.out.println("Cleaning room number " + i + " with total area" + floor.getSpace(i) +"square meters");
        }
        System.out.println("Cleaning is over!");
    }
}
