import java.util.concurrent.Semaphore;

public class Sema extends Semaphore {
    public Sema(int permits) {
        super(permits);
    }

    public boolean isBusy(){
        return (this.availablePermits() == 0);
    }

}
