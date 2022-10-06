import manager.InputManager;
import manager.OutputManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskPerformer {
    private static List<String> inputText;
    private static List<String> dateFormats = Arrays.asList("MM/dd/yyyy",
            "MM-dd-yyyy", "MM.dd.yyyy", "dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy");

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    public static void Perform(String filename) {
        // get input data
        inputText = InputManager.ReadFile(filename);

        OutputManager.PrintStingList("Стрічки, отримані з файлу " + filename + ":", inputText);

        // find date in any possible format & remember its position [line, indexInLine];
        // split line by spaces and brackets  and check whether each word is a Date
        Map<Date, SimpleDateFormat> datesMap = TaskPerformer.GetDatesMap();
        OutputManager.PrintDatesMap("Знайдено такі дати у файлі: ", datesMap);

        // get the range of dates range = max - min
        TaskPerformer.FindDatesRange(datesMap);

        // insert renewed values & insert Day of the week after them
        TaskPerformer.InsertNewDates(datesMap);
        OutputManager.PrintStingList("Результат роботи програми: ", inputText);
    }

    private static void InsertNewDates(Map<Date, SimpleDateFormat> datesMap) {
        for(int i = 0; i < inputText.size(); i++) {
            for (var item: datesMap.keySet()) {
                int pos = inputText.get(i).indexOf(datesMap.get(item).format(item)) ;

                if(pos != -1) {
                    String strToInsert = TaskPerformer.GetStringToInsert(datesMap.get(item).format(item), datesMap);
//                    System.out.println(strToInsert);
//                    String newLine = insertString(inputText.get(i), strToInsert, pos);
                    StringBuilder tmp = new StringBuilder(inputText.get(i));
                    StringBuilder newLine = tmp.replace(pos, pos + datesMap.get(item).format(item).length(), strToInsert);

//                    System.out.println(newLine );
                    inputText.set(i, newLine.toString());
                }
//                break;
            }
//            break;
        }
    }

    private static String GetStringToInsert(String template, Map<Date, SimpleDateFormat> datesMap) {
        StringBuilder res = new StringBuilder();

        for (var item : datesMap.keySet()) {
//            System.out.println("current elem:" + datesMap.get(item).format(item));
//            System.out.println("template: " + template);
            if(datesMap.get(item).format(item).equals(template)) {  // if equal
                Date nextDay = new Date(item.getTime() + MILLIS_IN_A_DAY); // change date to the next day
                res.append(datesMap.get(item).format(nextDay)); // format next day with the same format
                res.append(" day: ");
                res.append(nextDay.getDay());
                res.append(" ");
//                System.out.println("true");
            }
        }
        return res.toString();
    }

    private static void FindDatesRange(Map<Date, SimpleDateFormat> datesMap) {
        Set<Date> dateSet = new HashSet<>(datesMap.keySet());

        Date last = dateSet.iterator().next();
        Date first = last;

        for (var item : dateSet) {
            if(item.compareTo(last) > 0) last = item;
            if(item.compareTo(first) < 0) first = item;
        }

        long difference_In_Time = last.getTime() - first.getTime();
        // Calucalte time difference in
        // years and days

        long difference_In_Years
                = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));

        long difference_In_Days
                = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        OutputManager.PrintDatesRange(first, last, difference_In_Years, difference_In_Days);

    }

    private static Map<Date, SimpleDateFormat> GetDatesMap() {
        Map<Date, SimpleDateFormat> resMap = new LinkedHashMap<>();
        for (var line: inputText) {
            String lineWithoutExtraSpaces = TaskPerformer.RemoveSpaces(line);
//            System.out.println(lineWithoutExtraSpaces); // to be removed later
            List<String> words = TaskPerformer.SplitLine(lineWithoutExtraSpaces);
//            System.out.println(words); // to be removed later

            for (var word: words) {
                // check is each word is a date
                if(TaskPerformer.IsWordADate(word)) { //  current word is a date
//                    System.out.println("--");
                    TaskPerformer.ConvertStringToDateAndFormat(resMap, word);
                }
//                break; // to be removed later
            }
            break; // to be removed later
        }
        return resMap;
    }

    private static void ConvertStringToDateAndFormat(Map<Date, SimpleDateFormat> resMap, String word) {
        for (var item : dateFormats) {
            SimpleDateFormat format = new SimpleDateFormat(item);
            try {
                Date date = format.parse(word);
                if( word.equals(format.format(date))) {
                    resMap.put(date, format);
                }
            }
            catch (ParseException e) {
//                System.out.println("exception: " + word + "\tformat: " + format.toString()); // to be removed later
                continue;
            }
        }
    }

    private static boolean IsWordADate(String word) {
        boolean res = false;
        for (var item : dateFormats) {
            SimpleDateFormat format = new SimpleDateFormat(item);
            try {
                format.parse(word);
                res = true;
            }
            catch (ParseException e) {
//                System.out.println("exception: " + word + "\tformat: " + format.toString()); // to be removed later
                continue;
            }
        }
        return res;
    }

    public static String RemoveSpaces(String line) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            if(i + 1 < line.length() && !(line.charAt(i) == ' ' && line.charAt(i + 1) == ' ')) {
                res.append(line.charAt(i));
            } else if(i + 1 == line.length()) {
                res.append(line.charAt(i));
            }
        }

        return res.toString();
    }
    private static List<String> SplitLine(String line) {
        List<String> res = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if(!TaskPerformer.IsCharAtASpacer(line, i)) {
                stringBuilder.append(line.charAt(i));
            } else if(stringBuilder.length() != 0) {
                res.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
        return res;
    }

    public static boolean IsCharAtASpacer(String line, int pos) {
        return     line.charAt(pos) == ' ' || line.charAt(pos) == ','
                || line.charAt(pos) == '[' || line.charAt(pos) == ']'
                || line.charAt(pos) == '{' || line.charAt(pos) == '}'
                || line.charAt(pos) == '(' || line.charAt(pos) == ')'
                || (pos == line.length() - 1 && (
                        line.charAt(pos) == '.' || line.charAt(pos) == '!' || line.charAt(pos) == '?'
                ));
    }
}
