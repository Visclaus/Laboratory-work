package buildings.threads;

import buildings.interfaces.Floor;

import static java.lang.Thread.sleep;

public class SequentalRepairer implements Runnable {
    private MySemaphore semaphore;
    private Floor floor;

    public SequentalRepairer(MySemaphore semaphore, Floor floor) {
        this.semaphore = semaphore;
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i <floor.getCnt() ; i++) {
            semaphore.enter();
            System.out.println("Repairing space number " + i + " with total area" + floor.getSpace(i) +"square meters");
            /*try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            semaphore.leave();
        }
        System.out.println("Repairing is over!");

    }
}
