package ua.epam6.IOCRUD.controller;

import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.javaio.AccountRepositoryImpl;
import ua.epam6.IOCRUD.repository.javaio.DeveloperRepositoryImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeveloperController {
    private DeveloperRepositoryImpl developerRepository;
    private SkillRepository skillRepository;
    private AccountRepositoryImpl accountRepository;

    public DeveloperController(SkillRepository skillRepository, AccountRepositoryImpl accountRepository) {
        this.skillRepository = skillRepository;
        this.accountRepository = accountRepository;
        developerRepository = new DeveloperRepositoryImpl(skillRepository, accountRepository);
    }

    public String getAll() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Developer developer : developerRepository.getAll()) {
            stringBuilder.append(developer);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getById(long id) {
        Developer developer = developerRepository.getById(id);
        if (developer == null) {
            return "Developer not found";
        }
        else {
            return developer.toString();
        }
    }

    public String addNewDeveloper(String name, String accountData, Set<Long> skillsId) {
        Long id = developerRepository.getLastId() + 1;
        Set<Skill> skills = new HashSet<>();
        for (Long skillId : skillsId) {
            Skill skill = skillRepository.getById(skillId);
            if (skill != null) {
                skills.add(skill);
            }
        }
        Long accountId = accountRepository.getLastId() + 1;
        Account account = new Account(accountId, accountData, AccountStatus.ACTIVE);
        Developer developer = new Developer(id, name, skills, account);
        List<Developer> developers = developerRepository.getAll();
        if (developers.contains(developer)) {
            return "Developer already exists";
        }
        else {
            developerRepository.create(developer);
            return "Operation completed successfully";
        }
    }

    public String setAccount(long devId, long accId) {
        Developer developer = developerRepository.getById(devId);
        Account account = accountRepository.getById(accId);
        if (developer == null || account == null) {
            return "Operation failed";
        }
        else {
            developer.setAccount(account);
            developerRepository.create(developer);
            return "Operation completed successfully";
        }
    }

    public String setSkills(long devId, List<Long> skillIds) {
        Developer developer = developerRepository.getById(devId);
        if (developer == null) {
            return "Operation failed";
        }
        Set<Skill> skills = new HashSet<>();
        for (Long skillId : skillIds) {
            Skill skill = skillRepository.getById(skillId);
            if (skill != null) {
                skills.add(skill);
            }
        }
        developer.setSkills(skills);
        developerRepository.create(developer);
        return "Operation completed successfully";
    }

    public String getAllAccounts() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Account> accounts = accountRepository.getAll();
        for (Account account : accounts) {
            stringBuilder.append(account.getId());
            stringBuilder.append(": ");
            stringBuilder.append(account.getAccountStatus());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getAllSkills() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Skill> skills = skillRepository.getAll();
        for (Skill skill : skills) {
            stringBuilder.append(skill);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
