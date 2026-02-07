package app.models.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
}
