package ua.epam6.IOCRUD.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.service.AccountService;
import ua.epam6.IOCRUD.service.DeveloperService;
import ua.epam6.IOCRUD.service.Serviceable;
import ua.epam6.IOCRUD.service.SkillService;

@Configuration
public class ServiceConfig {
    @Bean(name = "skillService")
    public Serviceable skillService(SkillRepository skillRepository) {
        return new SkillService(skillRepository);
    }

    @Bean(name = "accountService")
    public Serviceable accountService(AccountRepository accountRepository) {
        return new AccountService(accountRepository);
    }

    @Bean(name = "developerService")
    public Serviceable developerService(DeveloperRepository developerRepository) {
        return new DeveloperService(developerRepository);
    }
}
