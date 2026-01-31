package app.model.command;

import java.io.IOException;

import app.exception.InvalidPatternException;
import app.model.TaskList;
import app.ui.Ui;

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
    public void execute(TaskList taskList) {
        // Mark a task as done
        try {
            String markResult = taskList.mark(argument);
            String markMsg = Ui.printWrappedMessage(markResult);
            System.out.print(markMsg);
        } catch (InvalidPatternException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
