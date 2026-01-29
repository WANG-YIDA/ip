package app.model.task;

public abstract class Task {
    private Boolean done;

    public Task(Boolean done) {
        this.done = done;
    }

    public Task() {
        this.done = false;
    }

    public abstract String printTask();
    public abstract String printStorageString();

    public String printStorageString(String partialStr) {
        return done ? "1/" + partialStr : "0/" + partialStr;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public String printCheckBox() {
        return done ? "[x]" : "[ ]";
    }

}
