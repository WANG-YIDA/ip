package app;

import app.exception.*;
import app.model.command.*;

public class UserInputParser {
    public static Command parse(String userInput) throws InvalidCommandException {
        String[] parts = userInput.split(" ", 2);
        String commandName = parts[0].trim();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        Command command;
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
        default:
            throw new InvalidCommandException(" Invalid Command:(");
        }
        return command;
    }
}
