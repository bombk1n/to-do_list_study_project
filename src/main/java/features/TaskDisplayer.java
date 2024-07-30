package features;


import dao.TaskDao;
import dto.TicketFilter;
import entity.Task;
import util.*;

import java.util.List;


public class TaskDisplayer extends Actions {
    @Override
    public void showActionsInformation() {

    }

    @Override
    public String checkInput() {
        return "";
    }

    @Override
    public void executeAction(String s) {
        TaskDao instance = TaskDao.getInstance();
        System.out.println();
        System.out.println("Uncompleted tasks:");
        List<Task> uncompletedTasks = instance.findAll(new TicketFilter(instance.size(), 0,null,null,false));
        if (!uncompletedTasks.isEmpty()) {
            uncompletedTasks.forEach(System.out::println);
        }else{
            System.out.println("No uncompleted tasks found.");
        }

        System.out.println("----------------");
        System.out.println("Completed tasks: ");
        List<Task> completedTasks = instance.findAll(new TicketFilter(instance.size(),0,null,null,true));
        if (!completedTasks.isEmpty()) {
            completedTasks.forEach(System.out::println);
        }else{
            System.out.println("No completed tasks found.");
        }
    }
}
