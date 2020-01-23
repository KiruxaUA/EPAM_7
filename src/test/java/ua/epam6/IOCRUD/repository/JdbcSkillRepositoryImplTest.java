package ua.epam6.IOCRUD.repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.TestUtil;
import ua.epam6.IOCRUD.mappers.JdbcSkillMapper;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.jdbc.JdbcSkillRepositoryImpl;
import ua.epam6.IOCRUD.utils.JDBCConnectionPool;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

class JdbcSkillRepositoryImplTest {
    private static final Logger log = Logger.getLogger(JdbcSkillRepositoryImplTest.class);
    private static final String PATH_TO_INIT_SCRIPT = "./src/test/resources/db/init.sql";
    private static final String PATH_TO_POPULATE_SCRIPT = "./src/test/resources/db/populate.sql";
    private static Connection connection;
    private List<Skill> allSkill = new ArrayList<>();
    private static JdbcSkillRepositoryImpl testRepo;

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testRepo = new JdbcSkillRepositoryImpl();
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
        Skill createdSkill = new Skill(5L, "OCaml");
        String SELECT_QUERY_CREATE = "SELECT * FROM ioapplication.skills GROUP BY Id HAVING MAX(Id);";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            testRepo.create(createdSkill);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_CREATE);
            assertEquals(createdSkill, new JdbcSkillMapper().map(resultSet, 5L));
            log.debug("Created skill(test)");
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetById() {
        Skill readSkill = new Skill(1L, "Java");
        try {
            assertEquals(readSkill, testRepo.getById(1L));
            log.debug("Got account by ID(test)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkUpdating() {
        String SELECT_QUERY = "SELECT * FROM ioapplication.skills WHERE Id = ?;";
        Skill updatedSkill = new Skill(1L, "Rust");
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            testRepo.update(updatedSkill);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(updatedSkill, new JdbcSkillMapper().map(resultSet, 1L));
            log.debug("Updated account(test)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkDeleting() {
        String SELECT_QUERY = "SELECT * FROM ioapplication.skills WHERE Id = ?;";
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
        List<Skill> allAccount = new ArrayList<>();
        try {
            Collections.addAll(allAccount, new Skill(1L, "Java"),
                    new Skill(2L, "C++"),
                    new Skill(3L, "C#"),
                    new Skill(4L, "Python"));
            assertEquals(allAccount, testRepo.getAll());
            log.debug("Got all accounts(test)");
        } catch (Exception e) {
            fail();
        }
    }
}
