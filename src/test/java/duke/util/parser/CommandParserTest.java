package duke.util.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import duke.DukeUtils;
import duke.command.AddCommand;
import duke.command.Command;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.RemoveCommand;
import duke.command.UnmarkCommand;
import duke.exception.DukeRuntimeException;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.TodoTask;

public class CommandParserTest {
    private static final Class<DukeRuntimeException> EXPECTED_EXCEPTION_CLASS =
            DukeRuntimeException.class;

    private Command parseCommand(String input) {
        return CommandParser.of(input).parse();
    }

    @Test
    public void shouldCorrectlyParseSomeCommand() {
        assertEquals(parseCommand("list"), new ListCommand());
        assertEquals(parseCommand("bye"), new ExitCommand());
        assertEquals(parseCommand("todo eat"), new AddCommand(new TodoTask("eat")));
        assertEquals(parseCommand("deadline homework --by 2023-01-01"), new AddCommand(
                new DeadlineTask("homework", DukeUtils.parseDate("2023-01-01").get())));
        assertEquals(parseCommand("event survive --from 2023-01-01 --to 2023-10-10"),
                new AddCommand(new EventTask("survive", DukeUtils.parseDate("2023-01-01").get(),
                        DukeUtils.parseDate("2023-10-10").get())));
        assertEquals(parseCommand("mark 10"), new MarkCommand(10));
        assertEquals(parseCommand("unmark 10"), new UnmarkCommand(10));
        assertEquals(parseCommand("delete 10"), new RemoveCommand(10));
        assertEquals(parseCommand("find money"), new FindCommand("money"));
    }

    @Test
    public void shouldThrowForEmptyDescription() {
        assertThrows(EXPECTED_EXCEPTION_CLASS, () -> parseCommand("todo"));
        assertThrows(EXPECTED_EXCEPTION_CLASS,
                () -> parseCommand("deadline --by 2023-01-01"));
        assertThrows(EXPECTED_EXCEPTION_CLASS,
                () -> parseCommand("event --from 2023-01-01 --to 2023-10-10"));

    }

    @Test
    public void shouldThrowForInvalidIntArgument() {
        assertThrows(EXPECTED_EXCEPTION_CLASS, () -> parseCommand("mark 10v"));
        assertThrows(EXPECTED_EXCEPTION_CLASS, () -> parseCommand("delete 10v"));
    }
}
