package app.model;

public class DeadlineTask extends Task {
    String taskContent;
    String deadline;

    public DeadlineTask(String taskContent, String deadline) {
        super();
        this.taskContent = taskContent;
        this.deadline = deadline;
    }

    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[D]";
        return String.format("%s%s %s (by: %s)", typeBox, checkBox, this.taskContent, this.deadline);
    }
}
