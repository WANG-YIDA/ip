package app.model.command;

import app.model.TaskList;
import app.ui.Ui;

/**
 * Command to exit the application.
 */
public class ByeCommand extends Command {
    /**
     * Executes the command by printing a farewell message and terminating the
     * JVM.
     *
     * @param taskList unused
     */
    @Override
    public void execute(TaskList taskList) {
        String exitMessage = Ui.printWrappedMessage(" Bye! Hope to see you again soon:)");
        System.out.print(exitMessage);
        System.exit(0);
    }
}
