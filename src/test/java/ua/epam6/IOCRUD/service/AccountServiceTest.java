package ua.epam6.IOCRUD.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.testUtil.TestUtil;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    private static AccountService testAccountService;
    @Mock
    private AccountRepository currentRepo;
    private Account createAccount = new Account(5L, "Jimmy", AccountStatus.ACTIVE);
    private Account updateAccount = new Account(5L, "Pol", AccountStatus.BANNED);

    @BeforeClass
    public static void connect() {
        TestUtil.toTestMode();
        try {
            testAccountService = new AccountService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void rollbackProperties() {
        TestUtil.toWorkMode();
    }

    @Test
    public void checkCreation() {
        try {
            testAccountService.create(createAccount);
            verify(currentRepo, times(1)).create(createAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetById() {
        try {
            testAccountService.getById(1L);
            verify(currentRepo, times(1)).getById(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkUpdating() {
        try {
            testAccountService.update(updateAccount);
            verify(currentRepo, times(1)).update(updateAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDeleting() {
        try {
            testAccountService.delete(1L);
            verify(currentRepo, times(1)).delete(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetAll() {
        try {
            testAccountService.getAll();
            verify(currentRepo, times(1)).getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
