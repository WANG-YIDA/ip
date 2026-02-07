package app.models.command;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.exceptions.InvalidTaskTypeException;
import app.exceptions.MissingComponentException;
import app.exceptions.RequestRejectedException;
import app.models.TaskList;
import app.models.task.TaskType;

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
    public String execute(TaskList taskList) {
        // Add task of type Deadline
        try {
            String addDeadlineTaskResult = taskList.addTask(argument, TaskType.DEADLINE);
            return addDeadlineTaskResult;
        } catch (InvalidPatternException | MissingComponentException
                 | RequestRejectedException | IOException | InvalidTaskTypeException e) {
            return e.getMessage();
        }
    }
}
