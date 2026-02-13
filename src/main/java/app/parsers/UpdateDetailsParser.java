package app.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import app.exceptions.InvalidPatternException;

public class UpdateDetailsParser {
    public static String parseTaskContent(String updateDetails) throws InvalidPatternException {
        String newContent =  parseTagValue(updateDetails, "/content");
        if (newContent == null) {
            return null;
        }
        if (newContent.isEmpty()) {
            throw new InvalidPatternException(" Please specify a new task content (e.g. /content quiz1)");
        }

        return newContent;
    }

    public static LocalDateTime parseDeadline(String updateDetails) throws InvalidPatternException {
        String newDeadlineStr =  parseTagValue(updateDetails, "/deadline");
        if (newDeadlineStr == null) {
            return null;
        }
        if (newDeadlineStr.isEmpty()) {
            throw new InvalidPatternException(" Please specify a new deadline (e.g. /deadline 2026-02-11 23:59)");
        }

        // parse deadline
        LocalDateTime newDeadline;
        try {
            DateTimeFormatter deadlineTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm", Locale.ENGLISH);
            newDeadline = LocalDateTime.parse(newDeadlineStr, deadlineTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPatternException(
                    " Please use deadline format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
        }

        return newDeadline;
    }

    public static LocalDateTime parseStartTime(String updateDetails) throws InvalidPatternException {
        String newStartTimeStr =  parseTagValue(updateDetails, "/startTime");
        if (newStartTimeStr == null) {
            return null;
        }
        if (newStartTimeStr.isEmpty()) {
            throw new InvalidPatternException(" Please specify a new start time (e.g. /deadline 2026-02-11 23:59)");
        }

        // parse start time
        LocalDateTime newStartTime;
        try {
            DateTimeFormatter startTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm", Locale.ENGLISH);
            newStartTime = LocalDateTime.parse(newStartTimeStr, startTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPatternException(
                    " Please use start time format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
        }

        return newStartTime;
    }

    public static LocalDateTime parseEndTime(String updateDetails) throws InvalidPatternException {
        String newEndTimeStr =  parseTagValue(updateDetails, "/endTime");
        if (newEndTimeStr == null) {
            return null;
        }
        if (newEndTimeStr.isEmpty()) {
            throw new InvalidPatternException(" Please specify a new end time (e.g. /deadline 2026-02-11 23:59)");
        }

        // parse end time
        LocalDateTime newEndTime;
        try {
            DateTimeFormatter endTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm", Locale.ENGLISH);
            newEndTime = LocalDateTime.parse(newEndTimeStr, endTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPatternException(
                    " Please use end time format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
        }

        return newEndTime;
    }

    private static String parseTagValue(String updateDetails, String tag) throws InvalidPatternException {
        if (!updateDetails.contains(tag)) {
            return null;
        }

        int start = updateDetails.indexOf(tag) + tag.length();
        if (start >= updateDetails.length() || updateDetails.charAt(start) != ' ') {
            throw new InvalidPatternException(String.format(" Please specify a new value after %s", tag));
        }
        int end = updateDetails.indexOf("/", start);
        if (end == -1) {
            end = updateDetails.length();
        }

        return updateDetails.substring(start, end).trim();
    }
}
