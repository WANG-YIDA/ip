package app.model.command;

import app.exception.InvalidPatternException;
import app.exception.InvalidTaskTypeException;
import app.exception.MissingComponentException;
import app.exception.RequestRejectedException;
import app.model.TaskList;
import app.model.task.TaskType;
import app.ui.Ui;

import java.io.IOException;

public class AddTodoCommand extends Command {
    private String argument;

    public AddTodoCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Add task of type To-do
        try {
            String addTodoTaskResult = taskList.addTask(argument, TaskType.TODO);
            String addTodoTaskMsg = Ui.printWrappedMessage(addTodoTaskResult);
            System.out.print(addTodoTaskMsg);
        } catch (InvalidPatternException | MissingComponentException | RequestRejectedException |
                 IOException | InvalidTaskTypeException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
