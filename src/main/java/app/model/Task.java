package app.model;

public abstract class Task {
    private Boolean done;

    public Task() {
        this.done = false;
    }

    public abstract String printTask();

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
