package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import app.exceptions.InvalidCommandException;
import app.models.commands.AddTodoCommand;
import app.models.commands.Command;
import app.parsers.UserInputParser;

public class UserInputParserTest {
    @Test
    public void parse_addTodoCommand_success() throws Exception {
        Command result = UserInputParser.parse("todo read book");
        assertNotNull(result);
        assertEquals(AddTodoCommand.class, result.getClass());
    }

    @Test
    public void parse_addTodoCommand_exceptionThrown() {
        try {
            UserInputParser.parse("tod read book");
            fail("Expected InvalidCommandException");
        } catch (InvalidCommandException e) {
            assertEquals(" Huh? What does that mean ?o?", e.getMessage());
        }
    }
}
