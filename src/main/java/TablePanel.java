import javax.swing.*;
import javax.swing.border.Border;
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

    private Database database;
    private String[] columnNames = new String[0];
    private String[][] data = new String[0][0];
    private JTable table;
    private JScrollPane pane;

    public TablePanel(Database database) {
        this.database = database;
        showCustomerData();

        //add pane and buttons to panel
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 500));
        this.add(new ButtonPanel(), BorderLayout.NORTH);
        this.add(new CustomQueryPanel(), BorderLayout.SOUTH);
        updateTable();
    }

    private void updateTable(){
        if(pane != null)
        {
            this.remove(pane);
        }
        table = new JTable(new UneditedAbleTableModel());
        pane  = new JScrollPane(table);
        this.add(pane);
        this.repaint();
        this.revalidate();
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
                    data[i][j] = row[j];
                }
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

    private class ButtonPanel extends JPanel{
        public ButtonPanel() {
            //add buttons for the 4 different views
            JButton customerButton = new JButton("View Customers");
            customerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    showCustomerData();
                    updateTable();
                }
            });

            JButton transactionButton = new JButton("View Transactions");
            transactionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    showTransactionData();
                    updateTable();
                }
            });

            JButton pointOfSaleButton = new JButton("View Point of sales");
            pointOfSaleButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    showPointOfSaleData();
                    updateTable();
                }
            });

            JButton companyButton = new JButton("View Companies");
            companyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    showCompanyData();
                    updateTable();
                }
            });
            this.setLayout(new GridLayout(1, 4));
            this.add(customerButton);
            this.add(pointOfSaleButton);
            this.add(transactionButton);
            this.add(companyButton);
        }
    }

    private class CustomQueryPanel
        extends JPanel
    {
        public JTextField input;
        public JButton    button;

        public CustomQueryPanel()
        {
            input = new JTextField();
            input.addActionListener(new FieldActionListener());
            button = new JButton("Enter");
            button.addActionListener(new FieldActionListener());

            this.setLayout(new GridLayout(2,1));
            this.add(input);
            this.add(button);
        }

        private class FieldActionListener
            implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent) {
                doQuery();
                input.setText("");
            }
        }

        public void doQuery()
        {
            try
            {
                ResultSet resultSet = database.getCustomResult(input.getText());
                updateData(resultSet);
                updateColumnNames(resultSet);
                updateTable();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
