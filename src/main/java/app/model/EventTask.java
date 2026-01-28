package app.model;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    String taskContent;
    LocalDateTime startTime;
    LocalDateTime endTime;
    DateTimeFormatter viewStrFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm", Locale.ENGLISH);
    DateTimeFormatter storageStrFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm", Locale.ENGLISH);

    public EventTask(String taskContent, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.taskContent = taskContent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(String taskContent, LocalDateTime startTime, LocalDateTime endTime, Boolean done) {
        super(done);
        this.taskContent = taskContent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[E]";
        String startTimeView = this.startTime.format(viewStrFormatter);
        String endTimeView = this.endTime.format(viewStrFormatter);
        return String.format("%s%s %s (from: %s to: %s)", typeBox, checkBox, this.taskContent, startTimeView, endTimeView);
    }

    @Override
    public String printStorageString() {
        String startTimeStorageStr = startTime.format(storageStrFormatter);
        String endTimeStorageStr = endTime.format(storageStrFormatter);
        String partialStr = String.format("%s/%s/%s/%s/%s", "E", taskContent, "", startTimeStorageStr, endTimeStorageStr);
        return super.printStorageString(partialStr);
    }
}
