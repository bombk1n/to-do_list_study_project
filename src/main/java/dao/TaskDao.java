package dao;

import entity.Task;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;

public class TaskDao {

    private static final TaskDao INSTANCE = new TaskDao();
    private static final String SAVE_SQL = """
            INSERT INTO tasks (name, description, due_date, is_completed) 
            VALUES (?, ?, ?, ?);
            """;

    private TaskDao() {}


    public Task save(Task ticket) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement ps = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ticket.getName());
            ps.setString(2, ticket.getDescription());
            ps.setDate(3, Date.valueOf(ticket.getDueDate()));
            ps.setBoolean(4,ticket.isCompleted());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()) {
                ticket.setId(generatedKeys.getInt("id"));
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }







    public static TaskDao getInstance() {
        return INSTANCE;
    }
}
