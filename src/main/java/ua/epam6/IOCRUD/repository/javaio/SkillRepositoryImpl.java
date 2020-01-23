package ua.epam6.IOCRUD.repository.javaio;

import ua.epam6.IOCRUD.exceptions.FileProcessingException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.utils.FileProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SkillRepositoryImpl implements SkillRepository {
    private FileProcessor fileProcessor = new FileProcessor("src\\main\\resources\\files\\skills.txt");

    public Skill getById(Long id) {
        Skill skill = null;
        try {
            for (String line : fileProcessor.readFile()) {
                if (line.startsWith("id:" + id)) {
                    skill = deserialize(line);
                    break;
                }
            }
        } catch (FileProcessingException e) {
            e.getMessage();
        }
        return skill;
    }

    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        try {
            for (String line : fileProcessor.readFile()) {
                Skill skill = deserialize(line);
                if (skill.getId() != null && skill.getName() != null) {
                    skills.add(skill);
                }
            }
        }
        catch (FileProcessingException e) {
            e.getMessage();
        }
        return skills;
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

    public Skill create(Skill skill) {
        List<Skill> skills = getAll();
        skills.add(skill);
        serialize(skills);
        return skill;
    }

    public void delete(Long ID) {
        List<Skill> skills = getAll();
        Skill searchedSkill = skills.stream().filter(e -> e.getId().equals(ID)).findAny().get();
        if (skills.remove(searchedSkill)) {
            serialize(skills);
        }
    }

    public Skill update(Skill updatedSkill) throws NoSuchElementException {
        List<Skill> skills = getAll();
        boolean updated = skills.removeIf(skill -> skill.getId().equals(updatedSkill.getId()));
        if (!updated) {
            throw new NoSuchElementException();
        }
        skills.add(updatedSkill);
        serialize(skills);
        return updatedSkill;
    }

    private void serialize(Collection<Skill> collection) {
        List<String> serialized = collection.stream().map(this::stringify).collect(Collectors.toList());
        try {
            fileProcessor.writeFile(serialized);
        } catch (FileProcessingException e) {
            e.getMessage();
        }
    }

    private Skill deserialize(String line) {
        Long id = null;
        String name = null;
        String[] tokens = line.split("/");
        for (String token : tokens) {
            if (token.startsWith("id:")) {
                id = Long.parseLong(token.substring(3));
            }
            if (token.startsWith("name:")) {
                name = token.substring(5);
            }
        }
        return new Skill(id, name);
    }

    private String stringify(Skill skill) {
        return "id:" + skill.getId() + "/name:" + skill.getName();
    }
}