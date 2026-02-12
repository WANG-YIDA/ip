package app.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.zip.DataFormatException;

import app.models.tasks.DeadlineTask;
import app.models.tasks.EventTask;
import app.models.tasks.Task;
import app.models.tasks.TodoTask;

/**
 * Utility class to parse a task storage line into a Task object.
 *
 * <p>Storage Pattern:
 * IsDone/TaskType/TaskContent/Deadline(if applicable)/StartTime(if applicable)/EndTime(if applicable).</p>
 */
public class StorageStringParser {
    /**
     * Parses a storage-line into a Task.
     *
     * @param taskStorageStr the string representation of the task in persistent storage
     * @return the parsed Task
     * @throws DataFormatException if the storage string is corrupted or cannot be parsed
     */
    public static Task parseTaskStorage(String taskStorageStr) throws DataFormatException {
        String[] parts = taskStorageStr.split("/", 6);
        Boolean isDone = (Integer.parseInt(parts[0].trim(), 10) == 1);
        String taskContent = parts[2].trim();
        String deadlineStr = parts[3].trim();
        String startTimeStr = parts[4].trim();
        String endTimeStr = parts[5].trim();

        Task taskToAdd;

        switch (parts[1].trim()) {
        case "T":
            taskToAdd = parseTodoTaskStorage(isDone, taskContent);
            break;
        case "D":
            taskToAdd = parseDeadlineTaskStorage(isDone, taskContent, deadlineStr);
            break;
        case "E":
            taskToAdd = parseEventTaskStorage(isDone, taskContent, startTimeStr, endTimeStr);
            break;
        default:
            throw new DataFormatException("Invalid Task Type");
        }

        return taskToAdd;
    }

    private static TodoTask parseTodoTaskStorage(Boolean isDone, String taskContent) throws DataFormatException {
        if (taskContent.isEmpty()) {
            String errMsg = "Task Content Missing";
            throw new DataFormatException(errMsg);
        }

        return new TodoTask(taskContent, isDone);
    }

    private static DeadlineTask parseDeadlineTaskStorage(Boolean isDone, String taskContent, String deadlineStr)
            throws DataFormatException {
        if (taskContent.isEmpty()) {
            String errMsg = "Task Content Missing";
            throw new DataFormatException(errMsg);
        } else if (deadlineStr.isEmpty()) {
            String errMsg = "Deadline Missing";
            throw new DataFormatException(errMsg);
        }

        // parse deadline storage string
        LocalDateTime deadline;
        try {
            DateTimeFormatter deadlineTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd-HH-mm", Locale.ENGLISH);
            deadline = LocalDateTime.parse(deadlineStr, deadlineTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DataFormatException("Invalid Deadline Time Format");
        }

        return new DeadlineTask(taskContent, deadline, isDone);
    }

    private static EventTask parseEventTaskStorage(Boolean isDone, String taskContent, String startTimeStr, String endTimeStr)
            throws DataFormatException {
        if (taskContent.isEmpty()) {
            String errMsg = "Task Content Missing";
            throw new DataFormatException(errMsg);
        } else if (startTimeStr.isEmpty()) {
            String errMsg = "Start Time Missing";
            throw new DataFormatException(errMsg);
        } else if (endTimeStr.isEmpty()) {
            String errMsg = "End Time Missing";
            throw new DataFormatException(errMsg);
        }

        // parse event time storage string
        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            DateTimeFormatter eventTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd-HH-mm", Locale.ENGLISH);
            startTime = LocalDateTime.parse(startTimeStr, eventTimeFormatter);
            endTime = LocalDateTime.parse(endTimeStr, eventTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DataFormatException("Invalid Event Time Format");
        }

        return new EventTask(taskContent, startTime, endTime, isDone);
    }
}
