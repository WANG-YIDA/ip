package app.model.task;

public class StubTodoTask extends Task {
    private final String taskContent;
    private final boolean isDone;

    public StubTodoTask(String taskContent, boolean isDone) {
        this.taskContent = taskContent;
        this.isDone = isDone;
    }

    @Override
    public String printStorageString() {
        int isDone = this.isDone ? 1 : 0;
        return String.format("%d/T/%s///", isDone, this.taskContent);
    }

    @Override
    public String printTask() {
        return String.format("[%s] %s", this.isDone ? "X" : " ", this.taskContent);
    }
}
