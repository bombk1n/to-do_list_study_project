package features;

public abstract class Actions {
    public static final int ADD_TASK = 1;
    public static final int MARK_AS_DONE = 2;
    public static final int REMOVE_TASK = 3;
    public static final int EDIT_TASK = 4;
    public static final int DISPLAY_ALL_TASKS = 5;
    public static final int EXIT = 6;



    public abstract void showActionsInformation();
    public abstract String checkInput();
    public abstract void executeAction(String s);

}
