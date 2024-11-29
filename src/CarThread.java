public class CarThread extends Thread {
    private final String name;
    private final int gateNO;
    private final int arrivalTime;
    private long waitingTime = 0;
    private final int parkingTime;
    private static final Semaphore spots = new Semaphore(4);


    public CarThread(String name, int gateNO, int arrivalTime, int parkingTime) {
        this.name = name;
        this.setName(name);
        this.gateNO = gateNO;
        this.arrivalTime = arrivalTime;
        this.parkingTime = parkingTime;
    }

    public String getname() {
        return name;
    }

    public int getGateNO() {
        return gateNO;
    }

    public int getParkingTime() {
        return parkingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void carArrival() {
        synchronized (System.out) {
            System.out.println(name + " from Gate " + gateNO + " arrived at time " + arrivalTime);
        }
    }

    public void waitTime() throws InterruptedException {
        long startTime = System.currentTimeMillis() * 1000L;
        System.out.println(name + " from Gate " + gateNO + " waiting for a spot");
        spots.acquire(this);
        long endTime = System.currentTimeMillis() * 1000L;
        this.waitingTime = endTime - startTime;

    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public synchronized void carParking() throws InterruptedException {
        spots.acquire(this);
        Thread.sleep(parkingTime * 1000L);
    }

    public synchronized void carLeaving() throws InterruptedException {
        spots.release(this, this.parkingTime);
    }

    public void run() {
        try {
            Thread.sleep(arrivalTime * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
