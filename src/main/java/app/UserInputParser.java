package app;

import app.exception.InvalidCommandException;
import app.model.command.AddDeadlineCommand;
import app.model.command.AddEventCommand;
import app.model.command.AddTodoCommand;
import app.model.command.ByeCommand;
import app.model.command.DeleteCommand;
import app.model.command.FindTaskCommand;
import app.model.command.ListCommand;
import app.model.command.MarkCommand;
import app.model.command.UnmarkCommand;

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
    public static app.model.command.Command parse(String userInput) throws InvalidCommandException {
        String[] parts = userInput.split(" ", 2);
        String commandName = parts[0].trim();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        app.model.command.Command command;
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
