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

    public String addTask(String taskContent) {
        if (index >= tasks.length) {
            return "Task list is full, cannot add new task:(";
        } else {
            Task newTask = new Task(taskContent);
            tasks[index] = newTask;
            index++;

            return String.format("Task Added: %s", taskContent);
        }
    }

    public String printList() {
        StringBuilder taskListView = new StringBuilder();
        taskListView.append(" Here are the tasks in your list:\n");
        for (int i = 0; i < index; i++) {
            String taskView = String.format(" %d.%s\n", i + 1, tasks[i].printTask());
            taskListView.append(taskView);
        }
        return taskListView.toString();
    }

    public String mark(String argument) {
        if (argument.isEmpty() || !TaskList.isNumeric(argument) || Integer.parseInt(argument.trim()) > index) {
            return "Please specify the valid task number to mark.";
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks[taskNum - 1].mark();
            return String.format(" Nice! I've marked this task as done:\n \t%s", tasks[taskNum - 1].printTask());
        }
    }

    public String unmark(String argument) {
        if (argument.isEmpty() || !TaskList.isNumeric(argument) || Integer.parseInt(argument.trim()) > index) {
            return "Please specify the valid task number to unmark.";
        } else {
            int taskNum = Integer.parseInt(argument.trim());
            tasks[taskNum - 1].unmark();
            return String.format(" OK, I've marked this task as not done yet:\n \t%s", tasks[taskNum - 1].printTask());
        }
    }
}
