import java.util.Scanner;

public class Paradox {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String horizontalLine = "------------------------------------------";

        String[] tasks = new String[100];
        int index = 0;

        // Print welcome message
        String welcomeMessage = String.format("%s\n Hi! I'm %s!\n What can I do for you?\n%s\n", horizontalLine, "Paradox", horizontalLine);
        System.out.print(welcomeMessage.indent(6));

        // Handle user commands
        String userInput = scanner.nextLine();
        mainLoop:
        while (true) {
            switch (userInput) {
                case "bye":
                    // Print exit message
                    String exitMessage = String.format("%s\n Bye! Hope to see you again soon:)\n%s", horizontalLine, horizontalLine);
                    System.out.print(exitMessage.indent(6));
                    break mainLoop;
                case "list":
                    // Print task list
                    StringBuilder listView = new StringBuilder();
                    listView.append(horizontalLine);
                    listView.append("\n Here are the tasks in your list:\n");
                    for (int i = 0; i < index; i++) {
                        String taskContent = String.format(" %d. %s\n", i + 1, tasks[i]);
                        listView.append(taskContent);
                    }
                    listView.append(horizontalLine);
                    System.out.print(listView.toString().indent(6));
                    break;
                default:
                    // Store user task
                    if (index >= tasks.length) {
                        String failMessage = String.format("%s\n %s\n%s", horizontalLine, "Task list is full, cannot add new task:(", horizontalLine);
                        System.out.println(failMessage.indent(6));
                        break;
                    } else {
                        tasks[index] = userInput;
                        index++;
                    }

                    String successMessage = String.format("%s\n Task Added: %s\n%s", horizontalLine, userInput, horizontalLine);
                    System.out.print(successMessage.indent(6));
            }

            // Read next user input
            userInput = scanner.nextLine();
        }
    }
}
