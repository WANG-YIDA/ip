package app.model.task;

public class TodoTask extends Task {
    String taskContent;

    public TodoTask(String taskContent) {
        super();
        this.taskContent = taskContent;
    }

    public TodoTask(String taskContent, Boolean done) {
        super(done);
        this.taskContent = taskContent;
    }

    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[T]";
        return String.format("%s%s %s", typeBox, checkBox, this.taskContent);
    }

    @Override
    public String printStorageString() {
        String partialStr = String.format("%s/%s/%s/%s/%s", "T", taskContent, "", "", "");
        return super.printStorageString(partialStr);
    }
}
