package app.model.task;

public abstract class Task {
    private Boolean isDone;

    public Task(Boolean isDone) {
        this.isDone = isDone;
    }

    public Task() {
        this.isDone = false;
    }

    public abstract String printTask();
    public abstract String printStorageString();
    public abstract Boolean contains(String keyword);

    public String printStorageString(String partialStr) {
        return isDone ? "1/" + partialStr : "0/" + partialStr;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String printCheckBox() {
        return isDone ? "[x]" : "[ ]";
    }

}
