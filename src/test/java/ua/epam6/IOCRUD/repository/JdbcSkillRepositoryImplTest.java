package ua.epam6.IOCRUD.repository;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.epam6.IOCRUD.testUtil.TestUtil;
import ua.epam6.IOCRUD.exceptions.NoSuchEntryException;
import ua.epam6.IOCRUD.exceptions.RepoStorageException;
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

public class JdbcSkillRepositoryImplTest {
    private static final Logger log = Logger.getLogger(JdbcSkillRepositoryImplTest.class);
    private static final String PATH_TO_INIT_SCRIPT = "./src/test/resources/db/initDB.sql";
    private static final String PATH_TO_POPULATE_SCRIPT = "./src/test/resources/db/populateDB.sql";
    private static JdbcSkillRepositoryImpl testedRepo;
    private static Connection connection;
    private String SELECT_QUERY_CREATE = "SELECT * FROM skills GROUP BY id HAVING Max(id);";
    private String SELECT_QUERY = "SELECT * FROM skills WHERE id = ?;";
    private Skill createSkill = new Skill(5L, "OCaml");
    private Skill getSkill = new Skill(2L, "C++");
    private Skill updateSkill = new Skill(1L, "Kotlin");
    private List<Skill> allSkills = new ArrayList<>();

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try {
            connection = JDBCConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testedRepo = new JdbcSkillRepositoryImpl();
    }

    @AfterClass
    public static void backProperty(){
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
            testedRepo.create(createSkill);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_CREATE);
            assertEquals(createSkill, new JdbcSkillMapper().map(resultSet, 5L));
            log.debug("Create(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkGetById() {
        try {
            assertEquals(getSkill, testedRepo.getById(2L));
            log.debug("Get(TEST)");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkUpdating() throws NoSuchEntryException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            testedRepo.update(updateSkill);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(updateSkill, new JdbcSkillMapper().map(resultSet, 1L));
            log.debug("Update(TEST)");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void checkDelete() throws RepoStorageException, NoSuchEntryException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
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
            Collections.addAll(allSkills, new Skill(1L, "Java"),
                    new Skill(2L, "C++"),
                    new Skill(3L, "C#"),
                    new Skill(4L, "Python"));
            assertEquals(allSkills, testedRepo.getAll());
            log.debug("Got all accounts(TEST)");
        } catch (Exception e) {
            fail();
        }
    }
}