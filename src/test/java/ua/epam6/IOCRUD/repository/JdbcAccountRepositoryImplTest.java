package ua.epam6.IOCRUD.repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.TestUtil;
import ua.epam6.IOCRUD.mappers.JdbcAccountMapper;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.repository.jdbc.JdbcAccountRepositoryImpl;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

class JdbcAccountRepositoryImplTest {
    private static final Logger log = Logger.getLogger(JdbcAccountRepositoryImplTest.class);
    private static final String PATH_TO_INIT_SCRIPT = "./src/test/resources/db/init.sql";
    private static final String PATH_TO_POPULATE_SCRIPT = "./src/test/resources/db/populate.sql";
    private static Connection connection;
    private static JdbcAccountRepositoryImpl testRepo;

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try{
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testRepo = new JdbcAccountRepositoryImpl();
    }

    @Before
    public void setupProperties() {
        try(FileReader frInit = new FileReader(PATH_TO_INIT_SCRIPT);
            FileReader frPop = new FileReader(PATH_TO_POPULATE_SCRIPT)) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(frInit);
            scriptRunner.runScript(frPop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void rollbackProperties(){
        TestUtil.toWorkMode();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkCreation() {
        Account createdAccount = new Account(4L, "Alexei", AccountStatus.ACTIVE);
        String SELECT_QUERY_CREATE = "SELECT * FROM ioapplication.accounts GROUP BY Id HAVING MAX(Id);";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            testRepo.create(createdAccount);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_CREATE);
            assertEquals(createdAccount, new JdbcAccountMapper().map(resultSet, 4L));
            log.debug("Created account(test)");
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetById() {
        Account readAccount = new Account(2L, "William", AccountStatus.DELETED);
        try {
            assertEquals(readAccount, testRepo.getById(2L));
            log.debug("Got account by ID(test)");
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkUpdating() {
        String SELECT_QUERY = "SELECT * FROM ioapplication.accounts WHERE Id = ?;";
        Account updatedAccount = new Account(1L, "Barry", AccountStatus.BANNED);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            testRepo.update(updatedAccount);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(updatedAccount, new JdbcAccountMapper().map(resultSet, 1L));
            log.debug("Updated account(test)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkDeleting() {
        String SELECT_QUERY = "SELECT * FROM ioapplication.accounts WHERE Id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            testRepo.delete(4L);
            statement.setLong(1, 4);
            assertFalse(statement.executeQuery().next());
            log.debug("Deleted account(test)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetAll() {
        List<Account> allAccount = new ArrayList<>();
        try {
            Collections.addAll(allAccount, new Account(1L, "Joe", AccountStatus.ACTIVE),
                    new Account(2L, "William", AccountStatus.DELETED),
                    new Account(3L, "John", AccountStatus.BANNED));
            assertEquals(allAccount, testRepo.getAll());
            log.debug("Got all accounts(test)");
        } catch (Exception e) {
            fail();
        }
    }
}