package app.models.commands;

import java.io.IOException;

import app.exceptions.InvalidPatternException;
import app.exceptions.InvalidTaskTypeException;
import app.exceptions.MissingComponentException;
import app.exceptions.RequestRejectedException;
import app.models.TaskList;
import app.models.tasks.TaskType;

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
    public String execute(TaskList taskList) {
        // Add task of type To-do
        try {
            String addTodoTaskResult = taskList.addTask(argument, TaskType.TODO);
            return addTodoTaskResult;
        } catch (InvalidPatternException
                 | MissingComponentException
                 | RequestRejectedException
                 | IOException
                 | InvalidTaskTypeException e) {
            return e.getMessage();
        }
    }
}
