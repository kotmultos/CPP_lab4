package manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringManager {
    private static final String [] dateFormats = {"MM/dd/yyyy",
            "MM-dd-yyyy", "MM.dd.yyyy", "dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy"};

    public static void ConvertStringToDateAndFormat(List<DateAndFormat> resList, String word) {
        for (var item : dateFormats) {
            SimpleDateFormat format = new SimpleDateFormat(item);
            try {
                Date date = format.parse(word);
                if( word.equals(format.format(date))) {
                    resList.add(new DateAndFormat(date, format));
                }
            }
            catch (ParseException e) {
//                System.out.println("exception: " + word + "\tformat: " + format.toString()); // to be removed later
            }
        }
    }

    public static boolean IsWordADate(String word) {
        boolean res = false;
        for (var item : dateFormats) {
            SimpleDateFormat format = new SimpleDateFormat(item);
            try {
                format.parse(word);
                res = true;
            }
            catch (ParseException e) {
//                System.out.println("exception: " + word + "\tformat: " + format.toString()); // to be removed later
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
    public static List<String> SplitLine(String line) {
        List<String> res = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if(!StringManager.IsCharAtASpacer(line, i)) {
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
