package app.exception;

import app.model.Command;
import app.model.TaskList;
import app.model.TaskType;
import app.ui.Ui;

import java.io.IOException;

public class AddEventCommand extends Command {
    private String argument;

    public AddEventCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Add task of type Event
        try {
            String addEventTaskResult = taskList.addTask(argument, TaskType.EVENT);
            String addEventTaskMsg = Ui.printWrappedMessage(addEventTaskResult);
            System.out.print(addEventTaskMsg);
        } catch (InvalidPatternException | MissingComponentException | RequestRejectedException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
