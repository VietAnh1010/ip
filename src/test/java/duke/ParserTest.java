package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import duke.command.AddTaskCommand;
import duke.command.ExitCommand;
import duke.command.FindTaskCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.RemoveTaskCommand;
import duke.command.UnmarkCommand;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.TodoTask;

public class ParserTest {

    private static final Class<DukeException> exceptionClass = DukeException.class;

    @Test
    public void shouldCorrectlyParseSomeCommand() {
        assertEquals(Parser.parseCommand("list"), new ListCommand());
        assertEquals(Parser.parseCommand("bye"), new ExitCommand());
        assertEquals(Parser.parseCommand("todo eat"), new AddTaskCommand(new TodoTask("eat")));
        assertEquals(Parser.parseCommand("deadline homework /by 2023-01-01"), new AddTaskCommand(
                new DeadlineTask("homework", DukeUtils.convertStringToDate("2023-01-01").get())));
        assertEquals(Parser.parseCommand("event live /from current /to future"),
                new AddTaskCommand(new EventTask("live", "current", "future")));
        assertEquals(Parser.parseCommand("mark 10"), new MarkCommand(10));
        assertEquals(Parser.parseCommand("unmark 10"), new UnmarkCommand(10));
        assertEquals(Parser.parseCommand("delete 10"), new RemoveTaskCommand(10));
        assertEquals(Parser.parseCommand("find money"), new FindTaskCommand("money"));
    }

    @Test
    public void shouldThrowForEmptyDescription() {
        DukeException ex;
        ex = assertThrows(exceptionClass, () -> Parser.parseCommand("todo"));
        assertTrue(ex.getMessage().contains("description cannot be empty"));
        ex = assertThrows(exceptionClass, () -> Parser.parseCommand("deadline /by 2023-01-01"));
        assertTrue(ex.getMessage().contains("description cannot be empty"));
        ex = assertThrows(exceptionClass,
                () -> Parser.parseCommand("event /from current /to future"));
        assertTrue(ex.getMessage().contains("description cannot be empty"));
    }

    @Test
    public void shouldThrowForInvalidIntArgument() {
        DukeException ex;
        ex = assertThrows(exceptionClass, () -> Parser.parseCommand("mark 10v"));
        assertTrue(ex.getMessage().contains("expect an integer as argument"));
        ex = assertThrows(exceptionClass, () -> Parser.parseCommand("delete 10v"));
        assertTrue(ex.getMessage().contains("expect an integer as argument"));
    }
}
