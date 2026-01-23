public class Paradox {
    public static void main(String[] args) {
        String horizontalLine = "--------------------------------";
        String welcomeMessage = String.format("%s\n Hello! I'm %s!\n What can I do for you?\n%s", horizontalLine, "Paradox", horizontalLine);
        String exitMessage = String.format("%s\n Bye. Hope to see you again soon!\n%s", horizontalLine, horizontalLine);
        System.out.print(welcomeMessage + "\n" + exitMessage);
    }
}
