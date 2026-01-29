package app.model.command;

import app.model.TaskList;
import app.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList taskList) {
        String exitMessage = Ui.printWrappedMessage(" Bye! Hope to see you again soon:)");
        System.out.print(exitMessage);
        System.exit(0);
    }
}
