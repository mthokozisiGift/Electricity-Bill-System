import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class AddBill extends JFrame {
    private JPanel panel1;
    private JButton processPurchaseButton;
    private JButton cancelButton;
    private JTextField txtPhone;
    private JTextField txtMeter;
    private JTextField txtName;
    private JTextField txtAmount;
    public String name;
    public int meter, phone;
    public double amount, unit;

    public AddBill(){
        setTitle("ADD BILL");
        setContentPane(panel1);
        setMinimumSize(new Dimension(700,650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        processPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phone = Integer.parseInt(txtPhone.getText());
                meter = Integer.parseInt(txtMeter.getText());
                name = txtName.getText();
                amount = Double.parseDouble(txtAmount.getText());
                unit = amount / 2.5;
                AddToDatabase();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void AddToDatabase() {
        String url = "jdbc:mysql://localhost:3306/electricity_billing_system";
        String cUsername="root";
        String cPassword="";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url,cUsername,cPassword);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String q = "Insert into orders(timestamp,phone_number,meter_number,cust_name,amount,unit_) "+
                    "Values('"+timestamp+"','"+phone+"','"+meter+"','"+name+"','"+amount+"','"+unit+"')";
            PreparedStatement pstmt = connection.prepareStatement(q);
            if (amount < 30.00){
                //not gonna happen
                JOptionPane.showMessageDialog(this,
                        "Can only process purchases from R30.00 to R1500.00 ",
                        "try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if (amount > 1500.00){
                // too much
                JOptionPane.showMessageDialog(this,
                        "Amount too high, can only process purchases up-to R1500.00 ",
                        "try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                pstmt.executeUpdate();
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(this,
                    String.valueOf(e),
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }




    }
}
