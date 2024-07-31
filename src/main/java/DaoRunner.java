import dao.TaskDao;
import dto.TicketFilter;
import entity.Task;

import java.time.LocalDate;
import java.util.List;

public class DaoRunner {

    public static void main(String[] args) {
        TaskDao taskDao = TaskDao.getInstance();


        List<Task> uncompletedTasks = taskDao.findAll(new TicketFilter(100,0,"a","a",null),null,false);
        for (Task task : uncompletedTasks) {
            System.out.println(task);
        }

    }
}
