import javax.swing.*;

/**
 * Created by nik on 10/21/16.
 */
public class MainFrame extends JFrame{

    public MainFrame(Database database)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new TablePanel(database));
        this.setVisible(true);
    }

}
