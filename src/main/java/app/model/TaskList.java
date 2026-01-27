package app.model;

import app.exception.InvalidPatternException;
import app.exception.MissingComponentException;
import app.exception.RequestRejectedException;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private static final Integer CAPACITY = 100;
    private static List<Task> tasks = new ArrayList<>();

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

    public String addTask(String argument, TaskType type) throws InvalidPatternException, RequestRejectedException, MissingComponentException {
        if (argument.isEmpty()) {
            throw new InvalidPatternException("Please specify more details:)");
        } else if (tasks.size() >= CAPACITY) {
            throw new RequestRejectedException("app.model.Task list is full, cannot add new task:(");
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

    public String mark(String argument) throws InvalidPatternException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException("Please specify a valid task number to mark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).mark();
            return String.format(" Nice! I've marked this task as done:\n \t%s", tasks.get(taskNum - 1).printTask());
        }
    }

    public String unmark(String argument) throws InvalidPatternException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException("Please specify a valid task number to unmark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks.get(taskNum - 1).unmark();
            return String.format(" OK, I've marked this task as not done yet:\n \t%s", tasks.get(taskNum - 1).printTask());
        }
    }

    public String delete(String argument) throws InvalidPatternException {
        if (argument.isEmpty()
            || !TaskList.isNumeric(argument)
            || Integer.parseInt(argument.trim()) > tasks.size()) {
                throw new InvalidPatternException("Please specify a valid task number to delete:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            String taskView =  tasks.get(taskNum - 1).printTask();
            tasks.remove(taskNum - 1);
            return String.format(" Noted. I've removed this task:\n \t%s\n Now you have %d tasks in the list.", taskView, tasks.size());
        }
    }
}
