package ua.epam6.IOCRUD.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.testUtil.TestUtil;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {

    @InjectMocks
    private static SkillService testSkillService;

    @Mock
    private SkillRepository currentRepo;
    private Skill createSkill = new Skill(5L, "PHP");
    private Skill updateSkill = new Skill(5L, "Scala");

    @BeforeClass
    public static void connect(){
        TestUtil.toTestMode();
        try {
            testSkillService = new SkillService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void rollbackProperties(){
        TestUtil.toWorkMode();
    }

    @Test
    public void checkCreation() {
        try {
            testSkillService.create(createSkill);
            verify(currentRepo, times(1)).create(createSkill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetById() {
        try {
            testSkillService.getById(1L);
            verify(currentRepo, times(1)).getById(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkUpdating() {
        try {
            testSkillService.update(updateSkill);
            verify(currentRepo, times(1)).update(updateSkill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDeleting() {
        try {
            testSkillService.delete(2L);
            verify(currentRepo, times(1)).delete(2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetAll() {
        try {
            testSkillService.getAll();
            verify(currentRepo, times(1)).getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
