package ua.epam6.IOCRUD.repository.javaio;

import ua.epam6.IOCRUD.exceptions.FileProcessingException;
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
    private FileProcessor fileProcessor = new FileProcessor("src\\main\\resources\\files\\developers.txt");
    private AccountRepository accountRepository;
    private SkillRepository skillRepository;

    public DeveloperRepositoryImpl(SkillRepository skillRepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.skillRepository = skillRepository;
    }

    public Developer getById(Long id) {
        Developer developer = null;
        try {
            for (String line : fileProcessor.readFile()) {
                if (line.startsWith("id:" + id)) {
                    developer = deserialize(line);
                }
            }
        } catch (FileProcessingException e) {
            e.getMessage();
        }
        return developer;
    }

    public Long getLastId() {
        List<String> entities;
        try {
            entities = fileProcessor.readFile();
            if (entities.size() == 0) {
                return 0L;
            }
            long[] IDs = new long[entities.size()];
            for (int i = 0; i < entities.size(); i++) {
                IDs[i] = deserialize(entities.get(i)).getId();
            }
            long max = IDs[0];
            for (long item : IDs) {
                if (item > max) {
                    max = item;
                }
            }
            return max;
        }
        catch (FileProcessingException e) {
            e.getMessage();
        }
        return null;
    }

    public List<Developer> getAll() {
        List<Developer> list = new ArrayList<>();
        try {
            for (String line : fileProcessor.readFile()) {
                Developer developer = deserialize(line);
                if (developer.getId() != null) {
                    list.add(developer);
                }
            }
        } catch (FileProcessingException e) {
            e.getMessage();
        }
        return list;
    }

    public Developer create(Developer developer) {
        List<Developer> developers = getAll();
        developers.add(developer);
        serialize(developers);
        return developer;
    }

    public void delete(Long ID) {
        List<Developer> developers = getAll();
        Developer searchedDeveloper = developers.stream().filter(e -> e.getId().equals(ID)).findAny().get();
        if (developers.remove(searchedDeveloper)) {
            serialize(developers);
        }
    }

    public Developer update(Developer updatedDeveloper) throws NoSuchElementException {
        List<Developer> developers = getAll();
        boolean updated = developers.removeIf(dev -> dev.getId().equals(updatedDeveloper.getId()));
        if (!updated) {
            throw new NoSuchElementException();
        }
        developers.add(updatedDeveloper);
        serialize(developers);
        return updatedDeveloper;
    }

    private void serialize(Collection<Developer> collection) {
        List<String> serialized = collection.stream().map(this::stringify).collect(Collectors.toList());
        try {
            fileProcessor.writeFile(serialized);
        } catch (FileProcessingException e) {
            e.getMessage();
        }
    }

    private Developer deserialize(String line) {
        Long id = null;
        String firstName = null;
        String lastName = null;
        Account account = null;
        Set<Skill> skills = new HashSet<>();

        String[] tokens = line.split("/");
        for (String token : tokens) {
            if (token.startsWith("id:")) {
                id = Long.parseLong(token.substring(3));
            }
            if (token.startsWith("firstName:")) {
                firstName = token.substring(10);
            }
            if (token.startsWith("lastName:")) {
                lastName = token.substring(9);
            }
            if (token.startsWith("account:")) {
                account = accountRepository.getById(Long.parseLong(token.substring(8)));
            }
            if (token.startsWith("skills:")) {
                String[] numbers = token.substring(7).split(",");
                for (String number : numbers) {
                    skills.add(skillRepository.getById(Long.parseLong(number)));
                }
            }
        }
        return new Developer(id, firstName, lastName, skills, account);
    }

    private String stringify(Developer developer) {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "id:" + developer.getId() + "/firstName:" + developer.getFirstName() + "/lastName" +
                "/account:" + developer.getAccount().getId() + "/skills:";
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
