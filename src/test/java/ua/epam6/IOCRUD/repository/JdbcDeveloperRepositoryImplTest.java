package ua.epam6.IOCRUD.repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.epam6.IOCRUD.testUtil.TestUtil;
import ua.epam6.IOCRUD.mappers.JdbcDeveloperMapper;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.jdbc.JdbcDeveloperRepositoryImpl;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class JdbcDeveloperRepositoryImplTest {
    private static final Logger log = Logger.getLogger(JdbcDeveloperRepositoryImplTest.class);
    private static final String PATH_TO_INIT_SCRIPT = "./src/test/resources/db/initDB.sql";
    private static final String PATH_TO_POPULATE_SCRIPT = "./src/test/resources/db/populateDB.sql";
    private static JdbcDeveloperRepositoryImpl testedRepo;
    private static Connection connection;
    private String SELECT_QUERY_CREATE = "SELECT Max(d.id), d.first_name, d.last_name, s.id, s.name, a.id, a.name, a.status " +
            "FROM developers d " +
            "LEFT JOIN (" +
            "SELECT ds.developer_id, s.id, s.name " +
            "FROM developer_skill ds " +
            "INNER JOIN skills s " +
            "ON ds.skill_id = s.id) s " +
            "ON d.id = s.developer_id " +
            "LEFT JOIN accounts a " +
            "ON d.account_id = a.id " +
            "GROUP BY s.id, d.last_name, d.first_name, s.name, a.id, a.name, a.status;";
    private String SELECT_QUERY = "SELECT d.id, d.first_name, d.last_name, s.id, s.name, a.id, a.name, a.status " +
            "FROM developers d " +
            "LEFT JOIN (" +
            "SELECT ds.developer_id, s.id, s.name " +
            "FROM developer_skill ds " +
            "INNER JOIN skills s " +
            "ON ds.skill_id = s.id) s " +
            "ON d.id = s.developer_id " +
            "LEFT JOIN accounts a " +
            "ON d.account_id = a.id " +
            "WHERE d.id = ?;";
    private Developer createDeveloper = new Developer(5L, "Max", "Kowalski",
            Arrays.stream(new Skill[]{new Skill(2L, "C++")}).collect(Collectors.toSet()),
            new Account(3L, "John", AccountStatus.BANNED));
    private Developer getDeveloper = new Developer(2L, "William", "Shakespear",
            Arrays.stream(new Skill[]{new Skill(2L, "C++")}).collect(Collectors.toSet()),
            new Account(2L, "William", AccountStatus.DELETED));
    private Developer updateDeveloper = new Developer(1L, "Denis", "Ritchie",
            Arrays.stream(new Skill[]{new Skill(3L, "C#")}).collect(Collectors.toSet()),
            new Account(1L, "Joe", AccountStatus.ACTIVE));
    private List<Developer> allDevelopers = new ArrayList<>();

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try{
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testedRepo = new JdbcDeveloperRepositoryImpl();
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

    @Test
    public void checkCreation() {
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)){
            testedRepo.create(createDeveloper);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_CREATE);
            assertEquals(createDeveloper, new JdbcDeveloperMapper().map(resultSet, 5L));
            log.debug("Create(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetById() {
        try {
            assertEquals(getDeveloper, testedRepo.getById(2L));
            log.debug("Get(TEST)");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkUpdating() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            testedRepo.update(updateDeveloper);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(updateDeveloper, new JdbcDeveloperMapper().map(resultSet, 1L));
            log.debug("Update(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkDeleting() {
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
            Collections.addAll(allDevelopers, new Developer(1L, "Joe", "Williams",
                                    Arrays.stream(new Skill[]{new Skill(3L, "C#")}).collect(Collectors.toSet()),
                                    new Account(1L, "Joe", AccountStatus.ACTIVE)),
                            new Developer(2L, "William", "Shakespear",
                                    Arrays.stream(new Skill[]{new Skill(2L, "C++")}).collect(Collectors.toSet()),
                                    new Account(2L, "William", AccountStatus.DELETED)),
                            new Developer(3L, "John", "Higgins",
                                    Arrays.stream(new Skill[]{new Skill(1L, "Java"),
                                    new Skill(2L, "C++")}).collect(Collectors.toSet()),
                                    new Account(3L, "John", AccountStatus.BANNED)),
                            new Developer(4L, "James", "Holden",
                                    Arrays.stream(new Skill[]{new Skill(4L, "Python")}).collect(Collectors.toSet()),
                                    new Account(4L, "James", AccountStatus.ACTIVE)));
            assertEquals(allDevelopers, testedRepo.getAll());
            log.debug("Got all developers(TEST)");
        } catch (Exception e) {
            fail();
        }
    }
}