package app.models.command;

import app.models.TaskList;

/**
 * Command to find tasks matching a keyword and print the matching list.
 */
public class FindTaskCommand extends Command {
    private String argument;

    public FindTaskCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public String execute(TaskList taskList) {
        // Find a task with keyword in argument
        String findResult = taskList.printMatchedList(argument);
        return findResult;
    }
}
