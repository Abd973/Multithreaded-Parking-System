import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Semaphore {

    private final AtomicInteger value;
    private final Queue<CarThread> carsQueue;
    private int occupiedSpots;


    public Semaphore(int permits) {
        this.value = new AtomicInteger(permits);
        this.carsQueue = new LinkedList<>();
        this.occupiedSpots = 0;
    }



    public AtomicInteger availablePermits(){
        if (value.get() <= 0){
            return new AtomicInteger(0);
        }
        else return value;
    }

    public synchronized void acquire(CarThread car) throws InterruptedException {
        if (occupiedSpots < value.get() && carsQueue.isEmpty()){
            occupiedSpots++;
            System.out.println(car.getname() + " from Gate " + car.getGateNO() +
                    " parked. (Parking Status: " + occupiedSpots + " spots occupied)");

        }
        else {
            System.out.println(car.getname() + " from Gate " + car.getGateNO() +
                    " waiting for a spot.");

            carsQueue.add(car);
            long startTime = System.currentTimeMillis();
            while (carsQueue.peek() != car || occupiedSpots == value.get()) {
                wait();
            }
            long endTime = System.currentTimeMillis();
            double time = (double) (endTime - startTime) /1000;
            time = Math.ceil(time);
            car.setWaitingTime((long) time);

            occupiedSpots++;
            carsQueue.poll();
            System.out.println(car.getname() + " from Gate " + car.getGateNO() +
                    " parked after waiting " + car.getWaitingTime() +  " units of time.  (Parking Status: " + occupiedSpots + " spots occupied)");
        }

    }

    public synchronized void release(CarThread car, int parkingTime) {
        occupiedSpots--;
        System.out.println(car.getname() + " from Gate " + car.getGateNO() +
                " left after " + parkingTime + " units of time. (Parking Status: "
                + occupiedSpots + " spots occupied)");
        if (!carsQueue.isEmpty()) notifyAll();
    }

    public boolean isBusy() {
        return value.get() <= 0;
    }
}
