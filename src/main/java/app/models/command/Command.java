package app.models.command;

import app.models.TaskList;

/**
 * Base class for all user commands.
 */
public abstract class Command {
    /**
     * Execute the command using the supplied TaskList and return the result.
     *
     * @param taskList the task list the command should operate on
     * @return the result of the execution
     */
    public abstract String execute(TaskList taskList);
}
