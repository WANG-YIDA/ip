package app.models.task;

/**
 * Abstract base for tasks (todo, deadline, event). Tracks completion state
 * and provides common formatting helpers.
 */
public abstract class Task {
    private Boolean isDone;

    /**
     * Creates a Task with specified completion state.
     *
     * @param isDone initial completion state
     */
    public Task(Boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Creates a new, not-yet-completed Task.
     */
    public Task() {
        this.isDone = false;
    }

    /**
     * Returns a user-facing string representation of the task.
     */
    public abstract String printTask();

    /**
     * Returns a storage-friendly string representation for persistence.
     */
    public abstract String printStorageString();

    /**
     * Helper to produce the storage string given a partial content.
     *
     * @param partialStr task-specific storage content
     * @return storage-formatted string including completion flag
     */
    public String printStorageString(String partialStr) {
        return isDone ? "1/" + partialStr : "0/" + partialStr;
    }

    public abstract Boolean contains(String keyword);

    /**
     * Marks the task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a simple checkbox-like string showing completion state.
     */
    public String printCheckBox() {
        return isDone ? "[x]" : "[ ]";
    }

}
