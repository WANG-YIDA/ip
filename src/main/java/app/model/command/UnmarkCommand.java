package app.model.command;

import app.exception.InvalidPatternException;
import app.model.TaskList;
import app.ui.Ui;

import java.io.IOException;

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
    public void execute(TaskList taskList) {
        // Mark a task as undone
        try {
            String unmarkResult = taskList.unmark(argument);
            String unmarkMsg = Ui.printWrappedMessage(unmarkResult);
            System.out.print(unmarkMsg);
        } catch (InvalidPatternException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
