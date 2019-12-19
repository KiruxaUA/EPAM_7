package ua.epam6.IOCRUD.repository.javaio;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.utils.FileProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SkillRepositoryImpl implements SkillRepository {
    private FileProcessor fileProcessor = new FileProcessor("src\\main\\resources\\Skill.txt");

    public Skill readByID(Long id) {
        Skill skill = null;
        try {
            for (String line : fileProcessor.readFile()) {
                if (line.startsWith("id:" + id)) {
                    skill = deserialize(line);
                    break;
                }
            }
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
        return skill;
    }

    public List<Skill> readAll() {
        List<Skill> skills = new ArrayList<Skill>();
        try {
            for (String line : fileProcessor.readFile()) {
                Skill skill = deserialize(line);
                if (skill.getId() != null && skill.getName() != null) {
                    skills.add(skill);
                }
            }
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public void create(Skill skill) {
        List<Skill> skills = readAll();
        skills.add(skill);
        serialize(skills);
    }

    public void delete(Skill skill) {
        List<Skill> skills = readAll();
        if (skills.remove(skill)) {
            serialize(skills);
        }
    }

    public void update(Skill updatedSkill) throws NoSuchElementException {
        List<Skill> skills = readAll();
        boolean updated = skills.removeIf(skill -> skill.getId().equals(updatedSkill.getId()));
        if (!updated) {
            throw new NoSuchElementException();
        }
        skills.add(updatedSkill);
        serialize(skills);
    }

    private void serialize(Collection<Skill> collection) {
        List<String> serialized = collection.stream().map(this::stringify).collect(Collectors.toList());
        try {
            fileProcessor.writeFile(serialized);
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
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
