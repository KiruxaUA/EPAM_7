package ua.epam6.IOCRUD.repository.jdbc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.epam6.IOCRUD.annotations.Timed;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.exceptions.NoSuchEntryException;
import ua.epam6.IOCRUD.exceptions.RepoStorageException;
import ua.epam6.IOCRUD.mappers.JdbcSkillMapper;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("skillRepository")
public class JdbcSkillRepositoryImpl implements SkillRepository {
    private static final Logger log = Logger.getLogger(JdbcSkillRepositoryImpl.class);
    private final String INSERT_QUERY = "INSERT INTO skills(name) VALUES (?);";
    private final String SELECT_QUERY = "SELECT * FROM skills WHERE id = ?;";
    private final String UPDATE_QUERY = "UPDATE skills SET name = ? WHERE id = ?;";
    private final String DELETE_QUERY = "DELETE FROM skills WHERE id = ?;";
    private final String SELECT_ALL_QUERY = "SELECT * FROM skills;";
    private Connection connection;

    public JdbcSkillRepositoryImpl() {
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to database", e);
        }
    }

    @Override
    @Timed
    public Skill create(Skill model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, model.getName());
            statement.execute();
            log.debug("Created entry(Database): " + model);
        }
        catch (SQLException e) {
            log.error("Error in creation of SQL query");
            System.out.println("Error in creation of SQL query");
            model = null;
        }
        return model;
    }

    @Override
    @Timed
    public Skill getById(Long Id) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, Id);
            ResultSet resultSet = statement.executeQuery();
            Skill skill = new JdbcSkillMapper().map(resultSet, Id);
            log.debug("Read entry(Database) with ID: " + Id);
            return skill;
        }
        catch (SQLException e) {
            log.error("Error in fetching record by ID in SQL query", e);
            System.out.println("Error in fetching record by ID in SQL query");
            return null;
        }
    }

    @Override
    @Timed
    public Skill update(Skill updatedModel) throws NoSuchEntryException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setString(1, updatedModel.getName());
            statement.setLong(2, updatedModel.getId());
            if(statement.executeUpdate() < 1) {
                log.warn("No such entry: " + updatedModel);
                throw new NoSuchEntryException("Updating in database is failed");
            }
            log.debug("Update entry(Database): " + updatedModel);
        }
        catch (SQLException e) {
            log.error("Error in updating record in SQL query", e);
        }
        return updatedModel;
    }

    @Override
    @Timed
    public void delete(Long deleteEntry) throws NoSuchEntryException, RepoStorageException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, deleteEntry);
            if (statement.executeUpdate() < 1) {
                log.warn("No such entry with ID: " + deleteEntry);
                throw new NoSuchEntryException("Deleting in DB is failed");
            }
            log.debug("Delete entry(Database) with ID: " + deleteEntry);
        }
        catch (SQLException e) {
            log.error("Error in deleting record in SQL query", e);
            throw new RepoStorageException("Wrong SQL query to DB in deleting");
        }
    }

    @Override
    @Timed
    public List<Skill> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Skill> skills = new ArrayList<>();
            JdbcSkillMapper mapper = new JdbcSkillMapper();
            while (resultSet.next()) {
                skills.add(mapper.map(resultSet, resultSet.getLong(1)));
            }
            log.debug("Read all entries(Database)");
            return skills;
        }
        catch (SQLException e) {
            log.error("Error in selecting records in SQL query", e);
            System.out.println("Error in selecting records in SQL query");
        }
        return null;
    }
}