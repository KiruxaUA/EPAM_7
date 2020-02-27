package ua.epam6.IOCRUD.service.servicevisitors;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.service.AccountService;
import ua.epam6.IOCRUD.service.DeveloperService;
import ua.epam6.IOCRUD.service.SkillService;

public class VisitorDeletion extends ServiceVisitor {
    private static final Logger log = Logger.getLogger(AccountService.class);

    public VisitorDeletion(Long inputData) {
        super(inputData);
    }

    @Override
    public void visitSkillService(SkillService service) {
        if(inputData!=null && inputData instanceof Long) {
            try {
                service.delete((Long) inputData);
            }
            catch (Exception e) {
                log.error("Error occurred while deleting skill via service visitor");
            }
        }
    }

    @Override
    public void visitAccountService(AccountService service) {
        if(inputData!=null && inputData instanceof Long) {
            try {
                service.delete((Long) inputData);
            }
            catch (Exception e) {
                log.error("Error occurred while deleting account via service visitor");
            }
        }
    }

    @Override
    public void visitDeveloperService(DeveloperService service) {
        if(inputData!=null && inputData instanceof Long) {
            try {
                service.delete((Long) inputData);
            }
            catch (Exception e) {
                log.error("Error occurred while deleting developer via service visitor");
            }
        }
    }
}
