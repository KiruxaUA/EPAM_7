package ua.epam6.IOCRUD.repository.jdbc;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.mappers.JdbcSkillMapper;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    private static final Logger log = Logger.getLogger(JdbcSkillRepositoryImpl.class);
    private final String INSERT_QUERY = "INSERT INTO skills(name) VALUES (?);";
    private final String SELECT_QUERY = "SELECT * FROM skills WHERE Id = ?;";
    private final String UPDATE_QUERY = "UPDATE skills SET name = ? WHERE Id = ?;";
    private final String DELETE_QUERY = "DELETE FROM skills WHERE Id = ?;";
    private final String SELECT_ALL_QUERY = "SELECT * FROM skills;";
    private Connection connection;

    public JdbcSkillRepositoryImpl() {
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to MySQL", e);
        }
    }

    @Override
    public Skill create(Skill model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, model.getName());
            statement.execute();
            log.debug("Created entry(MySQL): " + model);
        }
        catch (SQLException e) {
            log.error("Error in creation of SQL query");
            System.out.println("Error in creation of SQL query");
            model = null;
        }
        return model;
    }

    @Override
    public Skill getById(Long Id) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            statement.setLong(1, Id);
            ResultSet resultSet = statement.executeQuery();
            Skill skill = new JdbcSkillMapper().map(resultSet, Id);
            log.debug("Read entry(MySQL) with ID: " + Id);
            return skill;
        }
        catch (SQLException e) {
            log.error("Error in fetching record by ID in SQL query", e);
            System.out.println("Error in fetching record by ID in SQL query");
            return null;
        }
    }

    @Override
    public Skill update(Skill updatedModel) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, updatedModel.getName());
            statement.setLong(2, updatedModel.getId());
            if(statement.executeUpdate() < 1) {
                log.warn("No such entry: " + updatedModel);
                updatedModel = null;
            }
            log.debug("Update entry(MySQL): " + updatedModel);
        }
        catch (SQLException e) {
            log.error("Error in updating record in SQL query", e);
        }
        return updatedModel;
    }

    @Override
    public void delete(Long deleteEntry) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, deleteEntry);
            if (statement.executeUpdate() < 1) {
                log.warn("No such entry with ID: " + deleteEntry);
            }
            log.debug("Delete entry(MySQL) with ID: " + deleteEntry);
        }
        catch (SQLException e) {
            log.error("Error in deleting record in SQL query", e);
        }
    }

    @Override
    public List<Skill> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Skill> skills = new ArrayList<>();
            JdbcSkillMapper mapper = new JdbcSkillMapper();
            while (resultSet.next()) {
                skills.add(mapper.map(resultSet, resultSet.getLong(1)));
            }
            log.debug("Read all entries(MySQL)");
            return skills;
        }
        catch (SQLException e) {
            log.error("Error in selecting records in SQL query", e);
            System.out.println("Error in selecting records in SQL query");
        }
        return null;
    }
}