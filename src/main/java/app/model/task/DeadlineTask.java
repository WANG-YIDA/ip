package app.model.task;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline (date and time).
 */
public class DeadlineTask extends Task {
    private String taskContent;
    private LocalDateTime deadline;
    private DateTimeFormatter viewStrFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm", Locale.ENGLISH);
    private DateTimeFormatter storageStrFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm", Locale.ENGLISH);

    /**
     * Constructs a not-yet-completed DeadlineTask.
     *
     * @param taskContent task description
     * @param deadline    deadline date and time
     */
    public DeadlineTask(String taskContent, LocalDateTime deadline) {
        super();
        this.taskContent = taskContent;
        this.deadline = deadline;
    }

    /**
     * Constructs a DeadlineTask with specified completion state.
     *
     * @param taskContent task description
     * @param deadline    deadline date and time
     * @param isDone      initial completion state
     */
    public DeadlineTask(String taskContent, LocalDateTime deadline, Boolean isDone) {
        super(isDone);
        this.taskContent = taskContent;
        this.deadline = deadline;
    }

    /**
     * Returns a user-facing representation including type, checkbox and deadline.
     */
    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[D]";
        String deadlineView = this.deadline.format(viewStrFormatter);
        return String.format("%s%s %s (by: %s)", typeBox, checkBox, this.taskContent, deadlineView);
    }

    /**
     * Returns a storage-friendly string for persistence.
     */
    @Override
    public String printStorageString() {
        String deadlineTimeStorageStr = this.deadline.format(storageStrFormatter);
        String partialStr = String.format("%s/%s/%s/%s/%s", "D", taskContent, deadlineTimeStorageStr, "", "");
        return super.printStorageString(partialStr);
    }
}
