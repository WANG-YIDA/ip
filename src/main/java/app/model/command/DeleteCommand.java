package app.model.command;

import app.exception.InvalidPatternException;
import app.model.TaskList;
import app.ui.Ui;

import java.io.IOException;

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
    public void execute(TaskList taskList) {
        // Delete a task
        try {
            String deleteResult = taskList.delete(argument);
            String deleteMsg = Ui.printWrappedMessage(deleteResult);
            System.out.print(deleteMsg);
        } catch (InvalidPatternException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
