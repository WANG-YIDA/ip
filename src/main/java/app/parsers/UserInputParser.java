package app.parsers;

import app.exceptions.InvalidCommandException;
import app.models.commands.AddDeadlineCommand;
import app.models.commands.AddEventCommand;
import app.models.commands.AddTodoCommand;
import app.models.commands.ByeCommand;
import app.models.commands.Command;
import app.models.commands.DeleteCommand;
import app.models.commands.FindTaskCommand;
import app.models.commands.ListCommand;
import app.models.commands.MarkCommand;
import app.models.commands.UnmarkCommand;

/**
 * Parses raw user input and returns the corresponding Command object.
 */
public class UserInputParser {
    /**
     * Parse the provided user input into a Command instance.
     *
     * @param userInput raw input string
     * @return Command implementation matching the input
     * @throws InvalidCommandException when the command is not recognized
     */
    public static Command parse(String userInput) throws InvalidCommandException {
        assert userInput != null : "User input cannot be null";

        String[] parts = userInput.split(" ", 2);
        String commandName = parts[0].trim();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        app.models.commands.Command command;
        switch (commandName) {
        case "bye":
            command = new ByeCommand();
            break;
        case "list":
            command = new ListCommand();
            break;
        case "mark":
            command = new MarkCommand(argument);
            break;
        case "unmark":
            command = new UnmarkCommand(argument);
            break;
        case "todo":
            command = new AddTodoCommand(argument);
            break;
        case "deadline":
            command = new AddDeadlineCommand(argument);
            break;
        case "event":
            command = new AddEventCommand(argument);
            break;
        case "delete":
            command = new DeleteCommand(argument);
            break;
        case "find":
            command = new FindTaskCommand(argument);
            break;
        default:
            throw new InvalidCommandException(" Invalid Command:(");
        }
        return command;
    }
}
