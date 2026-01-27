package app.model;

public class TodoTask extends Task {
    String taskContent;

    public TodoTask(String taskContent) {
        super();
        this.taskContent = taskContent;
    }

    @Override
    public String printTask() {
        String checkBox = this.printCheckBox();
        String typeBox = "[T]";
        return String.format("%s%s %s", typeBox, checkBox, this.taskContent);
    }
}
