
import java.sql.*;
import java.util.List;

/**
 * Created by nik on 10/21/16.
 */
public class SelectQuery {

    private Connection connection;

    public SelectQuery(String url, String username, String password)
            throws SQLException
    {
        connection = DriverManager.getConnection(url, username, password);
    }

    public ResultSet getData(String query)
            throws SQLException
    {
        Statement s = connection.createStatement();
        return s.executeQuery(query);
    }

}
