public class TaskList {
    private static Task[] tasks = new Task[100];
    private static int index = 0;

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
        } else if (index >= tasks.length) {
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

            tasks[index] = newTask;
            index++;
            String taskView = newTask.printTask();

            return String.format("Got it. I've added this task:\n \t%s\n Now you have %d tasks in the list:)", taskView, index);
        }
    }

    public String printList() {
        if (index == 0) {
            return "No task in the list yet:)\n";
        }

        StringBuilder taskListView = new StringBuilder();
        taskListView.append(" Here are the tasks in your list:\n");
        for (int i = 0; i < index; i++) {
            String taskView = String.format(" %d.%s\n", i + 1, tasks[i].printTask());
            taskListView.append(taskView);
        }
        return taskListView.toString();
    }

    public String mark(String argument) throws InvalidPatternException {
        if (argument.isEmpty() || !TaskList.isNumeric(argument) || Integer.parseInt(argument.trim()) > index) {
            throw new InvalidPatternException("Please specify the valid task number to mark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks[taskNum - 1].mark();
            return String.format(" Nice! I've marked this task as done:\n \t%s", tasks[taskNum - 1].printTask());
        }
    }

    public String unmark(String argument) throws InvalidPatternException {
        if (argument.isEmpty() || !TaskList.isNumeric(argument) || Integer.parseInt(argument.trim()) > index) {
            throw new InvalidPatternException("Please specify the valid task number to unmark:(");
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks[taskNum - 1].unmark();
            return String.format(" OK, I've marked this task as not done yet:\n \t%s", tasks[taskNum - 1].printTask());
        }
    }
}
