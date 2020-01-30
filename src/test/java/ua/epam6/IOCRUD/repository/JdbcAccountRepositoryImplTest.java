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

public class JdbcAccountRepositoryImplTest {
    private static final Logger log = Logger.getLogger(JdbcAccountRepositoryImplTest.class);
    private static final String PATH_TO_INIT_SCRIPT = "./src/test/resources/db/initDB.sql";
    private static final String PATH_TO_POPULATE_SCRIPT = "./src/test/resources/db/populateDB.sql";
    private static JdbcAccountRepositoryImpl testedRepo;
    private static Connection connection;
    private String SELECT_QUERY_CREATE = "SELECT * FROM ioapplication.Accounts GROUP BY Id HAVING Max(Id);";
    private String SELECT_QUERY = "SELECT * FROM ioapplication.Accounts WHERE Id = ?;";
    private Account createAccount = new Account(4L, "Alexei", AccountStatus.ACTIVE);
    private Account getAccount = new Account(2L, "William", AccountStatus.DELETED);
    private Account updateAccount = new Account(1L, "Max", AccountStatus.BANNED);
    private List<Account> allAccounts = new ArrayList<>();

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try{
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testedRepo = new JdbcAccountRepositoryImpl();
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
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {
            testedRepo.create(createAccount);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_CREATE);
            assertEquals(createAccount, new JdbcAccountMapper().map(resultSet, 5L));
            log.debug("Create(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetById() {
        try {
            assertEquals(getAccount, testedRepo.getById(2L));
            log.debug("Get(TEST)");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkUpdate() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            testedRepo.update(updateAccount);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(updateAccount, new JdbcAccountMapper().map(resultSet, 1L));
            log.debug("Update(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkDelete() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            testedRepo.delete(4L);
            statement.setLong(1, 4);
            assertFalse(statement.executeQuery().next());
            log.debug("Delete(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetAll() {
        try {
            Collections.addAll(allAccounts, new Account(1L, "Joe", AccountStatus.ACTIVE),
                    new Account(2L, "William", AccountStatus.DELETED),
                    new Account(3L, "John", AccountStatus.BANNED));
            assertEquals(allAccounts, testedRepo.getAll());
            log.debug("Got all accounts(TEST)");
        } catch (Exception e) {
            fail();
        }
    }
}