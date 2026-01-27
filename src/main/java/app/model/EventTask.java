package app.model;

public class EventTask extends Task {
    String taskContent;
    String startTime;
    String endTime;

    public EventTask(String taskContent, String startTime, String endTime) {
        super();
        this.taskContent = taskContent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(String taskContent, String startTime, String endTime, Boolean done) {
        super(done);
        this.taskContent = taskContent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[E]";
        return String.format("%s%s %s (from: %s to: %s)", typeBox, checkBox, this.taskContent, this.startTime, this.endTime);
    }

    @Override
    public String printStorageString() {
        String partialStr = String.format("%s/%s/%s/%s/%s", "E", taskContent, "", startTime, endTime);
        return super.printStorageString(partialStr);
    }
}
