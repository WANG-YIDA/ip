package app.ui;

/**
 * Utility class for printing UI messages to the console.
 * Contains helpers for formatting messages with visual dividers.
 */
public class Ui {
    private static String divider = "----------------------------------------------------------------------";

    public static String printWrappedMessage(String message) {
        return String.format("%s\n%s\n%s", divider, message, divider).indent(6);
    }
}
