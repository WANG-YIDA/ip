package app.models.commands;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.exceptions.InvalidTaskTypeException;
import app.exceptions.MissingComponentException;
import app.exceptions.RequestRejectedException;
import app.models.TaskList;
import app.models.tasks.TaskType;

/**
 * Command to add an event task to the task list.
 */
public class AddEventCommand extends Command {
    private String argument;

    /**
     * Constructs an AddEventCommand with the user-provided argument.
     *
     * @param argument the raw event description
     */
    public AddEventCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the command by adding an event task to the provided task list
     * and printing the result. Errors are caught and printed.
     *
     * @param taskList the task list to modify
     */
    @Override
    public String execute(TaskList taskList) {
        // Add task of type Event
        try {
            String addEventTaskResult = taskList.addTask(argument, TaskType.EVENT);
            return addEventTaskResult;
        } catch (InvalidPatternException
                 | MissingComponentException
                 | RequestRejectedException
                 | IOException
                 | InvalidTaskTypeException e) {
            return e.getMessage();
        }
    }
}
