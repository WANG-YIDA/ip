package app.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

import app.dataaccess.TaskListStorage;
import app.exceptions.InvalidPatternException;
import app.exceptions.InvalidTaskTypeException;
import app.exceptions.MissingComponentException;
import app.exceptions.RequestRejectedException;
import app.models.tasks.DeadlineTask;
import app.models.tasks.EventTask;
import app.models.tasks.Task;
import app.models.tasks.TaskType;
import app.models.tasks.TodoTask;

/**
 * Represents a task list persisted to a file. Provides operations to add,
 * list, mark/unmark, and delete tasks; changes are written back to
 * persistent storage.
 */
public class TaskList {
    private final Integer capacity = 100;
    private File taskListFile;
    private List<Task> tasks;

    /**
     * Loads the task list from the given file path.
     *
     * @param taskListPath path to the task storage file
     * @throws FileNotFoundException if the file cannot be found
     * @throws DataFormatException   if the stored data is malformed
     */
    public TaskList(String taskListPath) throws FileNotFoundException, DataFormatException {
        assert taskListPath != null : "Task list path cannot be null";
        assert !taskListPath.trim().isEmpty() : "Task list path cannot be empty";

        this.taskListFile = new File(taskListPath);

        // load task list from source file
        this.tasks = TaskListStorage.readTaskList(taskListFile);
    }

    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Adds a new task of the specified type parsed from the given argument.
     * Returns a user-facing message describing the result.
     *
     * @param argument raw task argument (format depends on task type)
     * @param type     the type of task to create
     * @return a confirmation string describing the added task and new total
     * @throws InvalidPatternException   if the argument doesn't match expected format
     * @throws RequestRejectedException  if the list is full or request cannot be accepted
     * @throws MissingComponentException if required components are missing
     * @throws IOException               if writing to storage fails
     * @throws InvalidTaskTypeException  if the task type is unsupported
     */
    public String addTask(String argument, TaskType type) throws InvalidPatternException, RequestRejectedException,
            MissingComponentException, IOException, InvalidTaskTypeException {
        assert argument != null : "Argument cannot be null";
        assert type != null : "Task type cannot be null";
        assert tasks != null : "Task list should be initialized";

        if (argument.isEmpty()) {
            throw new InvalidPatternException(" Please specify more details >_<");
        } else if (tasks.size() >= capacity) {
            throw new RequestRejectedException(" Task list is full, cannot add new task Orz");
        } else {
            Task newTask;
            switch (type) {
            case TODO:
                newTask = processAddTodoTask(argument);
                break;
            case DEADLINE:
                newTask = processAddDeadlineTask(argument);
                break;
            case EVENT:
                newTask = processAddEventTask(argument);
                break;
            default:
                throw new InvalidTaskTypeException(
                        " I can only create one of these task types: todo, deadline, event :)");
            }

            tasks.add(newTask);
            assert tasks.contains(newTask) : "Task should be in the list after adding";

            TaskListStorage.writeNewTask(newTask, taskListFile);
            String taskView = newTask.printTask();

            return String.format(" Copy. I've added this task:\n \t%s\n Now you have %d tasks in the list:)",
                    taskView, tasks.size());
        }
    }

    private static TodoTask processAddTodoTask(String argument) {
        String todoTaskContent = argument;
        return new TodoTask(todoTaskContent);
    }

