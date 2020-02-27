package ua.epam6.IOCRUD.service.servicevisitors;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.service.AccountService;
import ua.epam6.IOCRUD.service.DeveloperService;
import ua.epam6.IOCRUD.service.SkillService;

public class VisitorGettingAll<T> extends ServiceVisitor {
    private static final Logger log = Logger.getLogger(AccountService.class);

    public VisitorGettingAll() {
        super(null);
    }

    @Override
    public void visitSkillService(SkillService service) {
        try {
            resultData = service.getAll();
        }
        catch (Exception e) {
            log.error("Error occurred while getting all skills via service visitor");
        }
    }

    @Override
    public void visitAccountService(AccountService service) {
        try {
            resultData = service.getAll();
        }
        catch (Exception e) {
            log.error("Error occurred while getting all accounts via service visitor");
        }
    }

    @Override
    public void visitDeveloperService(DeveloperService service) {
        try {
            resultData = service.getAll();
        }
        catch (Exception e) {
            log.error("Error occurred while getting all developers via service visitor");
        }
    }
}