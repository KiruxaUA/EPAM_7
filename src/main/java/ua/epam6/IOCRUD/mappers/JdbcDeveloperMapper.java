package ua.epam6.IOCRUD.mappers;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.model.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class JdbcDeveloperMapper implements Mapper<Developer, ResultSet, Long> {
    private static final Logger log = Logger.getLogger(JdbcDeveloperMapper.class);

    public Developer map(ResultSet source, Long searchID) throws SQLException {
        int currentRow = source.getRow();
        source.beforeFirst();
        long id = -1;
        String firstName = "";
        String lastName = "";
        Set<Skill> skills = new HashSet<>();
        Account account = null;
        while (source.next()) {
            if(source.getLong(1) == searchID) {
                id = source.getLong(1);
                firstName = source.getString(2);
                lastName = source.getString(3);
                skills.add(new Skill(source.getLong(4), source.getString(5)));
                account = (source.getString(6) == null) ? null : new Account(source.getLong(6),
                                source.getString(7),
                                AccountStatus.valueOf(source.getString(8)));
            }
        }
        source.absolute(currentRow);
        if(id == -1) {
            log.warn("No such entry with ID: " + searchID);
        }
        return new Developer(id, firstName, lastName, skills, account);
    }
}
