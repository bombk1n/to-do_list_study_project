import dao.TaskDao;
import dto.TicketFilter;
import entity.Task;

import java.time.LocalDate;
import java.util.List;

public class DaoRunner {

    public static void main(String[] args) {
        TaskDao taskDao = TaskDao.getInstance();


        List<Task> all = taskDao.findAll(new TicketFilter(20,0,"пі",null,true));
        for (Task task : all) {
            System.out.println(task);
        }

    }
}
