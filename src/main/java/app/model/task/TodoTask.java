package app.model.task;

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
}
