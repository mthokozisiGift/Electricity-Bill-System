import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ViewBill extends JFrame{
    private JPanel panel1;
    private JTextField txtPhone;
    private JTextField txtName;
    private JTextField txtMeter;
    private JTextArea txtResults;
    private JButton showButton;
    private JButton printButton;
    private JButton exitButton;

    public int phone;
    public int meter;
    public String name, results,fileName;

    public ViewBill(){
        setTitle("ADD BILL");
        setContentPane(panel1);
        setMinimumSize(new Dimension(700,650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phone = Integer.parseInt(txtPhone.getText());
                meter = Integer.parseInt(txtMeter.getText());
                name = txtName.getText();

                ExtractFromDatabase();

            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAsFile();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void SaveAsFile() {
        fileName = txtName.getText()+"_Electricity_Bill";
        try {
            FileWriter writer = new FileWriter(fileName+".txt");
            writer.write(" "+txtResults.getText());
            writer.close();
            JOptionPane.showMessageDialog(this,
                    "Done",
                    fileName+" Note Added",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ExtractFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/electricity_billing_system";
        String cUsername="root";
        String cPassword="";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url,cUsername,cPassword);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String q = "Select * From orders where phone_number = '"+phone+"' and meter_number = '"+meter+"' and cust_name = '"+name+"'";
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(q);
            if (rs.next()){
                txtResults.setText(txtResults.getText() +"\n");
                txtResults.setText(txtResults.getText() + "-----MTHOSKO ELECTRICITY BILLING SYSTEM------"+"\n");
                txtResults.setText(txtResults.getText() + "\n");
                txtResults.setText(txtResults.getText() + "Phone number: "+phone+"\n");
                txtResults.setText(txtResults.getText() + "Meter number: "+meter+"\n");
                txtResults.setText(txtResults.getText() + "Customer Name: "+name+"\n");
                Double amount = rs.getDouble("amount");
                txtResults.setText(txtResults.getText() + "Amount: R"+amount+"\n");
                Double unit = rs.getDouble("unit_");
                txtResults.setText(txtResults.getText() + "Unit(s): "+unit+"\n");
                txtResults.setText(txtResults.getText() + "\n");
                txtResults.setText(txtResults.getText() + "------------------Thank you----------------"+"\n");
                rs.close();
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
