import java.sql.SQLOutput;
import java.util.concurrent.Semaphore;

public class CarThread extends Thread {
    String name;
    int gateNO;
    int arrivalTime;
    long waitingTime = 0;
    int parkingTime;
    static Sema spots = new Sema(4);


    public CarThread(String name, int gateNO, int arrivalTime, int parkingTime) {
        this.name = name;
        this.gateNO = gateNO;
        this.arrivalTime = arrivalTime;
        this.parkingTime = parkingTime;
    }


    public void carArrival(){
        System.out.println(name + " from Gate " + gateNO + " at time " + arrivalTime );
    }

    public void waitTime(){
        System.out.println(name + "from Gate " + this.gateNO + " waiting for a spot");
        long startTime = System.currentTimeMillis();
        while (spots.availablePermits() == 0);
        long endTime = System.currentTimeMillis();
        this.waitingTime = endTime - startTime;
    }

    public void carParking() throws InterruptedException {
        boolean isWaiting = false;
        while (spots.isBusy()) {
            isWaiting = true;
            waitTime();
            System.out.println(name + "from Gate " + this.gateNO + " parked after " + this.waitingTime + " milliseconds");
        }

        if(!isWaiting){
            System.out.println(name + "from Gate " + this.gateNO + " parked");
        }
        spots.acquire();
        System.out.println("Parking Status: " + spots.availablePermits() + " spots occupied");
        Thread.sleep(parkingTime);
    }

    public void carLeaving() throws InterruptedException {
        spots.release();
        System.out.print(name + "from Gate " + this.gateNO + " left after " + this.parkingTime + " milliseconds");
        System.out.println("Parking Status: " + spots.availablePermits() + " spots occupied");
    }

    public void run(){
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
