package ua.epam6.IOCRUD.repository.jdbc;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.exceptions.NoSuchEntryException;
import ua.epam6.IOCRUD.exceptions.RepoStorageException;
import ua.epam6.IOCRUD.mappers.JdbcAccountMapper;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountRepositoryImpl implements AccountRepository {
    private static final Logger log = Logger.getLogger(JdbcAccountRepositoryImpl.class);
    private final String INSERT_QUERY = "INSERT INTO accounts(name, status) VALUES (?, ?);";
    private final String SELECT_QUERY = "SELECT * FROM accounts WHERE id = ?;";
    private final String UPDATE_QUERY = "UPDATE accounts SET Name = ?, Status = ? WHERE Id = ?;";
    private final String DELETE_QUERY = "DELETE FROM accounts WHERE Id = ?;";
    private final String SELECT_ALL_QUERY = "SELECT * FROM accounts;";
    private Connection connection;

    public JdbcAccountRepositoryImpl() {
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to database", e);
        }
    }

    @Override
    public Account create(Account accountModel) throws RepoStorageException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            statement.setString(1, accountModel.getName());
            statement.setString(2, accountModel.getAccountStatus().toString());
            statement.execute();
            log.debug("Created entry(Database): " + accountModel);
            return accountModel;
        } catch (SQLException e) {
            log.error("Wrong SQL query to database in creation", e);
            throw new RepoStorageException("Wrong SQL query to database in creation");
        }
    }

    @Override
    public Account getById(Long ID) throws RepoStorageException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.absolute(2)) {
                log.warn("Reading from database is failed");
            }
            resultSet.first();
            Account account = new JdbcAccountMapper().map(resultSet, ID);
            log.debug("Read entry(Database) with ID: "+ ID);
            return account;
        } catch (SQLException e) {
            log.error("Wrong SQL query to database in reading", e);
            throw new RepoStorageException("Wrong SQL query to database in reading");
        }
    }

    @Override
    public Account update(Account updatedModel) throws NoSuchEntryException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setString(1, updatedModel.getName());
            statement.setString(2, updatedModel.getAccountStatus().toString());
            statement.setLong(3, updatedModel.getId());
            if(statement.executeUpdate() < 1) {
                log.warn("No such entry: " + updatedModel);
                throw new NoSuchEntryException("Entry is not found");
            }
            log.debug("Updated entry(Database): " + updatedModel);
        }
        catch (SQLException e) {
            log.error("Error in updating record in SQL query");
        }
        return updatedModel;
    }

    @Override
    public void delete(Long deleteEntry) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, deleteEntry);
            if (statement.executeUpdate() < 1) {
                log.warn("No such entry with ID: " + deleteEntry);
            }
        }
        catch (SQLException e) {
            log.error("Error in deleting record in SQL query", e);
            System.out.println("Error in deleting record in SQL query");
        }
    }

    @Override
    public List<Account> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Account> accounts = new ArrayList<>();
            JdbcAccountMapper mapper = new JdbcAccountMapper();
            while (resultSet.next()) {
                accounts.add(mapper.map(resultSet, resultSet.getLong(1)));
            }
            log.debug("Read all entries(Database)");
            return accounts;
        }
        catch (SQLException e) {
            log.error("Error in selecting records in database query");
        }
        return null;
    }
}