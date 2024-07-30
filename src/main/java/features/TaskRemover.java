package features;



import dao.TaskDao;
import entity.Task;
import util.*;
import java.util.Iterator;
import java.util.Scanner;

public class TaskRemover extends Actions{
    private static final TaskDao instance = TaskDao.getInstance();
    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To remove a task, enter ID and press ENTER");
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
                Task task = instance.findById(id);
                if(task == null){
                    System.out.println("Enter a valid id");
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
        instance.delete(taskId);
        System.out.println("Task with id " + taskId + " was successfully deleted.");
    }
}

