public class Task {
    private String taskContent;
    private Boolean done;

    Task(String taskContent) {
        this.taskContent = taskContent;
        this.done = false;
    }

    public String printTask() {
        String checkbox = done ? " [x] " : " [ ] ";
        return checkbox + taskContent;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }
}
