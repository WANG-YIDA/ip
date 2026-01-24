import java.util.Scanner;

public class Paradox {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String horizontalLine = "----------------------------------------------";

        TaskList taskList = new TaskList();

        // Print welcome message
        String welcomeMessage = String.format("%s\n Hi! I'm %s!\n What can I do for you?\n%s\n", horizontalLine, "Paradox", horizontalLine);
        System.out.print(welcomeMessage.indent(6));

        // Handle user commands
        String userInput = scanner.nextLine();

        mainLoop:
        while (true) {
            String[] parts = userInput.split(" ", 2);
            String command = parts[0].trim();
            String argument = parts.length > 1 ? parts[1].trim() : "";

            switch (command) {
                case "bye":
                    // Print exit message
                    String exitMessage = String.format("%s\n Bye! Hope to see you again soon:)\n%s", horizontalLine, horizontalLine);
                    System.out.print(exitMessage.indent(6));
                    break mainLoop;
                case "list":
                    // Print task list
                    String listResult = taskList.printList();
                    String listMsg = String.format("%s\n%s%s", horizontalLine, listResult, horizontalLine);
                    System.out.print(listMsg.indent(6));
                    break;
                case "mark":
                    // Mark a task as done
                    String markResult = taskList.mark(argument);
                    String markMsg = String.format("%s\n%s\n%s", horizontalLine, markResult, horizontalLine);
                    System.out.print(markMsg.indent(6));
                    break;
                case "unmark":
                    // Mark a task as undone
                    String unmarkResult = taskList.unmark(argument);
                    String unmarkMsg = String.format("%s\n%s\n%s", horizontalLine, unmarkResult, horizontalLine);
                    System.out.print(unmarkMsg.indent(6));
                    break;
                case "todo":
                    // Add task of type To-do
                    String addTodoTaskResult = taskList.addTask(argument, TaskType.TODO);
                    String addTodoTaskMsg = String.format("%s\n %s\n%s", horizontalLine, addTodoTaskResult, horizontalLine);
                    System.out.print(addTodoTaskMsg.indent(6));
                    break;
                case "deadline":
                    // Add task of type Deadline
                    String addDeadlineTaskResult = taskList.addTask(argument, TaskType.DEADLINE);
                    String addDeadlineTaskMsg = String.format("%s\n %s\n%s", horizontalLine, addDeadlineTaskResult, horizontalLine);
                    System.out.print(addDeadlineTaskMsg.indent(6));
                    break;
                case "event":
                    // Add task of type Event
                    String addEventTaskResult = taskList.addTask(argument, TaskType.EVENT);
                    String addEventTaskMsg = String.format("%s\n %s\n%s", horizontalLine, addEventTaskResult, horizontalLine);
                    System.out.print(addEventTaskMsg.indent(6));
                default:
                    // Not supported Commands
                    String defaultMsg = String.format("%s\n %s\n%s", horizontalLine, "Invalid Command", horizontalLine);
                    System.out.print(defaultMsg.indent(6));
            }

            // Read next user input
            userInput = scanner.nextLine();
        }
    }
}
