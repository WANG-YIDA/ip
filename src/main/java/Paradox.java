import java.util.Scanner;

public class Paradox {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String horizontalLine = "------------------------------------------";

        // Print welcome message
        String welcomeMessage = String.format("%s\n Hi! I'm %s!\n What can I do for you?\n%s\n", horizontalLine, "Paradox", horizontalLine);
        System.out.print(welcomeMessage.indent(6));

        String userInput = scanner.nextLine();
        while (!userInput.equals("bye")) {
            // Echo user input
            String echo = String.format("%s\n %s\n%s\n", horizontalLine, userInput, horizontalLine);
            System.out.print(echo.indent(6));

            // Read next user input
            userInput = scanner.nextLine();
        }

        // Print exit message
        String exitMessage = String.format("%s\n Bye! Hope to see you again soon:)\n%s\n", horizontalLine, horizontalLine);
        System.out.print(exitMessage.indent(6));
    }
}
