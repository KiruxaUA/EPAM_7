package ua.epam6.IOCRUD.mappers;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcSkillMapper implements Mapper<Skill, ResultSet, Long> {
    private static final Logger log = Logger.getLogger(JdbcSkillMapper.class);

    public Skill map(ResultSet source, Long searchID) throws SQLException {
        int currentRow = source.getRow();
        source.beforeFirst();
        long id = -1;
        String name = "";
        while(source.next()) {
            if(source.getLong(1) == searchID) {
                id = source.getLong(1);
                name = source.getString(2);
            }
        }
        source.absolute(currentRow);
        if(id == -1) {
            log.warn("No such entry with ID: " + searchID);
        }
        return new Skill(id, name);
    }
}
