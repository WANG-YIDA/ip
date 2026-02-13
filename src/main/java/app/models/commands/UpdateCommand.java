package app.models.commands;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.models.TaskList;

/**
 * Command to update a task in the task list.
 */
public class UpdateCommand extends Command {
    private String argument;

    /**
     * Constructs an UpdateCommand with the user-provided argument.
     *
     * @param argument the raw event description
     */
    public UpdateCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the command by update a task of the provided task list
     * and printing the result. Errors are caught and printed.
     *
     * @param taskList the task list to modify
     */
    @Override
    public String execute(TaskList taskList) {
        try {
            String updateResult = taskList.updateTask(argument);
            return updateResult;
        } catch (InvalidPatternException | IOException e) {
            return e.getMessage();
        }
    }
}
