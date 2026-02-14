package app.models.tasks;

import app.exceptions.InvalidPatternException;

/**
 * Test stub representing a simple Todo task used in unit tests.
 */
public class StubTodoTask extends Task {
    private final String taskContent;
    private final boolean isDone;

    /**
     * Creates a stub todo task.
     *
     * @param taskContent visible content
     * @param isDone      completion flag
     */
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

    @Override
    public Boolean contains(String keyword) {
        return this.taskContent.contains(keyword);
    }

    @Override
    public void update(String updateDetails) throws InvalidPatternException {}
}