    private static DeadlineTask processAddDeadlineTask(String argument)
            throws InvalidPatternException, MissingComponentException {
        // Component Parsing
        String[] deadlineTaskParts = argument.split("/by");

        // Error Handling: Pattern Validation
        if (deadlineTaskParts.length != 2) {
            throw new InvalidPatternException(
                    " Oops, please use valid pattern for Deadline task creation (e.g. deadline <task content>"
                            + " /by <deadline>");
        }

        String deadlineTaskContent = deadlineTaskParts[0].trim();
        String deadlineStr = deadlineTaskParts[1].trim();

        // Error Handling: Empty Components
        if (deadlineStr.isEmpty()) {
            String errMsg = " Please add a deadline for this task:)";
            throw new MissingComponentException(errMsg);
        } else if (deadlineTaskContent.isEmpty()) {
            String errMsg = " Please specific the task content:)";
            throw new MissingComponentException(errMsg);
        }

        // parse deadline
        LocalDateTime deadline;
        try {
            DateTimeFormatter deadlineTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm", Locale.ENGLISH);
            deadline = LocalDateTime.parse(deadlineStr, deadlineTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPatternException(
                    " Oops, please use deadline format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
        }

        return new DeadlineTask(deadlineTaskContent, deadline);
    }

    private static EventTask processAddEventTask(String argument)
            throws InvalidPatternException, MissingComponentException {
        // Error Handling: Pattern Validation
        int fromIdx = argument.indexOf("/from");
        int lastFromIdx = argument.lastIndexOf("/from");

        int toIdx = argument.indexOf("/to");
        int lastToIdx = argument.lastIndexOf("/to");

        if (fromIdx == -1 || toIdx == -1 || fromIdx > toIdx || fromIdx != lastFromIdx
                || toIdx != lastToIdx) {
            throw new InvalidPatternException(
                    " Oops, please use valid pattern for Event task creation (e.g. event <task content> "
                            + " /from <start time> /to <end time>");
        }

        // Component Parsing
        String[] eventTaskParts = argument.split("/from|/to", 3);
        String eventTaskContent = eventTaskParts[0].trim();
        String startTimeStr = eventTaskParts[1].trim();
        String endTimeStr = eventTaskParts[2].trim();

        // Error Handling: Empty Components
        if (eventTaskContent.isEmpty()) {
            String errMsg = " Please specify task content:)";
            throw new MissingComponentException(errMsg);
        } else if (startTimeStr.isEmpty()) {
            String errMsg = " Please specific the starting time of the task:)";
            throw new MissingComponentException(errMsg);
        } else if (endTimeStr.isEmpty()) {
            String errMsg = " Please specific the ending time of the task:)";
            throw new MissingComponentException(errMsg);
        }

        // parse event startTime and endTime
        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            DateTimeFormatter eventTimeFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm", Locale.ENGLISH);
            startTime = LocalDateTime.parse(startTimeStr, eventTimeFormatter);
            endTime = LocalDateTime.parse(endTimeStr, eventTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPatternException(
                    " Oops, please use event time format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
        }

        return new EventTask(eventTaskContent, startTime, endTime);
    }

    /**
     * Returns a formatted string listing all tasks; returns a friendly message if empty.
     *
     * @return formatted task list or empty message
     */
    public String printList() {
        if (tasks.isEmpty()) {
            return " No task in the list yet. Take a break or get started by adding a new task:)";
        }

        StringBuilder taskListView = new StringBuilder();
        taskListView.append(" Sure! Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            String taskView = String.format(" \t%d.%s\n", i + 1, tasks.get(i).printTask());
            taskListView.append(taskView);
        }
        taskListView.append(String.format(" Total Number of Tasks: %d", tasks.size()));
        return taskListView.toString();
    }

    /**
     * Returns a formatted string with tasks matching the given keyword.
     *
     * @param keyword search keyword used to filter tasks
     * @return formatted matching task list or a message when no matches found
     */
    public String printMatchedList(String keyword) {
        assert keyword != null : "Keyword cannot be null";

        StringBuilder matchedTaskListView = new StringBuilder();
        matchedTaskListView.append(" Copy that. Here are the matching tasks in your list:\n");
        int matchedTaskIndex = 0;
        for (Task task : tasks) {
            if (task.contains(keyword)) {
                String taskView = String.format(" \t%d.%s\n", ++matchedTaskIndex, task.printTask());
                matchedTaskListView.append(taskView);
            }
        }

        if (matchedTaskIndex == 0) {
            return " Hey, I can;t find any matching task in the list XoX";
        }

        matchedTaskListView.append(String.format(" Total Number of Tasks: %d", matchedTaskIndex));
        return matchedTaskListView.toString();
    }

    /**
     * Marks a task as done by numeric index provided as a string.
     *
     * @param argument the task number (1-based) as string
     * @return a confirmation message with the updated task
     * @throws InvalidPatternException if the argument is invalid
     * @throws IOException             if writing to storage fails
     */
    public String markTask(String argument) throws InvalidPatternException, IOException {
        assert argument != null : "Argument cannot be null";

        if (argument.isEmpty()
                || !TaskList.isNumeric(argument)
                || Integer.parseInt(argument.trim()) > tasks.size()
                || Integer.parseInt(argument.trim()) <= 0) {
            throw new InvalidPatternException(" Oops, please specify a valid task number to mark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).mark();
            TaskListStorage.writeAllTasks(taskListFile, tasks);

            return String.format(" Excellent! I've marked this task as done:\n \t%s",
                    tasks.get(taskNum - 1).printTask());
        }
    }

    /**
     * Marks a task as not done by numeric index provided as a string.
     *
     * @param argument the task number (1-based) as string
     * @return a confirmation message with the updated task
     * @throws InvalidPatternException if the argument is invalid
     * @throws IOException             if writing to storage fails
     */
    public String unmarkTask(String argument) throws InvalidPatternException, IOException {
        assert argument != null : "Argument cannot be null";

        if (argument.isEmpty()
                || !TaskList.isNumeric(argument)
                || Integer.parseInt(argument.trim()) > tasks.size()
                || Integer.parseInt(argument.trim()) <= 0) {
            throw new InvalidPatternException(" Oops, please specify a valid task number to unmark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).unmark();
            TaskListStorage.writeAllTasks(taskListFile, tasks);

            return String.format(" Alright, I've marked this task as not done yet:\n \t%s",
                    tasks.get(taskNum - 1).printTask());
        }
    }

    /**
     * Deletes the task at the specified numeric index (1-based) and returns a message.
     *
     * @param argument the task number (1-based) as string
     * @return a confirmation message with the removed task and new total count
     * @throws InvalidPatternException if the argument is invalid
     * @throws IOException             if writing to storage fails
     */
    public String deleteTask(String argument) throws InvalidPatternException, IOException {
        assert argument != null : "Argument cannot be null";

        if (argument.isEmpty()
                || !TaskList.isNumeric(argument)
                || Integer.parseInt(argument.trim()) > tasks.size()
                || Integer.parseInt(argument.trim()) <= 0) {
            throw new InvalidPatternException(" Oops, please specify a valid task number to delete:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            String taskView = tasks.get(taskNum - 1).printTask();
            tasks.remove(taskNum - 1);
            TaskListStorage.writeAllTasks(taskListFile, tasks);

            return String.format(" Ay ay! I've removed this task:\n \t%s\n Now you have %d tasks in the list.",
                    taskView, tasks.size());
        }
    }

    /**
     * Updates a task in the task list based on the argument.
     *
     * @param argument the argument string containing task index and update details
     * @return the result message indicating the update status
     * @throws InvalidPatternException if the pattern is invalid
     * @throws IOException if there's an error saving the task list
     */
    public String updateTask(String argument) throws InvalidPatternException, IOException {
        String[] parts = argument.split(" ", 2);
        if (parts.length != 2) {
            throw new InvalidPatternException(" Oops, please use valid pattern for update (e.g. update 1 /content quiz1)");
        }

        String index = parts[0].trim();
        String updateDetails = parts[1].trim();

        if (!TaskList.isNumeric(index)
                || Integer.parseInt(index) > tasks.size()
                || Integer.parseInt(index) <= 0) {
            throw new InvalidPatternException(" Oops, please specify a valid task number to update:(");
        }

        int indexNum = Integer.parseInt(index) - 1;
        Task taskToUpdate = tasks.get(indexNum);

        taskToUpdate.update(updateDetails);
        TaskListStorage.writeAllTasks(taskListFile, tasks);

        return String.format(" Roger that. I've updated task %d to:\n \t%s", indexNum + 1, taskToUpdate.printTask());
    }
}
