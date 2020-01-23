package ua.epam6.IOCRUD.repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.TestUtil;
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
    private static final Logger log = Logger.getLogger(JdbcSkillRepositoryImplTest.class);
    private static final String PATH_TO_INIT_SCRIPT = "./src/test/resources/db/init.sql";
    private static final String PATH_TO_POPULATE_SCRIPT = "./src/test/resources/db/populate.sql";
    private static Connection connection;
    private List<Developer> allDeveloper = new ArrayList<>();
    private static JdbcDeveloperRepositoryImpl testRepo;

    private Developer updatedDeveloper = new Developer(1L, "Denis", "Chung",
            Arrays.stream(new Skill[]{new Skill(4L, "Python")}).collect(Collectors.toSet()),
            new Account(1L, "Denis", AccountStatus.ACTIVE));

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testRepo = new JdbcDeveloperRepositoryImpl();
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
        Developer createdDeveloper = new Developer(4L, "Alexei", "Dmitrenko",
                Arrays.stream(new Skill[]{new Skill(2L, "C++")}).collect(Collectors.toSet()),
                new Account(4L, "Alexei", AccountStatus.ACTIVE));
        String SELECT_QUERY_CREATE = "SELECT MAX(d.Id), d.FirstName, d.LastName, s.Id, s.Name, " +
                "a.Id, a.Name, a.Status " +
                "FROM ioapplication.developers d " +
                "LEFT JOIN (" +
                "SELECT ds.developerId, s.Id, s.Name " +
                "FROM ioapplication.developerSkill ds " +
                "INNER JOIN ioapplication.skills s " +
                "ON ds.skillId = s.Id) s " +
                "ON d.id = s.developerId " +
                "LEFT JOIN ioapplication.accounts a " +
                "ON d.accountId = a.id " +
                "GROUP BY s.id, d.lastName, d.firstName, s.Name, a.Id, a.Name, a.Status;";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)){
            testRepo.create(createdDeveloper);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_CREATE);
            assertEquals(createdDeveloper, new JdbcDeveloperMapper().map(resultSet, 4L));
            log.debug("Created developer(test)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetById() {
        Developer readDeveloper = new Developer(2L, "William", "Shakespear",
                Arrays.stream(new Skill[]{new Skill(2L, "C++")}).collect(Collectors.toSet()),
                new Account(2L, "William", AccountStatus.DELETED));
        try {
            assertEquals(readDeveloper, testRepo.getById(2L));
            log.debug("Got developer by ID(test)");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkUpdating() {
        String SELECT_QUERY = "SELECT d.Id, d.FirstName, d.LastName, s.Id, s.Name, a.Id, a.Name, a.Status " +
                "FROM ioapplication.developers d " +
                "LEFT JOIN (" +
                "SELECT ds.developerId, s.Id, s.Name " +
                "FROM ioapplication.developerSkill ds " +
                "INNER JOIN ioapplication.skills s " +
                "ON ds.skillId = s.Id) s " +
                "ON d.id = s.developerId " +
                "LEFT JOIN ioapplication.accounts a " +
                "ON d.accountId = a.id " +
                "WHERE d.id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            testRepo.update(updatedDeveloper);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(updatedDeveloper, new JdbcDeveloperMapper().map(resultSet, 1L));
            log.debug("Updated developer(test)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkDeleting() {
        String SELECT_QUERY = "SELECT d.Id, d.FirstName, d.LastName, s.Id, s.Name, a.Id, a.Name, a.Status " +
                "FROM ioapplication.developers d " +
                "LEFT JOIN (" +
                "SELECT ds.developerId, s.Id, s.Name " +
                "FROM ioapplication.developerSkill ds " +
                "INNER JOIN ioapplication.skills s " +
                "ON ds.skillId = s.Id) s " +
                "ON d.id = s.developerId " +
                "LEFT JOIN ioapplication.accounts a " +
                "ON d.accountId = a.id " +
                "WHERE d.id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            testRepo.delete(4L);
            statement.setLong(1, 4);
            assertFalse(statement.executeQuery().next());
            log.debug("Deleted developer(test)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void shouldGetAll() {
        try {
            Collections.addAll(allDeveloper, new Developer(1L, "Joe", "Williams",
                            Arrays.stream(new Skill[]{new Skill(1L, "Java"),
                                    new Skill(2L, "C++")}).collect(Collectors.toSet()),
                                    new Account(1L, "Joe", AccountStatus.ACTIVE)),
                    new Developer(2L, "William", "Shakespear",
                            Arrays.stream(new Skill[]{new Skill(1L, "Java"),
                                    new Skill(3L, "C#")}).collect(Collectors.toSet()),
                                    new Account(2L, "William", AccountStatus.DELETED)),
                    new Developer(3L, "John", "Higgins",
                            Arrays.stream(new Skill[]{new Skill(1L, "Java"),
                                    new Skill(2L, "C++"),
                                    new Skill(3L, "C#")}).collect(Collectors.toSet()),
                                    new Account(3L, "John", AccountStatus.BANNED)));
            assertEquals(allDeveloper, testRepo.getAll());
            log.debug("Got all developers(test)");
        } catch (Exception e) {
            fail();
        }
    }
}
