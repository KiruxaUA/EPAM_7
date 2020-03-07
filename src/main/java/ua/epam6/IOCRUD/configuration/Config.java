package ua.epam6.IOCRUD.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryConfig.class, ServiceConfig.class, WebConfig.class})
public class Config {

}
