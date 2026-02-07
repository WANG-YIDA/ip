package app.models.command;

import app.models.TaskList;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Executes the command by retrieving and printing the formatted task list.
     *
     * @param taskList the task list to read from
     */
    @Override
    public String execute(TaskList taskList) {
        // Print task list
        String listResult = taskList.printList();
        return listResult;
    }
}
