package app.model;

import app.dataaccess.TaskListStorage;
import app.exception.InvalidPatternException;
import app.exception.MissingComponentException;
import app.exception.RequestRejectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class TaskList {
    private final Integer CAPACITY = 100;
    private File taskListFile;
    private List<Task> tasks;

    public TaskList(String taskListPath) throws FileNotFoundException, DataFormatException {
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

    public String addTask(String argument, TaskType type) throws InvalidPatternException, RequestRejectedException, MissingComponentException, IOException {
        if (argument.isEmpty()) {
            throw new InvalidPatternException(" Please specify more details:)");
        } else if (tasks.size() >= CAPACITY) {
            throw new RequestRejectedException(" Task list is full, cannot add new task:(");
        } else {
            Task newTask = null;
            switch (type) {
            case TODO:
                String todoTaskContent = argument;

                newTask = new TodoTask(todoTaskContent);
                break;
            case DEADLINE:
                // Component Parsing
                String[] deadlineTaskParts = argument.split("/by");

                // Error Handling: Pattern Validation
                if (deadlineTaskParts.length != 2) {
                    throw new InvalidPatternException(" Please use valid pattern for Deadline task creation (e.g. deadline <task content> /by <deadline>");
                }

                String deadlineTaskContent = deadlineTaskParts[0].trim();
                String deadlineStr = deadlineTaskParts[1].trim();

                // Error Handling: Empty Components
                if (deadlineStr.isEmpty()) {
                    String errMsg = " Please add a deadline for this task:)";
                    throw new MissingComponentException(errMsg);
                } else if (deadlineTaskContent.isEmpty()) {
                    String errMsg =  " Please specific the task content:)";
                    throw new MissingComponentException(errMsg);
                }

                // parse deadline
                LocalDateTime deadline;
                try {
            DateTimeFormatter deadlineTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                    deadline = LocalDateTime.parse(deadlineStr, deadlineTimeFormatter);
                } catch (DateTimeParseException e) {
                    throw new InvalidPatternException(" Please use deadline format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
                }

                newTask = new DeadlineTask(deadlineTaskContent, deadline);
                break;
            case EVENT:
                // Error Handling: Pattern Validation
                int fromIdx = argument.indexOf("/from");
                int lastFromIdx = argument.lastIndexOf("/from");

                int toIdx = argument.indexOf("/to");
                int lastToIdx = argument.lastIndexOf("/to");

                if (fromIdx == -1 || toIdx == -1 || fromIdx > toIdx || fromIdx != lastFromIdx || toIdx != lastToIdx) {
                    throw new InvalidPatternException(" Please use pattern for Event task creation (e.g. event <task content> /from <start time> /to <end time>");
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
                    String errMsg =  " Please specific the starting time of the task:)";
                    throw new MissingComponentException(errMsg);
                } else if (endTimeStr.isEmpty()) {
                    String errMsg =  " Please specific the ending time of the task:)";
                    throw new MissingComponentException(errMsg);
                }

                // parse event startTime and endTime
                LocalDateTime startTime;
                LocalDateTime endTime;
                try {
                    DateTimeFormatter eventTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                    startTime = LocalDateTime.parse(startTimeStr, eventTimeFormatter);
                    endTime = LocalDateTime.parse(endTimeStr, eventTimeFormatter);
                } catch (DateTimeParseException e) {
                    throw new InvalidPatternException(" Please use event time format yyyy-MM-dd HH:mm (e.g. 2026-01-28 23:59)");
                }

                newTask = new EventTask(eventTaskContent, startTime, endTime);
            }

            tasks.add(newTask);
            TaskListStorage.writeTask(newTask, taskListFile);
            String taskView = newTask.printTask();

            return String.format(" Got it. I've added this task:\n \t%s\n Now you have %d tasks in the list:)", taskView, tasks.size());
        }
    }

    public String printList() {
        if (tasks.isEmpty()) {
            return " No task in the list yet:)";
        }

        StringBuilder taskListView = new StringBuilder();
        taskListView.append(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            String taskView = String.format(" %d.%s\n", i + 1, tasks.get(i).printTask());
            taskListView.append(taskView);
        }
        taskListView.append(String.format(" Total Number of Tasks: %d", tasks.size()));
        return taskListView.toString();
    }

    public String mark(String argument) throws InvalidPatternException, IOException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException(" Please specify a valid task number to mark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).mark();
            TaskListStorage.writeAllTasks(taskListFile, tasks);

            return String.format(" Nice! I've marked this task as done:\n \t%s", tasks.get(taskNum - 1).printTask());
        }
    }

    public String unmark(String argument) throws InvalidPatternException, IOException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException(" Please specify a valid task number to unmark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).unmark();
            TaskListStorage.writeAllTasks(taskListFile, tasks);

            return String.format(" OK, I've marked this task as not done yet:\n \t%s", tasks.get(taskNum - 1).printTask());
        }
    }

    public String delete(String argument) throws InvalidPatternException, IOException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException(" Please specify a valid task number to delete:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            String taskView =  tasks.get(taskNum - 1).printTask();
            tasks.remove(taskNum - 1);
            TaskListStorage.writeAllTasks(taskListFile, tasks);

            return String.format(" Noted. I've removed this task:\n \t%s\n Now you have %d tasks in the list.", taskView, tasks.size());
        }
    }
}
