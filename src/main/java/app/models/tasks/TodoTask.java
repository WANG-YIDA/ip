package app.models.tasks;

import app.exceptions.InvalidPatternException;
import app.parsers.UpdateDetailsParser;

/**
 * Represents a simple to-do task (no date/time).
 */
public class TodoTask extends Task {
    private String taskContent;

    /**
     * Constructs a not-yet-completed TodoTask.
     *
     * @param taskContent task description
     */
    public TodoTask(String taskContent) {
        super();
        assert taskContent != null : "Task content cannot be null";
        assert !taskContent.trim().isEmpty() : "Task content cannot be empty";
        this.taskContent = taskContent;
    }

    /**
     * Constructs a TodoTask with specified completion state.
     *
     * @param taskContent task description
     * @param isDone      initial completion state
     */
    public TodoTask(String taskContent, Boolean isDone) {
        super(isDone);
        assert taskContent != null : "Task content cannot be null";
        assert !taskContent.trim().isEmpty() : "Task content cannot be empty";
        assert isDone != null : "isDone flag cannot be null";
        this.taskContent = taskContent;
    }

    /**
     * Returns a user-facing representation including type and checkbox.
     */
    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[T]";
        return String.format("%s%s %s", typeBox, checkBox, this.taskContent);
    }

    /**
     * Returns a storage-friendly string for persistence.
     */
    @Override
    public String printStorageString() {
        String partialStr = String.format("%s/%s/%s/%s/%s", "T", taskContent, "", "", "");
        return super.printStorageString(partialStr);
    }

    @Override
    public Boolean contains(String keyword) {
        return this.taskContent.contains(keyword);
    }

    @Override
    public void update(String updateDetails) throws InvalidPatternException {
        String newContent = UpdateDetailsParser.parseTaskContent(updateDetails);
        boolean isUpdated = false;

        if (newContent != null) {
            this.taskContent = newContent;
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new InvalidPatternException(" Please specify valid value(s) to update");
        }
    }
}
