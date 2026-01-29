package app.model.command;

import app.model.TaskList;
import app.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList) {
        // Print task list
        String listResult = taskList.printList();
        String listMsg = Ui.printWrappedMessage(listResult);
        System.out.print(listMsg);
    }
}
