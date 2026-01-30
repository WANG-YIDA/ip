package app.model.command;

import app.model.TaskList;

/**
 * Base class for all user commands.
 */
public abstract class Command {
    /**
     * Execute the command using the supplied TaskList.
     *
     * @param taskList the task list the command should operate on
     */
    public abstract void execute(TaskList taskList);
}
