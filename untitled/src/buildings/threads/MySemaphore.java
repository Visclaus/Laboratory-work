package buildings.threads;

public class MySemaphore {
    private int max;
    private int cur;
    private Object object = new Object();

    public MySemaphore(int curMax) {
        this.max = curMax;
    }

    public void enter() {
        synchronized (object) {
            cur++;
            if (cur > max) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void leave() {
        synchronized (object) {
            cur--;
            object.notify();
        }
    }
}