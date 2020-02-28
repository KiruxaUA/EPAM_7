package ua.epam6.IOCRUD.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcAccountRepositoryImpl;
import ua.epam6.IOCRUD.repository.jdbc.JdbcDeveloperRepositoryImpl;
import ua.epam6.IOCRUD.repository.jdbc.JdbcSkillRepositoryImpl;

@Configuration
public class RepositoryConfig {
    @Bean(name = "skillRepository")
    public SkillRepository skillRepository() {
        return new JdbcSkillRepositoryImpl();
    }

    @Bean(name = "accountRepository")
    public AccountRepository accountRepository() {
        return new JdbcAccountRepositoryImpl();
    }

    @Bean(name = "developerRepository")
    public DeveloperRepository developerRepository() {
        return new JdbcDeveloperRepositoryImpl();
    }
}
