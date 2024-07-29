package features;


import dao.TaskDao;
import entity.Task;
import util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import java.util.Scanner;

public class TaskAdder extends Actions {
    private static final Scanner sca = new Scanner(System.in);

    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To add a task, please use one of the following formats:");
        System.out.println("[name],[description],[due date (format: dd-MM-yyyy)]");
        System.out.println("[name],[description]");
        System.out.println("[name]");
        System.out.println();
        System.out.println("Enter 0 to return");
    }

    @Override
    public String checkInput() {
        while (true)  {
            System.out.println();
            System.out.println("Enter task information:");
            String input = sca.nextLine().trim();

            if (!input.equals("0")) {
                String[] elements = input.split(",");
                if (elements.length == 3) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    try {
                        LocalDate.parse(elements[2].trim(), dtf);
                        return input;

                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use the format dd-MM-yyyy.");
                    }
                } else if (elements.length == 1 || elements.length == 2) {
                    return input;

                } else {
                    System.out.println("Invalid input format. Please follow the instructions.");
                }
            } else {
                return input;
            }
        }
    }

    @Override
    public void executeAction(String s) {
        String[] elements = s.split(",");
        Task task = null;
        switch (elements.length) {
            case 1:
                task = new Task(elements[0].trim(), null, null);
                break;
            case 2:
                task = new Task(elements[0].trim(), elements[1].trim(), null);
                break;
            case 3:
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                task = new Task(elements[0].trim(), elements[1].trim(), LocalDate.parse(elements[2].trim(), dtf));
                break;
        }

        if (task != null) {
            TaskDao.getInstance().save(task);
        } else {
            System.out.println("Failed to create task. Please check your input.");
        }
    }
}
