package features;



import dao.TaskDao;
import entity.Task;
import util.*;
import java.util.Iterator;
import java.util.Scanner;

public class TaskMarker extends Actions{
    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To complete a task, enter id and press ENTER: ");
        System.out.println();
        System.out.println("Enter 0 to RETURN");
    }

    @Override
    public String checkInput() {
        while(true){
            System.out.println();
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
        TaskDao taskDao = TaskDao.getInstance();
        int taskId = Integer.parseInt(s);
        Task task = taskDao.findById(taskId);
        task.setCompleted(!task.isCompleted());
        taskDao.update(task);
        System.out.println("Task with id " + taskId +" marked as "+ (task.isCompleted() ? "completed" : "uncompleted"));


        System.out.println("Task with id " + taskId + " not found.");
    }
}

