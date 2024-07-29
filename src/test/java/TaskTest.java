import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.Task;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;



public class TaskTest {

    Task task;

    @BeforeEach
    void init(){
        task = new Task();
    }
    @Test
    void getId(){
        task.setId(1L);
        assertEquals(1,task.getId());
    }
    @Test
    void getName(){
        task.setName("name");
        assertEquals("name",task.getName());
    }
    @Test
    void getDueDate(){
        LocalDate localDate = LocalDate.now();
        task.setDueDate(localDate);
        assertEquals(localDate,task.getDueDate());
    }

    @Test
    void getDescription(){
        task.setDescription("description");
        assertEquals("description",task.getDescription());
    }
    @Test
    void isCompleted(){
        task.setCompleted(false);
        assertEquals(false,task.isCompleted());
    }
    @Test
    void createTask(){
        Task task = Task.createTask("Read a book", "Read 100 pages", LocalDate.of(2023, 12, 31));
        assertEquals(1, task.getId());
        assertEquals("Read a book", task.getName());
        assertEquals("Read 100 pages", task.getDescription());
        assertEquals(LocalDate.of(2023, 12, 31), task.getDueDate());
        assertFalse(task.isCompleted());
    }
}
