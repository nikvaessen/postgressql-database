import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by nik on 10/11/16.
 */
public class FillTable
{

    private static Random rng = new Random(System.currentTimeMillis());
    private static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static void main(String args[]) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/javadatabase",
                            "postgres", "postgres");

            c.setAutoCommit(false);

            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }



    public static void makeTable(Statement s)
        throws Exception
    {
        String sqlStatement =
                "CREATE TABLE NAMES( " +
                        "ID SERIAL PRIMARY KEY," +
                        "NAME TEXT NOT NULL " +
                ")";
        s.execute(sqlStatement);
        s.close();
    }

    public static void fillTableWithNames(Connection c)
            throws Exception
    {
        //delete the table is it already exists
        String sqlDrop = "DROP TABLE if EXISTS NAMES cascade";
        Statement dropStatement = c.createStatement();
        dropStatement.executeUpdate(sqlDrop);
        dropStatement.close();
        c.commit();

        makeTable(c.createStatement());
        c.commit();

        fillTable(c.createStatement());
        c.commit();
    }

    public static void fillTable(Statement s)
        throws Exception
    {
        String name;
        System.out.println("Adding a million names");
        int amountOfJohn = 1000;
        int totalNames = 1000000;
        for(int i = 0; i < totalNames; i++)
        {
            if(i % 10000 == 0)
            {
                System.out.println(i);
            }
            if(rng.nextInt(totalNames) < amountOfJohn)
            {
                name = "'John'";
            }
            else
            {
                name =  "'" + getRandomName(rng.nextInt(15) + 1) + "'";
            }
            String insertQuery = String.format("INSERT INTO NAMES (NAME) VALUES(%s )", name);
            //System.out.println("Executing: " + insertQuery);
            s.executeUpdate(insertQuery);
        }
        System.out.println("Added a million names!");
        s.close();
    }

    public static String getRandomName(int len)
    {
        String s = "";
        for(int i = 0; i < len; i++)
        {
            s += alphabet[rng.nextInt(alphabet.length)];
        }
        return s;
    }
}

