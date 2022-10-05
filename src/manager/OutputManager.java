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
            System.out.print(datesMap.get(date).format(date));
            System.out.println("\t" + date);
        }
    }

    public static void PrintDatesRange(Date first, Date last, long difference_in_years, long difference_in_days) {
        System.out.println("Діапазон дат: ");
        System.out.println("Найперша дата: " + first);
        System.out.println("Найперша дата: " + last);
        System.out.println("Діапазон: " + difference_in_years + " р. " + difference_in_days + " д.");
    }
}
