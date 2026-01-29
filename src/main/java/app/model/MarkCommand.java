package app.model;

import app.exception.InvalidPatternException;
import app.ui.Ui;

import java.io.IOException;

public class MarkCommand extends Command {
    private String argument;

    public MarkCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList taskList) {
        // Mark a task as done
        try {
            String markResult = taskList.mark(argument);
            String markMsg = Ui.printWrappedMessage(markResult);
            System.out.print(markMsg);
        } catch (InvalidPatternException | IOException e) {
            String errMsg = Ui.printWrappedMessage(e.getMessage());
            System.out.print(errMsg);
        }
    }
}
