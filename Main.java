import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JButton ADDBILLButton;
    private JButton VIEWBILLButton;
    private JPanel MainPanel;

    public static void main(String[] args) {
        new Main();
    }
    public Main(){
        setTitle("ADD BILL");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(700,650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        ADDBILLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddBill();
            }
        });
        VIEWBILLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewBill();
            }
        });
    }
}
