package ua.epam6.IOCRUD.service.servicevisitors;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.service.AccountService;
import ua.epam6.IOCRUD.service.DeveloperService;
import ua.epam6.IOCRUD.service.SkillService;

public class VisitorUpdating<T> extends ServiceVisitor {
    private static final Logger log = Logger.getLogger(AccountService.class);

    public VisitorUpdating(T inputData) {
        super(inputData);
    }

    @Override
    public void visitSkillService(SkillService service) {
        if(inputData!=null && inputData instanceof Skill) {
            try {
                service.update((Skill) inputData);
            }
            catch (Exception e) {
                log.error("Error occurred while updating skill via service visitor");
            }
        }
    }

    @Override
    public void visitAccountService(AccountService service) {
        if(inputData!=null && inputData instanceof Account) {
            try {
                service.update((Account) inputData);
            }
            catch (Exception e) {
                log.error("Error occurred while updating account via service visitor");
            }
        }
    }

    @Override
    public void visitDeveloperService(DeveloperService service) {
        if(inputData!=null && inputData instanceof Developer) {
            try {
                service.update((Developer) inputData);
            }
            catch (Exception e) {
                log.error("Error occurred while updating developer via service visitor");
            }
        }
    }
}
