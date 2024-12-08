package managment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class AddCustomer extends JFrame implements ActionListener {
	JTextField txtName, txtSurname, txtNationality, txtAddress,txtPhone;
    JRadioButton rbMale, rbFemale;
    // Constructor to initialize the window
    public AddCustomer() {
    	 getContentPane().setBackground(Color.WHITE);
        // Create JFrame
    	 setTitle("ADD CUSTOMER");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550); 
        setLayout(null);
        JLabel heading = new JLabel("PLEASE ADD CUSTOMER DETAILS");
        heading.setBounds(175, 20, 600, 30);
        heading.setFont(new Font("Arial", Font.PLAIN, 26));
        heading.setForeground(Color.RED);
        add(heading);
        //NAME
        JLabel lblName = new JLabel("Name");
        lblName.setBounds(400, 90, 150, 25);
        lblName.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(550, 90, 175, 25);
        add(txtName);
        //SURNAME
        JLabel lblSurname = new JLabel("Surname");
        lblSurname.setBounds(400, 150, 150, 25);
        lblSurname.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblSurname);
        txtSurname = new JTextField();
        txtSurname.setBounds(550, 150, 175, 25);
        add(txtSurname);
        //NATIONALITY
        JLabel lblNationality = new JLabel("Nationality");
        lblNationality.setBounds(400, 210, 150, 25);
        lblNationality.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblNationality);
        txtNationality = new JTextField();
        txtNationality.setBounds(550, 210, 175, 25);
        add(txtNationality);
        //ADDRESS
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(400, 270, 150, 25);
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(550, 270, 175, 25);
        add(txtAddress);
        //PHONE
        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setBounds(400, 330, 150, 25);
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(550, 330, 175, 25);
        add(txtPhone);
        //GENDER
        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(400, 390, 150, 25);
        lblGender.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblGender);
        ButtonGroup gendergroup = new ButtonGroup();
        
        rbMale = new JRadioButton("Male");
        rbMale.setBounds(550, 390, 70, 25);
        rbMale.setBackground(Color.WHITE);
        add(rbMale);
        
        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(650, 390, 70, 25);
        rbFemale.setBackground(Color.WHITE);
        add(rbFemale);
        
        gendergroup.add(rbMale);
        gendergroup.add(rbFemale);
        //CONFIRM
        JButton confirm = new JButton("CONFIRM");
        confirm.setBackground(Color.RED);
        confirm.setForeground(Color.WHITE);
        confirm.setBounds(475, 450, 150, 30);
        confirm.addActionListener(this);
        add(confirm);
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("managment/images/logo.png"));
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(75, 150, 280, 300);
        add(lblimage);
        // Set the window location to center
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    public void actionPerformed(ActionEvent ae) {
    	 String name = txtName.getText();
    	 String surname = txtSurname.getText();
         String nationality = txtNationality.getText();
         String address = txtAddress.getText();
         String phone = txtPhone.getText();
         String gender = null;
         if (rbMale.isSelected()) {
             gender = "Male";
         } else {
             gender = "Female";
         }
         DBConnection Ac = new DBConnection();
         String query = "INSERT INTO passenger (name, surname, nationality, phone, gender, address) VALUES (?, ?, ?, ?, ?, ?)";
         try (PreparedStatement pstmt = Ac.getConnection().prepareStatement(query)) {
             pstmt.setString(1, name);
             pstmt.setString(2, surname);
             pstmt.setString(3, nationality);
             pstmt.setString(4, phone);
             pstmt.setString(5, gender);
             pstmt.setString(6, address);

             pstmt.executeUpdate(); // Executes the insert query
             JOptionPane.showMessageDialog(null, "Customer details added to database");
             setVisible(false);
         } catch (SQLException e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(null, "Failed");
         }
       
      
    }
    public static void main(String[] args) {
        // Create an instance of AddCustomer window
        new AddCustomer();
    }
}

