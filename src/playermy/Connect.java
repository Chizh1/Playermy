import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Adminh
 */

public class Connect {

    private final static String LOG_TAG = "myLogs";
    private final static Logger log=Logger.getLogger(LOG_TAG);
    final public String driver="com.mysql.jdbc.Driver";
    final public String connect="jdbc:mysql://localhost/Player";
    final public String User = "adminh";
    final public String Password = "123456";
    private Connection connection;

    public Connect() {
        connection = null;
    }
    public Connection getConnect(){
        try {
            Class.forName(driver).newInstance();
            connection =DriverManager.getConnection(connect, User, Password);
        } catch (ClassNotFoundException e) {
            log.log(Level.WARNING, "driver is not installed\n", e);
            JOptionPane.showMessageDialog(null, "Не установлен драйвер", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (SQLException e) {
            log.log(Level.WARNING, "not connected to a database\n", e);
            JOptionPane.showMessageDialog(null, "Нет соединения с БД", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
