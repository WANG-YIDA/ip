package app;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import app.models.TaskList;
import app.models.commands.Command;
import app.parsers.UserInputParser;

/** Paradox main application. */
public class Paradox {
    private TaskList taskList;

    /**
     * Loads tasks from the given file path.
     *
     * @param taskListPath path to the stored task list
     * @return welcome message on success, or an error message on failure
     */
    public String initialize(String taskListPath) {
        // create a task list and specific read source and write destination
        try {
            taskList = new TaskList(taskListPath);
        } catch (FileNotFoundException | DataFormatException e) {
            String errMsg = String.format(" Something went wrong: %s. Please try again later:(", e.getMessage());
            return errMsg;
        }

        // Print welcome message
        String welcomeMessage = String.format(" Hi there! I'm %s!\n Where should we start today:)", "Paradox");
        return welcomeMessage;
    }

    /**
     * Parses and executes a single user input line.
     *
     * @param userInput raw user input
     * @return the result message (success or error) to show to the user
     */
    public String getResponse(String userInput) {
        // Handle user commands
        try {
            Command command = UserInputParser.parse(userInput);
            String response = command.execute(taskList);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
