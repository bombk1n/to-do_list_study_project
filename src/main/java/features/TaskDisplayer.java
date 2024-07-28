package features;


import entity.Task;
import util.*;


public class TaskDisplayer extends Actions {
    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("Uncompleted tasks:");
    }

    @Override
    public String checkInput() {
        return "";
    }

    @Override
    public void executeAction(String s) {

        for(Task task : TaskManager.tasks){
            if(!task.isCompleted()){
                System.out.println(task);
            }

        }
        System.out.println("----------------");
        System.out.println("Completed tasks: ");
        TaskManager.completedTasks.forEach(System.out::println);
    }
}
