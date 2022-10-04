package manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OutputManager {

    public static void PrintStingList(String description, List<String> inputText) {
        System.out.println(description);
        System.out.println("--");
        for(var line: inputText) {
            System.out.println(line);
        }
        System.out.println("--");
    }

    public static void PrintDatesMap(String description, Map<Date, SimpleDateFormat> datesMap) {
        System.out.println(description);
        for (var date: datesMap.keySet()) {
            System.out.println(datesMap.get(date).format(date));
        }
    }
}
