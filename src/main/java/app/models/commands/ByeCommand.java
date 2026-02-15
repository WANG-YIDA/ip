package app.models.commands;

import app.models.TaskList;

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
    public String execute(TaskList taskList) {
        System.exit(0);
        return "See ya:)";
    }
}
