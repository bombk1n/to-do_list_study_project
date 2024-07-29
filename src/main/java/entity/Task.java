package entity;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted = false;

    public Long getId() {
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

    public void setId(Long id) {
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



    public Task() {
    }

    public Task(String name, String description, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        isCompleted = false;
    }

    public Task(Long id, String name, String description, LocalDate dueDate, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
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

