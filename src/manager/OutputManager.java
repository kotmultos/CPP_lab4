package manager;

import java.util.List;

public class OutputManager {

    public static void PrintStingList(String description, List<String> inputText) {
        System.out.println(description);
        System.out.println("--");
        for(var line: inputText) {
            System.out.println(line);
        }
        System.out.println("--");
    }
}
