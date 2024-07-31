package features;


import dao.TaskDao;
import entity.Task;
import util.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TaskEditor extends Actions {

    public static final Scanner sca = new Scanner(System.in);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final TaskDao instance = TaskDao.getInstance();

    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To edit a task please enter id and then use the construction:");
        System.out.println("[name],[description],[due date (format: dd-mm-yyyy)]");
        System.out.println("insert a \"_\" when an update is not needed to that specific parameter");
        System.out.println();
        System.out.println("Enter 0 to return");
    }

    @Override
    public String checkInput() {
        String id = checkInputId();
        if (id.equals("0")) {
            return "0";
        }
        String editInput = checkInputLineForEditing();
        if(editInput.equals("0")) {
            return "0";
        }
        return id + "," + editInput;

    }

    public String checkInputId() {
        while (true) {
            System.out.println();
            System.out.print("Enter task id:");
            String input = sca.nextLine().trim();

            if (input.equals("0")) {
                return "0";
            }

            try {
                int id = Integer.parseInt(input);

                // Перевірка існування завдання
                Task task = instance.findById(id);
                if (task == null) {
                    System.out.println("Task with ID " + id + " does not exist.");
                    continue;
                }

                return input;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid id");
            }
        }
    }

    public String checkInputLineForEditing() {
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

                if (name.isEmpty() || name.length() > 255) {
                    System.out.println("Name cannot be empty or contains more than 255 characters.");
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
        if (elements.length == 4) {
            int id = Integer.parseInt(elements[0]);
            String name = elements[1].trim();
            String description = elements[2].trim();
            String dateStr = elements[3].trim();
            Task originalTask = instance.findById(id);
            LocalDate parse = null;
            if (name.equals("_")) {
                name = originalTask.getName();
            }
            if (description.equals("_")) {
                description = originalTask.getDescription();
            }
            if (!dateStr.equals("_")) {
                try {
                    parse = LocalDate.parse(dateStr, dtf);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Task not created.");
                    return;
                }
            } else {
                parse = originalTask.getDueDate();
            }
            Task newTask = new Task(originalTask.getId(), name, description, parse, originalTask.isCompleted());
            instance.update(newTask);
            System.out.println("Task with id "+id+" updated.");
        } else {
            System.out.println("Failed to create task. Please check your input.");
        }
    }
}

