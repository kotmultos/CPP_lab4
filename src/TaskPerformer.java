import manager.DateAndFormat;
import manager.InputManager;
import manager.OutputManager;
import manager.StringManager;

import java.util.*;

public class TaskPerformer {
    private static List<String> inputText;
    private static final String [] weekDaysEngl = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String [] weekDaysUkr = {"Неділя", "Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота"};
    private static String [] weekDays;
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    public static void Perform(String filename) {
        // get input data
        inputText = InputManager.ReadFile(filename);
        // set localization
        weekDays = filename.contains("Ukr") ? weekDaysUkr : weekDaysEngl;

        OutputManager.PrintStingList("Стрічки, отримані з файлу " + filename + ":", inputText);

        // find date in any possible format & remember its position [line, indexInLine];
        // split line by spaces and brackets  and check whether each word is a Date
        List<DateAndFormat> datesList = TaskPerformer.GetDatesList();
        OutputManager.PrintDatesList("Знайдено такі дати у файлі: ", datesList);

        // get the range of dates range = max - min
        TaskPerformer.FindDatesRange(datesList);

        // insert renewed values & insert Day of the week after them
        TaskPerformer.InsertNewDates(datesList);
        OutputManager.PrintStingList("Результат роботи програми: ", inputText);
        OutputManager.SaveStringListToFile("result.txt", inputText);
    }

    private static List<DateAndFormat> GetDatesList() {
        List<DateAndFormat> resList = new ArrayList<>();
        for (var line: inputText) {
            String lineWithoutExtraSpaces = StringManager.RemoveSpaces(line);
            List<String> words = StringManager.SplitLine(lineWithoutExtraSpaces);

            for (var word: words) {
                // check is each word is a date
                if(StringManager.IsWordADate(word)) { //  current word is a date
                    StringManager.ConvertStringToDateAndFormat(resList, word);
                }
            }
        }
        return resList;
    }

    private static void InsertNewDates(List<DateAndFormat> datesList) {
        for(int i = 0; i < inputText.size(); i++) {
            for (var item: datesList) {
                int pos = inputText.get(i).indexOf(item.dateFormat.format(item.date)) ;

                if(pos != -1) {
                    String strToInsert = TaskPerformer.GetStringToInsert(item.dateFormat.format(item.date), datesList);
                    StringBuilder tmp = new StringBuilder(inputText.get(i));
                    StringBuilder newLine = tmp.replace(pos, pos + item.dateFormat.format(item.date).length(), strToInsert);

                    inputText.set(i, newLine.toString());
                }
            }
        }
    }

    private static String GetStringToInsert(String template, List<DateAndFormat> datesList) {
        StringBuilder res = new StringBuilder();

        for (var item : datesList) {
            if(item.dateFormat.format(item.date).equals(template)) {  // if equal
                Date nextDay = new Date(item.date.getTime() + MILLIS_IN_A_DAY); // change date to the next day
                res.append(item.dateFormat.format(nextDay)); // format next day with the same format
                res.append(" (");
                res.append(weekDays[nextDay.getDay()]);
                res.append(") ");
            }
        }
        return res.toString();
    }

    private static void FindDatesRange(List<DateAndFormat> list) {
        Set<Date> dateSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            dateSet.add(list.get(i).date);
        }

        Date last = dateSet.iterator().next();
        Date first = last;

        for (var item : dateSet) {
            if(item.compareTo(last) > 0) last = item;
            if(item.compareTo(first) < 0) first = item;
        }

        long difference_In_Time = last.getTime() - first.getTime();

        // Calucalte time difference in years and days
        long difference_In_Years
                = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));

        long difference_In_Days
                = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        OutputManager.PrintDatesRange(first, last, difference_In_Years, difference_In_Days);
    }
}
