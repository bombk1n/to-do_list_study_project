package features;


import dao.TaskDao;
import entity.Task;
import util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.Scanner;

public class TaskAdder extends Actions {
    private static final Scanner sca = new Scanner(System.in);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To add a task, please use the following format:");
        System.out.println("[name],[description],[due date (format: dd-MM-yyyy)]");
        System.out.println("mark unnecessary parameters as \"_\", except for the parameter \"name\"");
        System.out.println();
        System.out.println("Enter 0 to return");
    }

    @Override
    public String checkInput() {
        while (true) {
            System.out.println();
            System.out.println("Enter task information:");
            String input = sca.nextLine().trim();

            if (input.equals("0")) {
                return input;
            }

            String[] elements = input.split(",");
            if (elements.length == 3) {
                String name = elements[0].trim();
                String description = elements[1].trim();
                String dateStr = elements[2].trim();

                if (name.isEmpty() || name.equals("_") || name.length() > 255) {
                    System.out.println("Name is required and cannot be empty or \"_\" or contains more than 255 characters.");
                    continue;
                }

                if (description.equals("_") || description.isEmpty()) {
                    description = null;
                }

                LocalDate dueDate = null;
                if (!dateStr.equals("_") && !dateStr.isEmpty()) {
                    try {
                        dueDate = LocalDate.parse(dateStr, dtf);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use the format dd-MM-yyyy.");
                        continue;
                    }
                }

                return String.join(",", name, description == null ? "_" : description, dueDate == null ? "_" : dateStr);
            } else {
                System.out.println("Invalid input format. Please use three comma-separated values.");
            }
        }
    }

    @Override
    public void executeAction(String s) {
        String[] elements = s.split(",");
        if (elements.length == 3) {
            String name = elements[0].trim();
            String description = elements[1].trim();
            String dateStr = elements[2].trim();
            LocalDate parse = null;
            if(description.equals("_")){
                description = null;
            }
            if(!dateStr.equals("_")){
                try {
                     parse = LocalDate.parse(dateStr, dtf);
                }catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Task not created.");
                    return;
                }
            }
            Task task = new Task(name,description,parse);
            TaskDao.getInstance().save(task);
        }else {
            System.out.println("Failed to create task. Please check your input.");
        }

    }
}
