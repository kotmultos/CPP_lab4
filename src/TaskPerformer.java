import manager.InputManager;
import manager.OutputManager;

import java.util.List;

public class TaskPerformer {
    private static List<String> inputText;

    public static void Perform(String filename) {
        // get input data
        inputText = InputManager.ReadFile(filename);
        OutputManager.PrintStingList("Стрічки, отримані з файлу " + filename + ":", inputText);

        // find date in any possible format & remember its position [line, indexInLine];


        // get the range of dates range = max - min

        // change each date to the next day


        // insert renewed values & insert Day of the week after them

    }
}
