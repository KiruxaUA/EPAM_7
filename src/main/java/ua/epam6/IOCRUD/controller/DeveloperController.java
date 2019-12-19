package ua.epam6.IOCRUD.controller;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.javaio.DeveloperRepositoryImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeveloperController {
    private DeveloperRepository developerRepository;
    private SkillRepository skillRepository;
    private AccountRepository accountRepository;

    public DeveloperController(SkillRepository skillRepository, AccountRepository accountRepository) {
        this.skillRepository = skillRepository;
        this.accountRepository = accountRepository;
        developerRepository = new DeveloperRepositoryImpl(skillRepository, accountRepository);
    }

    public String getAll() throws NoSuchElementException, ChangesRejectedException {
        StringBuilder stringBuilder = new StringBuilder();

        for (Developer developer : developerRepository.readAll()) {
            stringBuilder.append(developer);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getById(long id) throws NoSuchElementException {
        Developer developer = developerRepository.readByID(id);
        if (developer == null) {
            return "Developer not found";
        }
        else {
            return developer.toString();
        }
    }

    public String addNewDeveloper(String name, String accountData, Set<Long> skillsId)
            throws ChangesRejectedException, NoSuchElementException {
        Long id = developerRepository.getLastId();
        Set<Skill> skills = new HashSet<Skill>();
        for (Long skillId : skillsId) {
            Skill skill = skillRepository.readByID(skillId);
            if (skill != null) {
                skills.add(skill);
            }
        }
        Long accountId = accountRepository.getLastId();
        Account account = new Account(accountId, accountData, AccountStatus.ACTIVE);
        Developer developer = new Developer(id, name, skills, account);
        List<Developer> developers = developerRepository.readAll();
        if (developers.contains(developer)) {
            return "Developer already exists";
        }
        else {
            developerRepository.create(developer);
            return "Operation completed successfully";
        }
    }
}
