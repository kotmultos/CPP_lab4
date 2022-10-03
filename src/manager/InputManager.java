package manager;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputManager {
    public static List<String> ReadFile(String filename) {
        ArrayList<String> res = new ArrayList<String>();

        try (FileReader reader = new FileReader(filename)) {
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                String tmp = scan.nextLine();
                res.add(tmp);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from file./// filename: " + filename);
            System.out.println(e);
            return new ArrayList<>();
        }
        return res;
    }
}
