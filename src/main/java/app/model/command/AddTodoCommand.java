package app.model.command;

import java.io.IOException;

import app.exception.InvalidPatternException;
import app.exception.InvalidTaskTypeException;
import app.exception.MissingComponentException;
import app.exception.RequestRejectedException;
import app.model.TaskList;
import app.model.task.TaskType;
import app.ui.Ui;

import java.io.IOException;

/**
 * Command to add a to-do task to the task list.
 */
public class AddTodoCommand extends Command {
    private String argument;

    /**
     * Creates an AddTodoCommand with the raw argument text.
     *
     * @param argument the user-provided task description
     */
    public AddTodoCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the command by adding a todo task to the provided task list and
     * printing the result to standard output. Any validation or I/O errors are
     * handled and printed.
     *
     * @param taskList the task list to modify
     */
    @Override
    public void execute(TaskList taskList) {
        // Add task of type To-do
        try {
            String addTodoTaskResult = taskList.addTask(argument, TaskType.TODO);
            String addTodoTaskMsg = Ui.printWrappedMessage(addTodoTaskResult);
            System.out.print(addTodoTaskMsg);
        } catch (InvalidPatternException | MissingComponentException |
                 RequestRejectedException | IOException | InvalidTaskTypeException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
