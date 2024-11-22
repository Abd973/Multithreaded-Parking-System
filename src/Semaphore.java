import java.util.concurrent.atomic.AtomicInteger;

public class Semaphore {
    private final AtomicInteger value;

    public Semaphore(int permits) {
        this.value = new AtomicInteger(permits);
    }

    public AtomicInteger availablePermits() {
        return value;
    }

    public synchronized void acquire() {
        if (value.get() < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        value.decrementAndGet();
    }

    public synchronized void release() {
        if (value.incrementAndGet() <= 0) {
            notify();
        }
    }

    public boolean isBusy() {
        return value.get() == 0;
    }
}
