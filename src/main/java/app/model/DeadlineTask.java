package app.model;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    String taskContent;
    LocalDateTime deadline;
    DateTimeFormatter viewStrFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm", Locale.ENGLISH);
    DateTimeFormatter storageStrFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm", Locale.ENGLISH);

    public DeadlineTask(String taskContent, LocalDateTime deadline) {
        super();
        this.taskContent = taskContent;
        this.deadline = deadline;
    }

    public DeadlineTask(String taskContent, LocalDateTime deadline, Boolean done) {
        super(done);
        this.taskContent = taskContent;
        this.deadline = deadline;
    }

    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[D]";
        String deadlineView = this.deadline.format(viewStrFormatter);
        return String.format("%s%s %s (by: %s)", typeBox, checkBox, this.taskContent, deadlineView);
    }

    @Override
    public String printStorageString() {
        String deadlineTimeStorageStr = this.deadline.format(storageStrFormatter);
        String partialStr = String.format("%s/%s/%s/%s/%s", "D", taskContent, deadlineTimeStorageStr, "", "");
        return super.printStorageString(partialStr);
    }
}
