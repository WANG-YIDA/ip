package app.ui;

public class Ui {
    private static String divider = "----------------------------------------------------------------------";

    public static String printWrappedMessage(String message) {
        return String.format("%s\n%s\n%s", divider, message, divider).indent(6);
    }
}
