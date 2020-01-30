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
    private final String INSERT_QUERY = "INSERT INTO 1?(2?) values (3?);";
    private final String SELECT_QUERY_SIMPLE = "select 1? from 2?;";
    private final String SELECT_QUERY_COMPLEX = "select d.id, d.first_name, d.last_name, s.id, s.name, a.id, a.name, a.status " +
            "from developers d " +
            "left join (" +
            "select ds.developer_id, s.id, s.name " +
            "from developer_skill ds " +
            "inner join skills s " +
            "on ds.skill_id = s.id) s " +
            "on d.id = s.developer_id " +
            "left join accounts a " +
            "on d.account_id = a.id " +
            "1?;";
    private final String UPDATE_QUERY = "update 1? set 2? where 3?;";
    private final String DELETE_QUERY = "delete from 1? where 2?;";

    private Connection connection;

    public JdbcDeveloperRepositoryImpl() {
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to MySQL", e);
        }
    }

    @Override
    public Developer create(Developer developerModel) {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)){
            connection.setAutoCommit(false);
            statement.execute(INSERT_QUERY.replace("1?", "Developers")
                    .replace("2?", "First_name, Last_name, Account_id")
                    .replace("3?", "'" + developerModel.getFirstName() + "', '" +
                            developerModel.getLastName() + "', '" + developerModel.getAccount().getId() + "'"));
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_SIMPLE.replace("1?", "Max(Id)")
                    .replace("2?", "Developers"));
            resultSet.first();
            long developerId = resultSet.getLong(1);
            for (Skill skill : developerModel.getSkills()) {
                statement.addBatch(INSERT_QUERY.replace("1?", "Developer_skill")
                        .replace("2?", "Developer_id, Skill_id")
                        .replace("3?", "'" + developerId + "', '" + skill.getId() + "'"));
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Created entry(MySQL): " + developerModel);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Wrong SQL query to MySQL in creation", e);
        }
        return developerModel;
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
            log.debug("Read entry(MySQL) with ID: "+ ID);
            return developer;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Rollback denied(MySQL)");
            }
            log.error("Wrong SQL query to MySQL in reading", e);
        }
        return null;
    }

    @Override
    public Developer update(Developer updatedModel) {
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
                    log.error("Rollback denied(MySQL)");
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
            log.debug("Updated entry(MySQL): " + updatedModel);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Rollback denied(MySQL)");
            }
            log.error("Wrong SQL query to DB in updating", e);
        }
        return updatedModel;
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
                    log.error("Rollback denied(MySQL)");
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
                log.error("Rollback denied(MySQL)");
            }
            log.error("Wrong SQL query to MySQL in deleting", e);
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
            log.error("Error in selecting records in SQL query");
        }
        return null;
    }
}