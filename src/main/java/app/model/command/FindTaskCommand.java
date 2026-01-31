package app.model.command;

import app.model.TaskList;
import app.ui.Ui;

/**
 * Command to find tasks matching a keyword and print the matching list.
 */
public class FindTaskCommand extends Command {
    private String argument;

    public FindTaskCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Find a task with keyword in argument
        String findResult = taskList.printMatchedList(argument);
        String findMsg = Ui.printWrappedMessage(findResult);
        System.out.print(findMsg);
    }
}
