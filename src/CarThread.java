public class CarThread extends Thread {
    private String name;
    private int gateNO;
    private int arrivalTime;
    private long waitingTime = 0;
    private int parkingTime;
    private static Semaphore spots = new Semaphore(4);

    public CarThread(String name, int gateNO, int arrivalTime, int parkingTime) {
        this.name = name;
        this.setName(name);
        this.gateNO = gateNO;
        this.arrivalTime = arrivalTime;
        this.parkingTime = parkingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void carArrival() {
        synchronized (System.out) {
            System.out.println(name + " from Gate " + gateNO + " arrived at time " + arrivalTime);
        }
    }

    public void waitTime() {
        long startTime = System.currentTimeMillis();
        spots.acquire();
        long endTime = System.currentTimeMillis();
        this.waitingTime = endTime - startTime;

        synchronized (System.out) {
            System.out.println(name + " from Gate " + this.gateNO + " waited for " + this.waitingTime + " milliseconds");
        }
    }

    public synchronized void carParking() throws InterruptedException {
        if (spots.isBusy()) {
            waitTime();
            synchronized (System.out) {
                System.out.println(name + " from Gate " + this.gateNO + " parked after waiting" + " for a spot");
            }
        } else {
            spots.acquire();
            Thread.sleep(parkingTime);
        }
        synchronized (System.out) {
            System.out.println(" Parking Status: " + (4 - spots.availablePermits().get()) + " spots occupied");
        }

    }

    public synchronized void carLeaving() throws InterruptedException {
        spots.release();
        synchronized (System.out) {
            System.out.println(name + " from Gate " + this.gateNO + " left after " + this.parkingTime + " milliseconds");
            System.out.println(" Parking Status: " + (4 - spots.availablePermits().get()) + " spots occupied");
        }
    }

    public void run() {
        carArrival();
        try {
            carParking();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            carLeaving();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
