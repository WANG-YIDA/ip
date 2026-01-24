public class EventTask extends Task {
    String taskContent;
    String startTime;
    String endTime;

    EventTask(String taskContent, String startTime, String endTime) {
        super();
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
}
