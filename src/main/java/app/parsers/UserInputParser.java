package app.parsers;

import app.exceptions.InvalidCommandException;
import app.models.command.AddDeadlineCommand;
import app.models.command.AddEventCommand;
import app.models.command.AddTodoCommand;
import app.models.command.ByeCommand;
import app.models.command.Command;
import app.models.command.DeleteCommand;
import app.models.command.FindTaskCommand;
import app.models.command.ListCommand;
import app.models.command.MarkCommand;
import app.models.command.UnmarkCommand;

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
        String[] parts = userInput.split(" ", 2);
        String commandName = parts[0].trim();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        app.models.command.Command command;
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
