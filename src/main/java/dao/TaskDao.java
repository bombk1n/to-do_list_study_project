package dao;

import dto.TicketFilter;
import entity.Task;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class TaskDao {

    private static final TaskDao INSTANCE = new TaskDao();

    private static final String SAVE_SQL = """
            INSERT INTO tasks (name, description, due_date, is_completed) 
            VALUES (?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM tasks 
            WHERE id = ?;    
            """;

    private static final String UPDATE_SQL = """
            UPDATE tasks
            SET name = ?, description = ?, due_date = ?, is_completed = ? 
            WHERE id = ?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, description, due_date, is_completed
            FROM tasks
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id = ?;";

    private TaskDao() {
    }


    public Task save(Task task) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, task.getName());
            prepareStatement.setString(2, task.getDescription());
            prepareStatement.setDate(3, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
            prepareStatement.setBoolean(4, task.isCompleted());
            prepareStatement.executeUpdate();

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getLong("id"));
            }
            return task;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(int id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public boolean update(Task task) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, task.getName());
            prepareStatement.setString(2, task.getDescription());
            prepareStatement.setDate(3, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
            prepareStatement.setBoolean(4, task.isCompleted());
            prepareStatement.setLong(5, task.getId());
            return prepareStatement.executeUpdate() > 0;


        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Task> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(createTask(resultSet));
            }
            return tasks;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Task> findAll(TicketFilter filter){
        List<Object> params = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.name() !=null){
            whereSql.add("name LIKE ?");
            params.add("%" + filter.name() + "%");
        }
        if(filter.description() !=null){
            whereSql.add("description LIKE ?");
            params.add("%" + filter.description() + "%");
        }
        if(filter.is_completed() != null){
            whereSql.add(" is_completed = ? ");
            params.add(filter.is_completed());
        }
        params.add(filter.limit());
        params.add(filter.offset());
        String where = whereSql.isEmpty() ? "" : " WHERE " + String.join(" AND ", whereSql);
        String sql = FIND_ALL_SQL + where + " LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                prepareStatement.setObject(i + 1, params.get(i));
            }
            System.out.println(prepareStatement);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(createTask(resultSet));
            }

            return tasks;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Task findById(int id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                prepareStatement.setLong(1, id);
                Task task = null;
                ResultSet resultSet = prepareStatement.executeQuery();
                if(resultSet.next()) {
                    task = createTask(resultSet);
                }
                return task;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Task createTask(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTimestamp("due_date") != null ? resultSet.getTimestamp("due_date").toLocalDateTime().toLocalDate() : null,
                resultSet.getBoolean("is_completed")
        );
    }

    public static TaskDao getInstance() {
        return INSTANCE;
    }



}
