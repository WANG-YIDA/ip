package app.model;

import app.exception.InvalidPatternException;
import app.exception.MissingComponentException;
import app.exception.RequestRejectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class TaskList {
    private final Integer CAPACITY = 100;
    private File taskListFile;
    private List<Task> tasks = new ArrayList<>();

    public TaskList(String taskListPath) throws FileNotFoundException, DataFormatException {
        this.taskListFile = new File(taskListPath);

        if (!taskListFile.exists()) {
            throw new FileNotFoundException("Task List Not Found");
        }

        // load tasks from source file
        Scanner scanner = new Scanner(taskListFile);
        while (scanner.hasNext()) {
            String taskString = scanner.nextLine();
            taskParser(taskString);
        }
    }

    /**
     * Parse each component of the task stored in source file, create corresponding task with these components and store the task in list.
     * Storage Pattern: Done/TaskType/TaskContent/Deadline(if applicable)/StartTime(if applicable)/EndTime(if applicable).
     * @param taskStorageStr the string representation of the task in persistent storage.
     */
    private void taskParser(String taskStorageStr) throws DataFormatException {
        String[] parts = taskStorageStr.split("/", 6);
        Boolean done = (Integer.parseInt(parts[0].trim(), 10) == 1);
        String taskContent = parts[2].trim();
        String deadline = parts[3].trim();
        String startTime = parts[4].trim();
        String endTime = parts[5].trim();

        Task taskToADD;

        switch (parts[1].trim()) {
        case "T":
            if (taskContent.isEmpty()) {
                String errMsg = "Task Content Missing";
                throw new DataFormatException(errMsg);
            }

            taskToADD = new TodoTask(taskContent, done);
            break;
        case "D":
            if (taskContent.isEmpty()) {
                String errMsg = "Task Content Missing";
                throw new DataFormatException(errMsg);
            } else if (deadline.isEmpty()) {
                String errMsg =  "Deadline Missing";
                throw new DataFormatException(errMsg);
            }

            taskToADD = new DeadlineTask(taskContent, deadline, done);
            break;
        case "E":
            if (taskContent.isEmpty()) {
                String errMsg = "Task Content Missing";
                throw new DataFormatException(errMsg);
            } else if (startTime.isEmpty()) {
                String errMsg =  "Start Time Missing";
                throw new DataFormatException(errMsg);
            } else if (endTime.isEmpty()) {
                String errMsg =  "End Time Missing";
                throw new DataFormatException(errMsg);
            }

            taskToADD = new EventTask(taskContent, startTime, endTime, done);
            break;
        default:
            throw new DataFormatException("Invalid Task Type");
        }

        tasks.add(taskToADD);
    }

    private void writeTask(Task task) throws IOException {
        FileWriter taskListWriter = new FileWriter(taskListFile, true);
        String taskStorageStr = task.printStorageString();

        taskListWriter.write(taskStorageStr + System.lineSeparator());
        taskListWriter.close();
    }

    private void writeAllTasks() throws IOException {
        FileWriter taskListWriter = new FileWriter(taskListFile, false);

        for (Task task: tasks) {
            String taskStorageStr = task.printStorageString();
            taskListWriter.write(taskStorageStr + System.lineSeparator());
        }

        taskListWriter.close();
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
            throw new InvalidPatternException("Please specify more details:)");
        } else if (tasks.size() >= CAPACITY) {
            throw new RequestRejectedException("Task list is full, cannot add new task:(");
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
                    throw new InvalidPatternException("Invalid pattern for Deadline task creation:(");
                }

                String deadlineTaskContent = deadlineTaskParts[0].trim();
                String deadline = deadlineTaskParts[1].trim();

                // Error Handling: Empty Components
                if (deadline.isEmpty()) {
                    String errMsg = "Please add a deadline for this task:)";
                    throw new MissingComponentException(errMsg);
                } else if (deadlineTaskContent.isEmpty()) {
                    String errMsg =  "Please specific the task content:)";
                    throw new MissingComponentException(errMsg);
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
                    throw new InvalidPatternException("Invalid pattern for Event task creation:(");
                }

                // Component Parsing
                String[] eventTaskParts = argument.split("/from|/to", 3);
                String eventTaskContent = eventTaskParts[0].trim();
                String startTime = eventTaskParts[1].trim();
                String endTime = eventTaskParts[2].trim();

                // Error Handling: Empty Components
                if (eventTaskContent.isEmpty()) {
                    String errMsg = "Please specify task content:)";
                    throw new MissingComponentException(errMsg);
                } else if (startTime.isEmpty()) {
                    String errMsg =  "Please specific the starting time of the task:)";
                    throw new MissingComponentException(errMsg);
                } else if (endTime.isEmpty()) {
                    String errMsg =  "Please specific the ending time of the task:)";
                    throw new MissingComponentException(errMsg);
                }

                newTask = new EventTask(eventTaskContent, startTime, endTime);
            }

            tasks.add(newTask);
            writeTask(newTask);
            String taskView = newTask.printTask();

            return String.format("Got it. I've added this task:\n \t%s\n Now you have %d tasks in the list:)", taskView, tasks.size());
        }
    }

    public String printList() {
        if (tasks.isEmpty()) {
            return "No task in the list yet:)\n";
        }

        StringBuilder taskListView = new StringBuilder();
        taskListView.append(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            String taskView = String.format(" %d.%s\n", i + 1, tasks.get(i).printTask());
            taskListView.append(taskView);
        }
        return taskListView.toString();
    }

    public String mark(String argument) throws InvalidPatternException, IOException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException("Please specify a valid task number to mark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).mark();
            writeAllTasks();
            return String.format(" Nice! I've marked this task as done:\n \t%s", tasks.get(taskNum - 1).printTask());
        }
    }

    public String unmark(String argument) throws InvalidPatternException, IOException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException("Please specify a valid task number to unmark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).unmark();
            writeAllTasks();
            return String.format(" OK, I've marked this task as not done yet:\n \t%s", tasks.get(taskNum - 1).printTask());
        }
    }

    public String delete(String argument) throws InvalidPatternException, IOException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException("Please specify a valid task number to delete:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            String taskView =  tasks.get(taskNum - 1).printTask();
            tasks.remove(taskNum - 1);
            writeAllTasks();
            return String.format(" Noted. I've removed this task:\n \t%s\n Now you have %d tasks in the list.", taskView, tasks.size());
        }
    }
}
