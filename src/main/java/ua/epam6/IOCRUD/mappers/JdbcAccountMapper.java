package ua.epam6.IOCRUD.mappers;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcAccountMapper implements Mapper<Account, ResultSet, Long> {
    private static final Logger log = Logger.getLogger(JdbcAccountMapper.class);

    public Account map(ResultSet source, Long searchID) throws SQLException {
        int currentRow = source.getRow();
        source.beforeFirst();
        long id = -1;
        String name = "";
        AccountStatus accountStatus = null;
        while(source.next()) {
            if(source.getLong(1) == searchID) {
                id = source.getLong(1);
                name = source.getString(2);
                accountStatus = AccountStatus.valueOf(source.getString(3));
            }
        }
        source.absolute(currentRow);
        if(id == -1) {
            log.warn("No such entry with ID: " + searchID);
        }
        return new Account(id, name, accountStatus);
    }
}
