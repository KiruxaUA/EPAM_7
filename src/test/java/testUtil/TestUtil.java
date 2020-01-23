package testUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtil {
    private static final String PATH_TO_PROPERTIES = "./src/main/resources/config.properties";
    private static final String workMode;
    private static final String testMode = "# JDBC\n" +
            "# JDBC\n" +
            "jdbc.driverClassName = org.h2.Driver\n" +
            "jdbc.url = jdbc:h2:mem:ioapplication;DB_CLOSE_DELAY=-1\n" +
            "jdbc.username = root\n" +
            "jdbc.password = MySQL_password_1999";
    static {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fr = new FileReader(PATH_TO_PROPERTIES)){
            int c;
            while ((c = fr.read()) != -1) {
                stringBuilder.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        workMode = stringBuilder.toString();
    }
    public static void toTestMode() {
        try(FileWriter fw = new FileWriter(PATH_TO_PROPERTIES, false)) {
            fw.write(testMode);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void toWorkMode() {
        try(FileWriter fw = new FileWriter(PATH_TO_PROPERTIES, false)) {
            fw.write(workMode);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}