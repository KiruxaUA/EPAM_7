package ua.epam6.IOCRUD.controller;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.service.AccountService;
import ua.epam6.IOCRUD.service.DeveloperService;
import ua.epam6.IOCRUD.service.SkillService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeveloperController {
    private static final Logger log = Logger.getLogger(DeveloperController.class);
    private AccountService accountService = new AccountService();
    private SkillService skillService = new SkillService();
    private DeveloperService developerService = new DeveloperService();

    public String getAll() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        for (Developer developer : developerService.getAll()) {
            stringBuilder.append(developer);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getById(long id) throws Exception {
        Developer developer = developerService.getById(id);
        if (developer == null) {
            log.warn("Developer not found while getting by ID(MySQL): " + id);
            return "Developer not found";
        }
        else {
            return developer.toString();
        }
    }

    public String addNewDeveloper(String firstName, String lastName, String accountData, Set<Long> skillsId) throws Exception {
        Set<Skill> skills = new HashSet<>();
        for (Long skillId : skillsId) {
            Skill skill = skillService.getById(skillId);
            if (skill != null) {
                skills.add(skill);
            }
        }
        Account account = new Account(null, accountData, AccountStatus.ACTIVE);
        Developer developer = new Developer(null, firstName, lastName, skills, account);
        List<Developer> developers = developerService.getAll();
        if (developers.contains(developer)) {
            return "Developer already exists";
        }
        else {
            developerService.create(developer);
            return "Operation completed successfully";
        }
    }

    public String setAccount(long devId, long accId) throws Exception {
        Developer developer = developerService.getById(devId);
        Account account = accountService.getById(accId);
        if (developer == null || account == null) {
            log.error("Error occurred while setting account to developer");
            return "Operation failed";
        }
        else {
            developer.setAccount(account);
            developerService.create(developer);
            log.debug("Account was set successfully");
            return "Operation completed successfully";
        }
    }

    public String setSkills(long devId, List<Long> skillIds) throws Exception {
        Developer developer = developerService.getById(devId);
        if (developer == null) {
            log.error("Error occurred while setting skills to developer");
            return "Operation failed";
        }
        Set<Skill> skills = new HashSet<>();
        for (Long skillId : skillIds) {
            Skill skill = skillService.getById(skillId);
            if (skill != null) {
                skills.add(skill);
            }
        }
        developer.setSkills(skills);
        developerService.create(developer);
        log.debug("Skills were set successfully");
        return "Operation completed successfully";
    }

    public String getAllAccounts() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        List<Account> accounts = accountService.getAll();
        for (Account account : accounts) {
            stringBuilder.append(account.getId());
            stringBuilder.append(": ");
            stringBuilder.append(account.getAccountStatus());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getAllSkills() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        List<Skill> skills = skillService.getAll();
        for (Skill skill : skills) {
            stringBuilder.append(skill);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}