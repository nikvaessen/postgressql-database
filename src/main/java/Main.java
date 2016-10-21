import java.sql.*;
import java.util.ArrayList;


/**
 * Created by nik on 10/21/16.
 */
public class Main {

    public static void main(String[] args)
            throws SQLException
    {
        new MainFrame(new Database());
    }

    public static void printTable(ArrayList<String[]> table)
    {
        for(String[] row: table)
        {
            String s = "";
            for(int i = 0; i < row.length; i++)
            {
                s += row[i];
                if(i != row.length - 1)
                {
                    s += " ";
                }
            }
            System.out.println(s);
        }
    }

}
