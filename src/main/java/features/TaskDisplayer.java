package features;


import dao.TaskDao;
import dto.TicketFilter;
import entity.Task;
import util.*;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;


public class TaskDisplayer extends Actions {
    @Override
    public void showActionsInformation() {
        System.out.println("If you want to display in current order by id or due_date, please use the following from:");
        System.out.println("[id/due_date],[asc/desc]");
        System.out.println("insert a \"_\" if you needn't use order or ascending");
        System.out.println();
        System.out.println("Enter 0 to return");
    }

    @Override
    public String checkInput() {
        Scanner sca = new Scanner(System.in);
        while (true) {
            System.out.println("Enter from:");
            String input = sca.nextLine().trim();
            if (input.equals("0")) {
                return input;  // Повертає "0" для виходу
            }
            String[] elements = input.split(",");
            if (elements.length == 2) {
                String orderBy = elements[0].trim();
                if (!orderBy.equalsIgnoreCase("id") && !orderBy.equalsIgnoreCase("due_date") && !orderBy.equals("_")) {
                    System.out.println("Invalid order by, try again");
                    continue;
                }
                boolean ascending;
                if (elements[1].trim().equalsIgnoreCase("asc") || elements[1].trim().equals("_")) {
                    ascending = true;
                } else if (elements[1].trim().equalsIgnoreCase("desc")) {
                    ascending = false;
                } else {
                    System.out.println("Invalid ascending, try again");
                    continue;
                }
                return orderBy + "," + ascending;
            } else {
                System.out.println("Failed displaying order, please check input");
            }
        }
    }

    @Override
    public void executeAction(String s) {
        TaskDao instance = TaskDao.getInstance();
        String[] elements = s.split(",");
        if(elements.length == 2) {
            String orderBy = elements[0];
            boolean ascending = Boolean.parseBoolean(elements[1]);

            System.out.println();
            System.out.println("Uncompleted tasks:");
            List<Task> uncompletedTasks = instance.findAll(new TicketFilter(instance.size(), 0, null, null, false), orderBy.equals("_") ? null : orderBy, ascending);
            if (!uncompletedTasks.isEmpty()) {
                uncompletedTasks.forEach(System.out::println);
            } else {
                System.out.println("No uncompleted tasks found.");
            }

            System.out.println("----------------");
            System.out.println("Completed tasks: ");
            List<Task> completedTasks = instance.findAll(new TicketFilter(instance.size(), 0, null, null, true), orderBy.equals("_") ? null : orderBy, ascending);
            if (!completedTasks.isEmpty()) {
                completedTasks.forEach(System.out::println);
            } else {
                System.out.println("No completed tasks found.");
            }
        }else{
            System.out.println("Failed to display tasks. Please try again");
        }
    }
}
