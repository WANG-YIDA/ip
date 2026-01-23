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
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

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
                    System.out.println(listMsg.indent(6));
                    break;
                case "mark":
                    String markResult = taskList.mark(argument);
                    String markMsg = String.format("%s\n%s\n%s", horizontalLine, markResult, horizontalLine);
                    System.out.println(markMsg.indent(6));
                    break;
                case "unmark":
                    String unmarkResult = taskList.unmark(argument);
                    String unmarkMsg = String.format("%s\n%s\n%s", horizontalLine, unmarkResult, horizontalLine);
                    System.out.println(unmarkMsg.indent(6));
                    break;
                default:
                    // Store user task
                    String addTaskResult = taskList.addTask(userInput);
                    String addTaskMsg = String.format("%s\n %s\n%s", horizontalLine, addTaskResult, horizontalLine);
                    System.out.println(addTaskMsg.indent(6));
            }

            // Read next user input
            userInput = scanner.nextLine();
        }
    }
}
