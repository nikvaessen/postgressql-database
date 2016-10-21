import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nik on 10/21/16.
 */
public class Database {

    private static final String url  = "jdbc:postgresql://localhost:5432/Databases_project";
    private static final String user = "postgres";
    private static final String pass = user;

    private static final String customerQuery    = "select * from Customer";
    private static final String pointOfSaleQuery = "select * from Point_of_Sale";
    private static final String transactionQuery = "select * from Transaction";
    private static final String companyQuery     = "select * from Company";

    private SelectQuery query;

    public Database()
            throws SQLException
    {
        query = new SelectQuery(url, user, pass);
    }

    public ResultSet getCustomers()
            throws SQLException
    {
        return query.getData(customerQuery);
    }

    public ResultSet getTransctions()
            throws SQLException
    {
        return query.getData(transactionQuery);
    }

    public ResultSet getPointOfSales()
            throws SQLException
    {
        return query.getData(pointOfSaleQuery);
    }

    public ResultSet getCompanies()
            throws SQLException
    {
        return query.getData(companyQuery);
    }

    public ResultSet getCustomResult(String sQuery)
        throws SQLException
    {
        return query.getData(sQuery);
    }
}
