package ua.epam6.IOCRUD.service.servicevisitors;

import ua.epam6.IOCRUD.service.AccountService;
import ua.epam6.IOCRUD.service.DeveloperService;
import ua.epam6.IOCRUD.service.SkillService;

public abstract class ServiceVisitor {
    protected Object inputData;
    protected Object resultData;

    protected ServiceVisitor(Object inputData) {
        this.inputData = inputData;
        resultData = new Object();
    }

    public void visitSkillService(SkillService service) {}
    public void visitAccountService(AccountService service) {}
    public void visitDeveloperService(DeveloperService service) {}

    public Object getResultData() {
        return resultData;
    }

    public void setInputData(Object inputData) {
        this.inputData = inputData;
    }
}