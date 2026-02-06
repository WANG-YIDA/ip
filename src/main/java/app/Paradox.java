package app;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import app.exception.InvalidCommandException;
import app.model.TaskList;
import app.model.command.Command;
import app.ui.Ui;

/**
 * Main entry point for the Paradox application.
 */
public class Paradox {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // create a task list and specific read source and write destination
        TaskList taskList = null;
        try {
            String taskListPath = "./src/main/resources/taskList.txt";
            taskList = new TaskList(taskListPath);
        } catch (FileNotFoundException | DataFormatException e) {
            String errMsg = Ui.printWrappedMessage(
                    String.format(" Error: %s. Please try again later:(", e.getMessage()));
            System.out.print(errMsg);

            System.exit(0);
        }

        // Print welcome message
        String welcomeMessage = Ui.printWrappedMessage(
                String.format(" Hi! I'm %s!\n What can I do for you:)", "Paradox"));
        System.out.print(welcomeMessage);

        // Handle user commands
        String userInput = scanner.nextLine();
        while (true) {
            try {
                Command command = UserInputParser.parse(userInput);
                command.execute(taskList);
            } catch (InvalidCommandException e) {
                System.out.print(Ui.printWrappedMessage(e.getMessage()));
            }

            // Read next user input
            userInput = scanner.nextLine();
        }
    }
}
