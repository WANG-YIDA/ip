package app.exception;

import app.model.Command;
import app.model.TaskList;
import app.model.TaskType;
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
                 IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
