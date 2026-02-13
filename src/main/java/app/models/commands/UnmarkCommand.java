package app.models.commands;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.models.TaskList;

/**
 * Command to mark a task as not completed (undo mark).
 */
public class UnmarkCommand extends Command {
    private String argument;

    /**
     * Creates an UnmarkCommand.
     *
     * @param argument identifier or index of the task to unmark
     */
    public UnmarkCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the command by unmarking the specified task and printing the
     * result. Errors are handled and printed.
     *
     * @param taskList the task list to operate on
     */
    @Override
    public String execute(TaskList taskList) {
        // Mark a task as undone
        try {
            String unmarkResult = taskList.unmarkTask(argument);
            return unmarkResult;
        } catch (InvalidPatternException | IOException e) {
            return e.getMessage();
        }
    }
}
