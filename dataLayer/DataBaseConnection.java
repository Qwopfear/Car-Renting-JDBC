package carsharing.dataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String DRIVER = "org.h2.Driver";

    String dataBasePath;



    public Connection createConnection () throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(dataBasePath);
        connection.setAutoCommit(true);

        return connection;
    }

    public DataBaseConnection (String dataBaseFileName) {

        this.dataBasePath = "jdbc:h2:D:\\Java_Backend\\Car Sharing\\Car Sharing\\task\\src\\carsharing/db/" + dataBaseFileName;
    }


}
