package app.models.commands;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.models.TaskList;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private String argument;

    /**
     * Creates a DeleteCommand.
     *
     * @param argument identifier or index of the task to delete
     */
    public DeleteCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the command by deleting the specified task and printing the
     * result. Errors are handled and printed.
     *
     * @param taskList the task list to operate on
     */
    @Override
    public String execute(TaskList taskList) {
        // Delete a task
        try {
            String deleteResult = taskList.delete(argument);
            return deleteResult;
        } catch (InvalidPatternException | IOException e) {
            return e.getMessage();
        }
    }
}
