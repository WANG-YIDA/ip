package app.model;

import app.exception.InvalidPatternException;
import app.ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {
    private String argument;

    public DeleteCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Delete a task
        try {
            String deleteResult = taskList.delete(argument);
            String deleteMsg = Ui.printWrappedMessage(deleteResult);
            System.out.print(deleteMsg);
        } catch (InvalidPatternException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
