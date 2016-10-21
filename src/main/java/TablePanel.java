import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nik on 10/21/16.
 */
public class TablePanel extends JPanel {

//    private static final String[] columnNames = {"id", "name", "salary"};
//    private static final String[][] data = {
//            {"1", "nik",     "10000"},
//            {"2", "carla",    "9000"},
//            {"3", "philip",   "8000"},
//            {"4", "bianca",   "7000"},
//            {"5", "michael",  "6000"},
//    };

    private Database database;
    private String[] columnNames = new String[0];
    private String[][] data = new String[0][0];

    public TablePanel(Database database)
    {
        this.database = database;

        JTable table = new JTable(new UneditedAbleTableModel());
        JScrollPane pane = new JScrollPane(table);

        //add buttons for the 4 different views
        JButton customerButton = new JButton("View Customers");
        customerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                showCustomerData();
                repaint();
            }
        });

        JButton transactionButton = new JButton("View Transactions");
        transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                showTransactionData();
                repaint();
            }
        });

        JButton pointOfSaleButton = new JButton("View Point of sales");
        pointOfSaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                showPointOfSaleData();
                repaint();
            }
        });

        JButton companyButton = new JButton("View Companies");
        companyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                showCustomerData();
                repaint();
            }
        });

        //add pane and buttons to panel
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        this.add(companyButton, c);

        c.gridx++;
        this.add(pointOfSaleButton, c);

        c.gridx++;
        this.add(transactionButton, c);

        c.gridx++;
        this.add(customerButton, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth  = 5;
        c.gridheight = 5;
        this.add(pane, c);
    }

    private void showTransactionData()
    {
        try
        {
            setData(database.getTransctions());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void showPointOfSaleData()
    {
        try
        {
            setData(database.getPointOfSales());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void showCustomerData()
    {
        try
        {
            setData(database.getCustomers());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void showCompanyData()
    {
        try
        {
            setData(database.getCompanies());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }




    private void setData(ResultSet set)
    {
        updateColumnNames(set);
        updateData(set);
    }

    private void updateColumnNames(ResultSet resultSet)
    {
        try
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            String[] names = new String[columns];
            for(int i = 0; i < columns; i++)
            {
                names[i] = metaData.getColumnName(i + 1);
            }
            columnNames = names;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void updateData(ResultSet resultSet)
    {
        try
        {
            int columns = resultSet.getMetaData().getColumnCount();
            ArrayList<String[]> table = new ArrayList<String[]>();
            while(resultSet.next())
            {
                String[] row = new String[columns];
                for(int i = 0; i < columns; i++)
                {
                    row[i] = resultSet.getString(i + 1);
                }
                table.add(row);
            }
            String[][] data = new String[table.size()][columns];
            for(int i = 0 ; i < data.length; i++)
            {
                String[] row = table.get(i);
                for(int j = 0; j < row.length; j++)
                {
                    System.out.print(row[j]);
                    data[i][j] = row[j];
                }
                System.out.println();
            }
            this.data = data;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private class UneditedAbleTableModel
        extends AbstractTableModel
    {
        public String getColumnName(int col)
        {
            return columnNames[col];
        }

        public int getRowCount()
        {
            return data.length;
        }

        public int getColumnCount()
        {
            return columnNames.length;
        }

        public Object getValueAt(int row, int col)
        {
            return data[row][col];
        }

        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    }
}
