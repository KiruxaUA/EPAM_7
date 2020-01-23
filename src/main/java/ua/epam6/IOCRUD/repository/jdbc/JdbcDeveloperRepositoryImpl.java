package ua.epam6.IOCRUD.repository.jdbc;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.mappers.JdbcDeveloperMapper;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private static final Logger log = Logger.getLogger(JdbcDeveloperRepositoryImpl.class);
    private Connection connection;

    public JdbcDeveloperRepositoryImpl() {
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to MySQL", e);
        }
    }

    public Developer create(Developer developerModel) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {
            connection.setAutoCommit(false);
            String INSERT_QUERY = "INSERT INTO developers(FirstName, LastName, AccountId) " +
                    "VALUES ('" + developerModel.getFirstName()+"', '" + developerModel.getLastName()+"', '" +
                    developerModel.getAccount().getId()+"');";
            statement.execute(INSERT_QUERY);
            ResultSet resultSet = statement.executeQuery("SELECT MAX(Id) FROM developers;");
            resultSet.first();
            long developerID = resultSet.getLong(1);
            for (Skill skill : developerModel.getSkills()) {
                statement.addBatch("INSERT INTO developerskill(developerid, skillid) " +
                        "VALUES ('" + developerID + "', '" + skill.getId() + "');");
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Create entry(MySQL): " + developerID);
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Error occurred while creating developer", e);
        }
        return developerModel;
    }

    public Developer getById(Long ID) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {
            connection.setAutoCommit(false);
            String SELECT_QUERY = "SELECT d.id, d.firstname, d.lastname, s.id, s.name, a.id, a.name, a.status " +
                    "FROM developers d " +
                    "LEFT JOIN (" +
                    "SELECT ds.developerid, s.id, s.name " +
                    "FROM developerskill ds " +
                    "INNER JOIN skills s " +
                    "ON ds.skillid = s.id) s " +
                    "ON d.id = s.developerid " +
                    "LEFT JOIN accounts a " +
                    "ON d.accountid = a.id " +
                    "WHERE d.id = '" + ID + "';";
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            connection.commit();
            connection.setAutoCommit(true);
            Developer developer = new JdbcDeveloperMapper().map(resultSet, ID);
            log.debug("Read entry(MySQL) with ID: " + ID);
            return developer;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Error occurred while getting developer by ID", e);
        }
        return null;
    }

    public Developer update(Developer updatedModel) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            String UPDATE_QUERY = "UPDATE developers " +
                    "SET firstname = '" + updatedModel.getFirstName() + "', " +
                    "lastname = '" + updatedModel.getLastName() + "', " +
                    "accountid = '" + updatedModel.getAccount().getId() + "' " +
                    "WHERE id = '" + updatedModel.getId() + "';";
            if(statement.executeUpdate(UPDATE_QUERY) < 1) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                log.warn("No such entry: " + updatedModel);
            }
            statement.execute("DELETE FROM developerskill WHERE developerid = '" + updatedModel.getId() + "';");
            for (Skill skill : updatedModel.getSkills()) {
                statement.addBatch("INSERT INTO developerskill(developerid, skillid) " +
                        "VALUES ('" + updatedModel.getId() + "', '" + skill.getId() + "');");
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Update entry(DB): " + updatedModel);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Wrong SQL query to DB in updating", e);
        }
        return updatedModel;
    }

    public void delete(Long deletedEntry) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            connection.setAutoCommit(false);
            statement.execute("DELETE FROM developerskill WHERE developerid = '" + deletedEntry + "';");
            String deleteQuery = "DELETE FROM developers WHERE id = '" + deletedEntry + "';";
            if (statement.executeUpdate(deleteQuery) < 1) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                log.warn("No such entry with ID: " + deletedEntry);
            }
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Delete entry(MySQL) with ID: " + deletedEntry);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Error occurred while deleting developer", e);
        }
    }

    public List<Developer> getAll() {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            String SELECT_QUERY = "SELECT d.id, d.firstname, d.lastname, s.id, s.name, a.id, a.name, a.status " +
                    "FROM developers d " +
                    "LEFT JOIN (" +
                    "SELECT ds.developerid, s.id, s.name " +
                    "FROM developerskill ds " +
                    "INNER JOIN skills s " +
                    "ON ds.skillid = s.id) s " +
                    "ON d.id = s.developerid " +
                    "LEFT JOIN accounts a " +
                    "ON d.accountid = a.id;";
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            ArrayList<Developer> developers = new ArrayList<>();
            JdbcDeveloperMapper mapper = new JdbcDeveloperMapper();
            long index = -1;
            while (resultSet.next()){
                if(index!=resultSet.getLong(1)){
                    index = resultSet.getLong(1);
                    developers.add(mapper.map(resultSet, index));
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Read all entries(MySQL)");
            return developers;
        } catch (SQLException e) {
            log.error("Error occurred while getting developers", e);
        }
        return null;
    }
}
