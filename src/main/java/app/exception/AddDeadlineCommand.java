package app.exception;

import app.model.Command;
import app.model.TaskList;
import app.model.TaskType;
import app.ui.Ui;

import java.io.IOException;

public class AddDeadlineCommand extends Command {
    private String argument;

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
        } catch (InvalidPatternException | MissingComponentException | RequestRejectedException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
