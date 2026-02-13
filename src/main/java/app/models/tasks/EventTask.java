package app.models.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import app.exceptions.InvalidPatternException;
import app.parsers.UpdateDetailsParser;

/**
 * Represents an event task with a start and end time.
 */
public class EventTask extends Task {
    private String taskContent;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DateTimeFormatter viewStrFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm", Locale.ENGLISH);
    private DateTimeFormatter storageStrFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm", Locale.ENGLISH);

    /**
     * Constructs a not-yet-completed EventTask.
     *
     * @param taskContent description of the event
     * @param startTime   event start date/time
     * @param endTime     event end date/time
     */
    public EventTask(String taskContent, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        assert taskContent != null : "Task content cannot be null";
        assert !taskContent.trim().isEmpty() : "Task content cannot be empty";
        assert startTime != null : "Start time cannot be null";
        assert endTime != null : "End time cannot be null";
        assert !endTime.isBefore(startTime) : "End time must be after or equal to start time";
        this.taskContent = taskContent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs an EventTask with a specified completion state.
     *
     * @param taskContent description of the event
     * @param startTime   event start date/time
     * @param endTime     event end date/time
     * @param isDone      initial completion state
     */
    public EventTask(String taskContent, LocalDateTime startTime, LocalDateTime endTime, Boolean isDone) {
        super(isDone);
        assert taskContent != null : "Task content cannot be null";
        assert !taskContent.trim().isEmpty() : "Task content cannot be empty";
        assert startTime != null : "Start time cannot be null";
        assert endTime != null : "End time cannot be null";
        assert !endTime.isBefore(startTime) : "End time must be after or equal to start time";
        assert isDone != null : "isDone flag cannot be null";
        this.taskContent = taskContent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns a user-facing representation including type, checkbox and time range.
     */
    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[E]";
        String startTimeView = this.startTime.format(viewStrFormatter);
        String endTimeView = this.endTime.format(viewStrFormatter);
        return String.format("%s%s %s (from: %s to: %s)", typeBox, checkBox, this.taskContent,
                startTimeView, endTimeView);
    }

    /**
     * Returns a storage-friendly string for persistence.
     */
    @Override
    public String printStorageString() {
        String startTimeStorageStr = startTime.format(storageStrFormatter);
        String endTimeStorageStr = endTime.format(storageStrFormatter);
        String partialStr = String.format("%s/%s/%s/%s/%s", "E", taskContent, "", startTimeStorageStr,
                endTimeStorageStr);
        return super.printStorageString(partialStr);
    }

    @Override
    public Boolean contains(String keyword) {
        return this.taskContent.contains(keyword);
    }

    @Override
    public void update(String updateDetails) throws InvalidPatternException {
        String newContent = UpdateDetailsParser.parseTaskContent(updateDetails);
        LocalDateTime newStartTime = UpdateDetailsParser.parseStartTime(updateDetails);
        LocalDateTime newEndTime = UpdateDetailsParser.parseEndTime(updateDetails);
        boolean isUpdated = false;

        if (newContent != null) {
            this.taskContent = newContent;
            isUpdated = true;
        }
        if (newStartTime != null) {
            this.startTime = newStartTime;
            isUpdated = true;
        }
        if (newEndTime != null) {
            this.endTime = newEndTime;
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new InvalidPatternException(" Please specify valid value(s) to update");
        }
    }
}
