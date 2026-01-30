package app.model.command;

import app.model.TaskList;
import app.ui.Ui;

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
    public void execute(TaskList taskList) {
        // Print task list
        String listResult = taskList.printList();
        String listMsg = Ui.printWrappedMessage(listResult);
        System.out.print(listMsg);
    }
}
