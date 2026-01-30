package app.model.task;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private String taskContent;
    private LocalDateTime deadline;
    private DateTimeFormatter viewStrFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm", Locale.ENGLISH);
    private DateTimeFormatter storageStrFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm", Locale.ENGLISH);

    public DeadlineTask(String taskContent, LocalDateTime deadline) {
        super();
        this.taskContent = taskContent;
        this.deadline = deadline;
    }

    public DeadlineTask(String taskContent, LocalDateTime deadline, Boolean isDone) {
        super(isDone);
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

    @Override
    public Boolean contains(String keyword) {
        return this.taskContent.contains(keyword);
    }
}
