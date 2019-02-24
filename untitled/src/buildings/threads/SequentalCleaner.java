package buildings.threads;

import buildings.interfaces.Floor;

import static java.lang.Thread.sleep;

public class SequentalCleaner implements Runnable {
    private MySemaphore semaphore;
    private Floor floor;

    public SequentalCleaner(MySemaphore semaphore, Floor floor) {
        this.semaphore = semaphore;
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getCnt(); i++) {
            semaphore.enter();
            System.out.println("Cleaning room number " + i + " with total area" + floor.getSpace(i) + "square meters");
            semaphore.leave();
        }
        System.out.println("Cleaning is over!");
    }
}

