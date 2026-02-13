package app.models.commands;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.models.TaskList;

/**
 * Command to mark a task as completed.
 */
public class MarkCommand extends Command {
    private String argument;

    /**
     * Creates a MarkCommand.
     *
     * @param argument identifier or index of the task to mark
     */
    public MarkCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the command by marking the specified task and printing the
     * result. Errors are handled and printed.
     *
     * @param taskList the task list to operate on
     */
    @Override
    public String execute(TaskList taskList) {
        // Mark a task as done
        try {
            String markResult = taskList.markTask(argument);
            return markResult;
        } catch (InvalidPatternException | IOException e) {
            return e.getMessage();
        }
    }
}
