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
    public void execute(TaskList taskList) {
        // Add task of type Event
        try {
            String addEventTaskResult = taskList.addTask(argument, TaskType.EVENT);
            String addEventTaskMsg = Ui.printWrappedMessage(addEventTaskResult);
            System.out.print(addEventTaskMsg);
        } catch (InvalidPatternException
                 | MissingComponentException
                 | RequestRejectedException
                 | IOException
                 | InvalidTaskTypeException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
