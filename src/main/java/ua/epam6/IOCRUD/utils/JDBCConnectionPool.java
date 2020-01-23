package ua.epam6.IOCRUD.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnectionPool {
    private static final Logger log = Logger.getLogger(JDBCConnectionPool.class);
    private final static String LINK_TO_CONFIG = "./src/main/resources/config.properties";
    private static BasicDataSource ds = new BasicDataSource();
    static {
        Properties properties = new Properties();
        try(FileReader fr = new FileReader(LINK_TO_CONFIG)){
            properties.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ds.setDriverClassName(properties.getProperty("jdbc.driver"));
        ds.setUrl(properties.getProperty("jdbc.url"));
        ds.setUsername(properties.getProperty("jdbc.user"));
        ds.setPassword(properties.getProperty("jdbc.password"));
    }
    public static Connection getConnection() throws SQLException {
        log.debug("Getting connection from pool");
        return ds.getConnection();
    }
}
