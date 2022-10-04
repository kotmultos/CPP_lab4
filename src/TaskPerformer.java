import manager.InputManager;
import manager.OutputManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskPerformer {
    private static List<String> inputText;
    private static List<String> dateFormats = Arrays.asList("MM/dd/yyyy",
            "MM-dd-yyyy", "MM.dd.yyyy", "dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy");

    public static void Perform(String filename) {
        // get input data
        inputText = InputManager.ReadFile(filename);

        OutputManager.PrintStingList("Стрічки, отримані з файлу " + filename + ":", inputText);

        // find date in any possible format & remember its position [line, indexInLine];
        // split line by spaces and brackets  and check whether each word is a Date
        Map<Date, SimpleDateFormat> datesMap = TaskPerformer.GetDatesMap();
        OutputManager.PrintDatesMap("Знайдено такі дати у файлі: ", datesMap);

        // get the range of dates range = max - min

        // change each date to the next day


        // insert renewed values & insert Day of the week after them

    }

    private static Map<Date, SimpleDateFormat> GetDatesMap() {
        Map<Date, SimpleDateFormat> resMap = new LinkedHashMap<>();
        for (var line: inputText) {
            String lineWithoutExtraSpaces = TaskPerformer.RemoveSpaces(line);
            System.out.println(lineWithoutExtraSpaces); // to be removed later
            List<String> words = TaskPerformer.SplitLine(lineWithoutExtraSpaces);
            System.out.println(words); // to be removed later

            for (var word: words) {
                // check is each word is a date
                if(TaskPerformer.IsWordADate(word)) { //  current word is a date
                    System.out.println("--");
                    System.out.println();
                    TaskPerformer.ConvertStringToDateAndFormat(resMap, word);
                    // now it adds dates in 2 different formats
                    // because some of them are meant by program as the same,
                    // so we have them all together
                    //
                    // lets try to use java.util.Calendar instead of java.util.Date
                    // or to compare Date.toString() to String representation of current word
                    // if so, add
                    // else don't
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
                resMap.put(date, format);
            }
            catch (ParseException e) {
                System.out.println("exception: " + word + "\tformat: " + format.toString()); // to be removed later
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
                System.out.println("exception: " + word + "\tformat: " + format.toString()); // to be removed later
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
