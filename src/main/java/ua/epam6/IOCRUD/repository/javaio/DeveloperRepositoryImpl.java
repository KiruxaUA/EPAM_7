package ua.epam6.IOCRUD.repository.javaio;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.utils.FileProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private FileProcessor fileProcessor = new FileProcessor("src\\main\\resources\\Developer.txt");
    private AccountRepository accountRepository;
    private SkillRepository skillRepository;

    public DeveloperRepositoryImpl(SkillRepository skillRepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.skillRepository = skillRepository;
    }

    public Developer readByID(Long id) throws NoSuchElementException {
        Developer developer = null;
        try {
            for (String line : fileProcessor.readFile()) {
                if (line.startsWith("id:" + id)) {
                    developer = deserialize(line);
                }
            }
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
        return developer;
    }

    public List<Developer> readAll() throws NoSuchElementException {
        List<Developer> list = new ArrayList<Developer>();
        try {
            for (String line : fileProcessor.readFile()) {
                Developer developer = deserialize(line);
                if (developer.getId() != null) {
                    list.add(developer);
                }
            }
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void create(Developer developer) throws NoSuchElementException {
        List<Developer> developers = readAll();
        developers.add(developer);
        serialize(developers);
    }

    public void delete(Developer developer) throws NoSuchElementException {
        List<Developer> developers = readAll();
        if (developers.remove(developer)) {
            serialize(developers);
        }
    }

    public void update(Developer updatedDeveloper) throws NoSuchElementException {
        List<Developer> developers = readAll();
        boolean updated = developers.removeIf(dev -> dev.getId().equals(updatedDeveloper.getId()));
        if (!updated) {
            throw new NoSuchElementException();
        }
        developers.add(updatedDeveloper);
        serialize(developers);
    }

    private void serialize(Collection<Developer> collection) {
        List<String> serialized = collection.stream().map(this::stringify).collect(Collectors.toList());
        try {
            fileProcessor.writeFile(serialized);
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
    }

    private Developer deserialize(String line) throws NoSuchElementException {
        Long id = null;
        String name = null;
        Account account = null;
        Set<Skill> skills = new HashSet<Skill>();

        String[] tokens = line.split("/");
        for (String token : tokens) {
            if (token.startsWith("id:")) {
                id = Long.parseLong(token.substring(3));
            }
            if (token.startsWith("name:")) {
                name = token.substring(5);
            }
            if (token.startsWith("account:")) {
                account = accountRepository.readByID(Long.parseLong(token.substring(8)));
            }
            if (token.startsWith("skills:")) {
                String[] numbers = token.substring(7).split(",");
                for (String number : numbers) {
                    skills.add(skillRepository.readByID(Long.parseLong(number)));
                }
            }
        }
        return new Developer(id, name, skills, account);
    }

    private String stringify(Developer developer) {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "id:" + developer.getId() + "/name:" + developer.getName() + "/account:"
                + developer.getAccount().getId() + "/skills:";
        stringBuilder.append(str);
        for (Skill skill : developer.getSkills()) {
            stringBuilder.append(skill.getId());
            stringBuilder.append(",");
        }
        if (developer.getSkills().size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
