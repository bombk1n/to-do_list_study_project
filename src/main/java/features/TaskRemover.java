package features;



import tasks.*;
import java.util.Iterator;
import java.util.Scanner;

public class TaskRemover extends Actions{
    @Override
    public void showActionsInformation() {
        System.out.println("");
        System.out.println("To remove a task, enter ID and press ENTER");
        System.out.println("");
        System.out.println("Enter 0 to RETURN");
    }

    @Override
    public String checkInput() {
        while(true){
            System.out.println("");
            System.out.print("Enter task id:");
            Scanner sca = new Scanner(System.in);
            String input = sca.nextLine();
            try {
                int id = Integer.parseInt(input);
                return input;
            }catch (Exception e){
                System.out.println("Enter a valid id");
            }
        }
    }

    @Override
    public void executeAction(String s) {
        int taskId = Integer.parseInt(s);
        Iterator<Task> iterator = TaskManager.tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == taskId) {
                iterator.remove();
                System.out.println("Task " + taskId + " was removed");
                return;
            }
        }


        iterator = TaskManager.completedTasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == taskId) {
                iterator.remove();
                System.out.println("Task " + taskId + " was removed");
                return;
            }
        }

        System.out.println("Task with id " + taskId + " not found.");
    }
}

