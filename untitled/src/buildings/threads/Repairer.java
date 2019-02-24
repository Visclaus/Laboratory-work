package buildings.threads;

import buildings.interfaces.Floor;

public class Repairer extends Thread {
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
            System.out.println("Repairing space number " + i + " with total area" + floor.getSpace(i) +"square meters");
        }
        System.out.println("Repairing is over!");
    }
}
