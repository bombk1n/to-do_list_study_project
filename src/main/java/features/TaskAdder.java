package features;


import entity.Task;
import util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TaskAdder extends Actions {
    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To add task please use one of this construction:");
        System.out.println("[name],[description],[due date (format: dd-mm-yyyy)]");
        System.out.println("[name],[description]");
        System.out.println("[name]");
        System.out.println();
        System.out.println("Enter 0 to return");
    }

    @Override
    public String checkInput() {
        while (true) {
            System.out.println();
            System.out.println("Enter task information:");
            Scanner sca = new Scanner(System.in);
            String input = sca.nextLine();

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
                task = Task.createTask(elements[0].trim(), null, null);
                break;
            case 2:
                task = Task.createTask(elements[0].trim(), elements[1].trim(), null);
                break;
            case 3:
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                task = Task.createTask(elements[0].trim(), elements[1].trim(), LocalDate.parse(elements[2].trim(), dtf));
        }
        TaskManager.tasks.add(task);

    }
}
