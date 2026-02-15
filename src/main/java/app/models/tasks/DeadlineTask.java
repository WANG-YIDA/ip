package app.models.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import app.exceptions.InvalidPatternException;
import app.parsers.UpdateDetailsParser;

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
        assert taskContent != null : "Task content cannot be null";
        assert !taskContent.trim().isEmpty() : "Task content cannot be empty";
        assert deadline != null : "Deadline cannot be null";
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
        assert taskContent != null : "Task content cannot be null";
        assert !taskContent.trim().isEmpty() : "Task content cannot be empty";
        assert deadline != null : "Deadline cannot be null";
        assert isDone != null : "isDone flag cannot be null";
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

    @Override
    public Boolean contains(String keyword) {
        return this.taskContent.contains(keyword);
    }

    @Override
    public void update(String updateDetails) throws InvalidPatternException {
        String newContent = UpdateDetailsParser.parseTaskContent(updateDetails);
        LocalDateTime newDeadline = UpdateDetailsParser.parseDeadline(updateDetails);
        boolean isUpdated = false;

        if (newContent != null) {
            this.taskContent = newContent;
            isUpdated = true;
        }
        if (newDeadline != null) {
            this.deadline = newDeadline;
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new InvalidPatternException(
                    " Oops, please specify valid value(s) to update a deadline task"
                    + " (e.g. update 1 /content quiz1 /deadline 2026-02-03 21:00)");
        }
    }
}
