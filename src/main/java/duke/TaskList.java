package duke;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import duke.task.Task;

/**
 * A wrapper around {@code List<Task>}.
 * <p>
 * This class contains some basic utilities of a {@code List}, while also provides some special
 * methods to work with {@code Task}.
 */
public class TaskList implements Serializable {

    private static final long serialVersionUID = 1094392670804693665L;

    private List<Task> tasks;

    /**
     * Initializes an empty {@code TaskList}.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Initializes a {@code TaskList} by copying the given list of tasks.
     * 
     * @param tasks the list of tasks to populate this {@code TaskList}
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    private void raiseIfInvalidIndex(int index) {
        int n = tasks.size();
        if (index >= 1 && index <= n) {
            return;
        }
        String msg = String.format("Invalid index - valid index is from 1 to %d", n);
        throw new DukeIndexOutOfBoundsException(msg);
    }

    /**
     * Adds a new task into this {@code TaskList}.
     * 
     * @param task the task to be added
     * @return {@code true} if the task was added, otherwise {@code false}
     */
    public boolean add(Task task) {
        return tasks.add(task);
    }

    /**
     * Gets the task located at the given index.
     * 
     * @param index the index of the task
     * @return the accessed task
     */
    public Task get(int index) {
        raiseIfInvalidIndex(index);
        return tasks.get(index - 1);
    }

    /**
     * Removes the task located at the given index.
     * 
     * @param index the index of the task
     * @return the removed task
     */
    public Task remove(int index) {
        raiseIfInvalidIndex(index);
        return tasks.remove(index - 1);
    }

    /**
     * Returns the size of this {@code TaskList}.
     * 
     * @return the number of tasks stored in this {@code TaskList}
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Counts the number of stored tasks.
     * 
     * @return a string representing the number of stored tasks
     */
    public String countTaskAsString() {
        int n = tasks.size();
        return String.format("%d task%s", n, n < 2 ? "" : "s");
    }

    private String listTasksFrom(Stream<Task> stream) {
        return stream.map(new Function<Task, String>() {
            int index = 1;

            @Override
            public String apply(Task task) {
                String out = String.format("%d.%s", index, task);
                index++;
                return out;
            }
        }).collect(Collectors.joining("\n"));
    }

    /**
     * Lists all stored tasks, with indicies. Indicies start from {@code 1}.
     * 
     * @return a string showing the contents of all tasks, with indicies.
     */
    public String listAllTasks() {
        return listTasksFrom(tasks.stream());
    }

    public String listTasksContainKeyword(String keyword) {
        return listTasksFrom(tasks.stream().filter(task -> task.contains(keyword)));
    }

    @Override
    public String toString() {
        return String.format("TaskList: %s", tasks);
    }

}
