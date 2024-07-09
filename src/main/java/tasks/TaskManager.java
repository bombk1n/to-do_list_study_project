package tasks;



import features.*;
import java.util.*;


public class TaskManager {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static ArrayList<Task> completedTasks = new ArrayList<>();
    public static boolean isRunning = true;
    private static final Scanner sca = new Scanner(System.in); // створення Scanner один раз

    public void start() {
        printTitle();
        while (isRunning) {
            printActions();
            int actionNumber = readAction();
            executeAction(actionNumber);
        }
    }

    public void executeAction(int actionNumber) {
        Actions action;
        switch (actionNumber) {

            case Actions.ADD_TASK:
                action = new TaskAdder();
                action.showActionsInformation();
                String adder = action.checkInput();
                if (!adder.equals("0")) {
                    action.executeAction(adder);
                }
                break;

            case Actions.MARK_AS_DONE:
                action = new TaskMarker();
                action.showActionsInformation();
                String marker = action.checkInput();
                if (!marker.equals("0")) {
                    action.executeAction(marker);
                }
                break;

            case Actions.REMOVE_TASK:
                action = new TaskRemover();
                action.showActionsInformation();
                String remover = action.checkInput();
                if (!remover.equals("0")) {
                    action.executeAction(remover);
                }
                break;

            case Actions.EDIT_TASK:
                action = new TaskEditor();
                action.showActionsInformation();
                String editor = action.checkInput();
                if (!editor.equals("0")) {
                    action.executeAction(editor);
                }
                break;

            case Actions.DISPLAY_ALL_TASKS:
                if (tasks.size() > 0 || completedTasks.size() > 0) {
                    action = new TaskDisplayer();
                    action.showActionsInformation();
                    action.executeAction(null);
                } else {
                    System.out.println("Your list is empty, add tasks first! ");
                }
                break;

            case Actions.SORT_TASKS_BY_DATE:
                if (tasks.size() > 0 || completedTasks.size() > 0) {
                    action = new TaskSorterByDate();
                    action.showActionsInformation();
                    action.executeAction(null);
                } else {
                    System.out.println("Your list is empty, add tasks first! ");
                }
                break;

            case Actions.EXIT:
                System.out.println();
                System.out.println("Program is finished!");
                isRunning = false;
                break;


        }
    }

    public void printTitle() {
        System.out.println("Welcome to To-Do List by bombk1n");
        System.out.println("--------------------------------");
    }

    public void printActions() {
        System.out.println();
        System.out.println("1. Add a task");
        System.out.println("2. Mark task as done");
        System.out.println("3. Remove task ");
        System.out.println("4. Edit task");
        System.out.println("5. Display all tasks");
        System.out.println("6. Sort tasks by date");
        System.out.println("7. Exit");
        System.out.println();
    }

    public int readAction() {
        List<Integer> listOfActions = List.of(1, 2, 3, 4, 5, 6, 7);
        while (true) {
            System.out.println("Enter a number:");
            int actionNumber = sca.nextInt();
            if (listOfActions.contains(actionNumber)) {
                return actionNumber;
            } else {
                System.out.println("FATAL: The entered number is incorrect");
            }
        }
    }
}

