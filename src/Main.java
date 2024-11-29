import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File file = new File("input.txt");
        ArrayList<CarThread> carThreads = new ArrayList<>();

        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()) {

            String line = reader.nextLine().replaceAll(",", "");
            String[] splitWords = line.split(" ");
            String carName = splitWords[2] + " " + splitWords[3];
            int gateNo = Integer.parseInt(splitWords[1]);
            int arrivalTime = Integer.parseInt(splitWords[5]);
            int parkingTime = Integer.parseInt(splitWords[7]);

            CarThread carThread = new CarThread(carName, gateNo, arrivalTime, parkingTime);
            carThreads.add(carThread);
        }


        carThreads.sort(Comparator.comparingInt(CarThread::getArrivalTime));
//        for (CarThread carThread : carThreads) {
//            System.out.println(carThread.getName() + " " +  carThread.getArrivalTime());
//        }
        for (CarThread carThread : carThreads) {
            carThread.start();
        }

        //carThreads.forEach(carThread -> carThread.start());


    }
}




