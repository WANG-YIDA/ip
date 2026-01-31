package app.model.command;

import java.io.IOException;

import app.exception.InvalidPatternException;
import app.exception.InvalidTaskTypeException;
import app.exception.MissingComponentException;
import app.exception.RequestRejectedException;
import app.model.TaskList;
import app.model.task.TaskType;
import app.ui.Ui;

/**
 * Command to add a deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {
    private String argument;

    /**
     * Creates an AddDeadlineCommand.
     *
     * @param argument raw argument text for deadline task
     */
    public AddDeadlineCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Add task of type Deadline
        try {
            String addDeadlineTaskResult = taskList.addTask(argument, TaskType.DEADLINE);
            String addDeadlineTaskMsg = Ui.printWrappedMessage(addDeadlineTaskResult);
            System.out.print(addDeadlineTaskMsg);
        } catch (InvalidPatternException | MissingComponentException
                 | RequestRejectedException | IOException | InvalidTaskTypeException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
