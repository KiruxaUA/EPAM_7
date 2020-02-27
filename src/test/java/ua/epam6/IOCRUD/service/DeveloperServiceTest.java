package ua.epam6.IOCRUD.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcDeveloperRepositoryImpl;
import ua.epam6.IOCRUD.testUtil.TestUtil;

import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceTest {

    @InjectMocks
    private static DeveloperService testDeveloperService;

    @Mock
    private DeveloperRepository currentRepo;
    private Developer createDeveloper = new Developer(5L, "Mike", "Wazowski", new HashSet<>(), new Account(2L));
    private Developer updateDeveloper = new Developer(5L, "Alex", "Fridrich", new HashSet<>(), new Account(1L));

    @BeforeClass
    public static void connect(){
        TestUtil.toTestMode();
        try {
            testDeveloperService = new DeveloperService(new JdbcDeveloperRepositoryImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void rollbackProperties(){
        TestUtil.toWorkMode();
    }

    @Test
    public void checkCreation() {
        try {
            testDeveloperService.create(createDeveloper);
            verify(currentRepo, times(1)).create(createDeveloper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetById() {
        try {
            testDeveloperService.getById(1L);
            verify(currentRepo, times(1)).getById(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkUpdating() {
        try {
            testDeveloperService.update(updateDeveloper);
            verify(currentRepo, times(1)).update(updateDeveloper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDeleting() {
        try {
            testDeveloperService.delete(2L);
            verify(currentRepo, times(1)).delete(2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetAll() {
        try {
            testDeveloperService.getAll();
            verify(currentRepo, times(1)).getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}