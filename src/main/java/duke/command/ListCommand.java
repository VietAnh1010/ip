package duke.command;

import duke.util.container.TaskList;

public class ListCommand extends Command {

    @Override
    public String execute(TaskList list) {
        return "Here are the tasks in your list:\n" + list.listAllTasks();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ListCommand;
    }
}
