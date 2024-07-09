package features;




import tasks.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TaskEditor extends Actions{
    @Override
    public void showActionsInformation() {
        System.out.println();
        System.out.println("To edit a task please enter id and then use one of this construction:");
        System.out.println("[name],[description],[due date (format: dd-mm-yyyy)]");
        System.out.println("[name],[description]");
        System.out.println("[name]");
        System.out.println("insert a [-] when an update is not needed to that specific parameter");
        System.out.println();
        System.out.println("Enter 0 to return");
    }

    @Override
    public String checkInput() {
        return checkInputId()+","+checkInputLineForEditing();

    }
    public String checkInputId() {
        while(true){
            System.out.println("");
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
    public String checkInputLineForEditing() {
        while(true){

            System.out.println("");
            System.out.print("Enter information:");
            Scanner sca = new Scanner(System.in);
            String input = sca.nextLine();
            if (!input.equals("0")) {
                String[] elements = input.split(",");
                if (elements.length == 3) {
                    if (!elements[2].trim().equals("-")) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        try {
                            LocalDate.parse(elements[2].trim(), dtf); // Перевірка дати
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use the format dd-MM-yyyy.");
                            continue;
                        }
                    }
                    return input; // Якщо дата правильна або '-', виконуємо дію
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
        String[] elements = s.split(",", 2);
        int id = Integer.parseInt(elements[0]);
        String line = elements[1];
        String[] parts = line.split(",");

        for (Task task : TaskManager.tasks) {
            if (id == task.getId()) {
                if (parts.length > 0) {
                    if (!parts[0].trim().equals("-")) {
                        task.setName(parts[0].trim());
                    }
                }
                if (parts.length > 1) {
                    if (!parts[1].trim().equals("-")) {
                        task.setDescription(parts[1].trim());
                    }
                }
                if (parts.length > 2) {
                    if (!parts[2].trim().equals("-")) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        task.setDueDate(LocalDate.parse(parts[2].trim(), dtf));
                    }
                }
                System.out.println("Task " + id + " updated.");
                return;
            }
        }

        for (Task task : TaskManager.completedTasks) {
            if (id == task.getId()) {
                if (parts.length > 0) {
                    if (!parts[0].trim().equals("-")) {
                        task.setName(parts[0].trim());
                    }
                }
                if (parts.length > 1) {
                    if (!parts[1].trim().equals("-")) {
                        task.setDescription(parts[1].trim());
                    }
                }
                if (parts.length > 2) {
                    if (!parts[2].trim().equals("-")) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        task.setDueDate(LocalDate.parse(parts[2].trim(), dtf));
                    }
                }
                System.out.println("Task " + id + " updated.");
                return;
            }
        }

        System.out.println("Task with id " + id + " not found.");
    }
}

