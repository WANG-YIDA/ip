package app.model.command;

import app.model.TaskList;

public abstract class Command {
    public abstract void execute(TaskList taskList);
}
