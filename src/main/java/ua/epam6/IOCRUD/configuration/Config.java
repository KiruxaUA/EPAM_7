package ua.epam6.IOCRUD.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.epam6.IOCRUD.annotation.TimerBeanPostProcessor;

@Configuration
@Import({RepositoryConfig.class, ServiceConfig.class, WebConfig.class})
public class Config {
    @Bean
    public TimerBeanPostProcessor timerBeanPostProcessor() {
        return new TimerBeanPostProcessor();
    }
}
