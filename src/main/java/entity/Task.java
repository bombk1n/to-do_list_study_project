package entity;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Task {
    private static int nextId = 1;
    private int id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted = false;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Task(int id, String name, LocalDate dueDate, String description, boolean isCompleted) {
        this.id = nextId++;
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public static Task createTask(String name, String description, LocalDate dueDate){
        Task task = new Task();
        task.setId(nextId++);
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(dueDate);

        return task;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id= ").append(id).append(" | ");
        sb.append("name= ").append(name);

        if (description != null && !description.isEmpty()) {
            sb.append(" | description= ").append(description);
        }

        if (dueDate != null) {
            sb.append(" | due date= ").append(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(dueDate));
        }

        sb.append(" | ").append(isCompleted ? "completed" : "uncompleted");

        return sb.toString();
    }
}

