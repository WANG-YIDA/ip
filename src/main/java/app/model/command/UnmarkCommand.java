package app.model.command;

import app.exception.InvalidPatternException;
import app.model.TaskList;
import app.ui.Ui;

import java.io.IOException;

public class UnmarkCommand extends Command {
    private String argument;

    public UnmarkCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Mark a task as undone
        try {
            String unmarkResult = taskList.unmark(argument);
            String unmarkMsg = Ui.printWrappedMessage(unmarkResult);
            System.out.print(unmarkMsg);
        } catch (InvalidPatternException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
