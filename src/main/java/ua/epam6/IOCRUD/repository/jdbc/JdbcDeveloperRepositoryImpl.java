package ua.epam6.IOCRUD.repository.jdbc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.mappers.JdbcDeveloperMapper;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("developerRepository")
public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private static final Logger log = Logger.getLogger(JdbcDeveloperRepositoryImpl.class);
    private final String INSERT_QUERY = "INSERT INTO 1?(2?) VALUES (3?);";
    private final String SELECT_QUERY_SIMPLE = "SELECT 1? FROM 2?;";
    private final String SELECT_QUERY_COMPLEX = "SELECT d.id, d.first_name, d.last_name, s.id, s.name, a.id, a.name, a.status " +
            "FROM developers d " +
            "LEFT JOIN (" +
            "SELECT ds.developer_id, s.id, s.name " +
            "FROM developer_skill ds " +
            "INNER JOIN skills s " +
            "ON ds.skill_id = s.id) s " +
            "ON d.id = s.developer_id " +
            "LEFT JOIN accounts a " +
            "ON d.account_id = a.id " +
            "1?;";
    private final String UPDATE_QUERY = "UPDATE 1? SET 2? WHERE 3?;";
    private final String DELETE_QUERY = "DELETE FROM 1? WHERE 2?;";

    private Connection connection;

    public JdbcDeveloperRepositoryImpl() {
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to database", e);
        }
    }

    @Override
    public void create(Developer developerModel) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            statement.execute(INSERT_QUERY.replace("1?", "developers")
                    .replace("2?", "first_name, last_name, account_id")
                    .replace("3?", "'" + developerModel.getFirstName() + "', '" +
                            developerModel.getLastName() + "', '" + developerModel.getAccount().getId() + "'"));
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_SIMPLE.replace("1?", "Max(id)")
                    .replace("2?", "developers"));
            resultSet.first();
            long developerId = resultSet.getLong(1);
            for (Skill skill : developerModel.getSkills()) {
                statement.addBatch(INSERT_QUERY.replace("1?", "developer_skill")
                        .replace("2?", "developer_id, skill_id")
                        .replace("3?", "'" + developerId + "', '" + skill.getId() + "'"));
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Created entry(Database): " + developerModel);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Wrong SQL query to database in creation", e);
        }
    }

    @Override
    public Developer getById(Long ID) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_COMPLEX
                    .replace("1?", "WHERE d.Id = '" + ID + "'"));
            connection.commit();
            connection.setAutoCommit(true);
            Developer developer = new JdbcDeveloperMapper().map(resultSet, ID);
            log.debug("Read entry(Database) with ID: "+ ID);
            return developer;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Rollback denied(Database)");
            }
            log.error("Wrong SQL query to H2 in reading", e);
        }
        return null;
    }

    @Override
    public void update(Developer updatedModel) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            if(statement.executeUpdate(UPDATE_QUERY.replace("1?", "Developers")
                    .replace("2?", "First_name = '" + updatedModel.getFirstName() + "', " +
                            "Last_name = '" + updatedModel.getLastName() + "', " +
                            "Account_id = '" + updatedModel.getAccount().getId() + "' ")
                    .replace("3?", "Id = '" + updatedModel.getId() + "'")) < 1) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    log.error("Rollback denied(Database)");
                }
                log.warn("No such entry: " + updatedModel);
            }
            statement.execute(DELETE_QUERY.replace("1?", "Developer_skill")
                    .replace("2?", "Developer_id = '" + updatedModel.getId() + "'"));
            for (Skill skill : updatedModel.getSkills()) {
                statement.addBatch(INSERT_QUERY.replace("1?", "Developer_skill")
                        .replace("2?", "Developer_id, Skill_id")
                        .replace("3?", "'" + updatedModel.getId() + "', '" + skill.getId() + "'"));
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Updated entry(Database): " + updatedModel);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Rollback denied(Database)");
            }
            log.error("Wrong SQL query to database in updating", e);
        }
    }

    @Override
    public void delete(Long deletedEntry) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            statement.execute(DELETE_QUERY.replace("1?", "Developer_skill")
                    .replace("2?", "Developer_id = '" + deletedEntry + "'"));
            if(statement.executeUpdate(DELETE_QUERY.replace("1?", "Developers")
                    .replace("2?", "Id = '" + deletedEntry + "'")) < 1) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    log.error("Rollback denied(Database)");
                }
                log.warn("No such entry with ID: " + deletedEntry);
            }
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Delete entry(Database) with ID: " + deletedEntry);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Rollback denied(Database)");
            }
            log.error("Wrong SQL query to database in deleting", e);
        }
    }

    @Override
    public List<Developer> getAll() {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_COMPLEX.replace("1?", ""));
            ArrayList<Developer> developers = new ArrayList<>();
            JdbcDeveloperMapper mapper = new JdbcDeveloperMapper();
            long index = -1;
            while (resultSet.next()){
                if(index != resultSet.getLong(1)) {
                    index = resultSet.getLong(1);
                    developers.add(mapper.map(resultSet, index));
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Read all entries(Database)");
            return developers;
        } catch (SQLException e) {
            log.error("Error in selecting records in SQL query");
        }
        return null;
    }
}