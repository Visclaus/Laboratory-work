package buildings;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Iterator;

public  class SynchronizedFloor implements Floor {

    private final Floor floor;

    SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    public synchronized int getCnt(){
        return this.floor.getCnt();
    }

    public synchronized double getSq(){
        return this.floor.getSq();
    }

    public synchronized int getRn(){
        return this.floor.getRn();
    }

    public synchronized Space[] getFloor(){
        return this.floor.getFloor();
    }

    public synchronized Space getSpace(int number){
        return this.floor.getSpace(number);
    }

    public synchronized void setSpace(int number, Space space){
        this.floor.setSpace(number,space);
    }

    public synchronized void addSpace(int number, Space space){
        this.floor.addSpace(number,space);
    }

    public synchronized void delSpace(int number){
        this.floor.delSpace(number);
    }

    public synchronized Space getBestSpace(){
        return this.floor.getBestSpace();
    }

    public synchronized Object clone(){
        return new SynchronizedFloor((Floor)this.floor.clone());
    }

    public synchronized Iterator iterator(){
        return this.floor.iterator();
    }

    public synchronized int compareTo(Floor o){
        return this.floor.compareTo(o);
    }
}
