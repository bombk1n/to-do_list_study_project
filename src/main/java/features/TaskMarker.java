package features;



import dao.TaskDao;
import entity.Task;
import util.*;
import java.util.Iterator;
import java.util.Scanner;

public class TaskMarker extends Actions{
    private static final TaskDao instance = TaskDao.getInstance();
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

            if(input.equals("0")){
                return input;
            }

            try {
                int id = Integer.parseInt(input);
                Task task = instance.findById(id);
                if (task == null) {
                    System.out.println("Task with ID " + id + " does not exist.");
                    continue;
                }
                return input;
            }catch (Exception e){
                System.out.println("Enter a valid id");
            }
        }
    }

    @Override
    public void executeAction(String s) {
        int taskId = Integer.parseInt(s);
        Task task = instance.findById(taskId);
        task.setCompleted(!task.isCompleted());
        instance.update(task);
        System.out.println("Task with id " + taskId +" marked as "+ (task.isCompleted() ? "completed" : "uncompleted"));

    }
}

