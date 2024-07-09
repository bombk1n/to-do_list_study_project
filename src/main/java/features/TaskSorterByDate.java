package features;


import tasks.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskSorterByDate extends Actions{
    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("Tasks will be sorted by due date.");
        System.out.println();
    }

    @Override
    public String checkInput() {
        // No input needed for sorting, returning an empty string
        return "";
    }

    @Override
    public void executeAction(String s) {
        List<Task> tasks = TaskManager.tasks;

        // Sort the tasks by due date
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                if (t1.getDueDate() == null && t2.getDueDate() == null) {
                    return 0;
                } else if (t1.getDueDate() == null) {
                    return 1;
                } else if (t2.getDueDate() == null) {
                    return -1;
                } else {
                    return t1.getDueDate().compareTo(t2.getDueDate());
                }
            }
        });

        // Sort the completed tasks by due date
        List<Task> completedTasks = TaskManager.completedTasks;
        Collections.sort(completedTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                if (t1.getDueDate() == null && t2.getDueDate() == null) {
                    return 0;
                } else if (t1.getDueDate() == null) {
                    return 1;
                } else if (t2.getDueDate() == null) {
                    return -1;
                } else {
                    return t1.getDueDate().compareTo(t2.getDueDate());
                }
            }
        });

        // Print sorted tasks
        System.out.println("Sorted tasks by due date:");
        for (Task task : tasks) {
            System.out.println(task);
        }

        System.out.println("Sorted completed tasks by due date:");
        for (Task task : completedTasks) {
            System.out.println(task);
        }
    }
}
