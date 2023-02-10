package duke.command;

import duke.task.Task;
import duke.util.container.TaskList;

public class MarkCommand extends Command {

    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList list) {
        Task task = list.get(index);
        task.markAsDone();
        return "Nice! I've marked this task as done:\n  " + task;
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
