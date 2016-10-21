import javax.swing.*;
import java.awt.*;

/**
 * Created by nik on 10/21/16.
 */
public class MainFrame extends JFrame{

    public MainFrame(Database database)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(700,500));
        this.add(new TablePanel(database));
        this.pack();
        this.setVisible(true);
    }

}
