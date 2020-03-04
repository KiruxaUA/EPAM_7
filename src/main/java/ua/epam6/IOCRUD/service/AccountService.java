package ua.epam6.IOCRUD.service;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcAccountRepositoryImpl;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;

import java.util.List;

public class AccountService implements Serviceable {
    private static final Logger log = Logger.getLogger(AccountService.class);
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void create(Account model) throws Exception {
        log.debug("Executing account creation...");
        accountRepository.create(model);
    }

    public Account getById(Long ID) throws Exception {
        log.debug("Executing account getting by ID...");
        return accountRepository.getById(ID);
    }

    public void update(Account model) throws Exception {
        log.debug("Executing account updating...");
        accountRepository.update(model);
    }

    public void delete(Long ID) throws Exception {
        log.debug("Executing account deletion...");
        accountRepository.delete(ID);
    }

    public List<Account> getAll() throws Exception {
        log.debug("Executing accounts information...");
        return accountRepository.getAll();
    }

    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitAccountService(this);
    }
}