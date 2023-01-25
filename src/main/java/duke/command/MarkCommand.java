package duke.command;

import duke.TaskList;
import duke.UI;
import duke.task.Task;

public class MarkCommand extends Command {

    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList list) {
        Task task = list.get(index);
        task.markAsDone();
        UI.echo("Nice! I've marked this task as done:\n  " + task);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MarkCommand)) {
            return false;
        }
        MarkCommand cmd = (MarkCommand) obj;
        return index == cmd.index;
    }
}
