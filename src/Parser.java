import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    private static String[] tokens;
    public void parse(File inputFile) throws FileNotFoundException {
        StringBuilder output = new StringBuilder();
        Scanner reader = new Scanner(inputFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ',') {

                }

            }
//           tokens = line.trim().split("\\s+");
            output.append(line).append("\n");
        }
        reader.close();
        System.out.println(output.toString());
    }











}
