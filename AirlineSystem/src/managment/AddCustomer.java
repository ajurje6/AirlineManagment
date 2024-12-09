package managment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {
    JTextField txtName, txtSurname, txtNationality, txtAddress, txtPhone;
    JRadioButton rbMale, rbFemale;
    private Connection conn;

    public AddCustomer() {
        try {
            // Initialize the connection once when the class is created
            conn = DBConnection.getConnection(); // Get the connection from DBConnection class
            System.out.println("Connection initialized in AddCustomer constructor.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed.");
            System.exit(1); // Exit the application if the connection fails
        }

        // UI setup
        getContentPane().setBackground(Color.WHITE);
        setTitle("ADD CUSTOMER");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLayout(null);

        JLabel heading = new JLabel("PLEASE ADD CUSTOMER DETAILS");
        heading.setBounds(175, 20, 600, 30);
        heading.setFont(new Font("Arial", Font.PLAIN, 26));
        heading.setForeground(Color.RED);
        add(heading);

        // Name
        JLabel lblName = new JLabel("Name");
        lblName.setBounds(400, 90, 150, 25);
        lblName.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(550, 90, 175, 25);
        add(txtName);

        // Surname
        JLabel lblSurname = new JLabel("Surname");
        lblSurname.setBounds(400, 150, 150, 25);
        lblSurname.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblSurname);
        txtSurname = new JTextField();
        txtSurname.setBounds(550, 150, 175, 25);
        add(txtSurname);

        // Nationality
        JLabel lblNationality = new JLabel("Nationality");
        lblNationality.setBounds(400, 210, 150, 25);
        lblNationality.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblNationality);
        txtNationality = new JTextField();
        txtNationality.setBounds(550, 210, 175, 25);
        add(txtNationality);

        // Address
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(400, 270, 150, 25);
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(550, 270, 175, 25);
        add(txtAddress);

        // Phone
        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setBounds(400, 330, 150, 25);
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(550, 330, 175, 25);
        add(txtPhone);

        // Gender
        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(400, 390, 150, 25);
        lblGender.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblGender);
        ButtonGroup genderGroup = new ButtonGroup();

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(550, 390, 70, 25);
        rbMale.setBackground(Color.WHITE);
        add(rbMale);

        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(650, 390, 70, 25);
        rbFemale.setBackground(Color.WHITE);
        add(rbFemale);

        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        // Confirm Button
        JButton confirm = new JButton("CONFIRM");
        confirm.setBackground(Color.RED);
        confirm.setForeground(Color.WHITE);
        confirm.setBounds(475, 450, 150, 30);
        confirm.addActionListener(this);
        add(confirm);

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("managment/images/logo.png"));
        JLabel lblImage = new JLabel(image);
        lblImage.setBounds(75, 150, 280, 300);
        add(lblImage);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String nationality = txtNationality.getText();
        String address = txtAddress.getText();
        String phone = txtPhone.getText();
        String gender = rbMale.isSelected() ? "Male" : "Female";

        String query = "INSERT INTO passenger (name, surname, nationality, phone, gender, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) { // Use the connection initialized in constructor
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, nationality);
            pstmt.setString(4, phone);
            pstmt.setString(5, gender);
            pstmt.setString(6, address);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer details added to database");
            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add customer details.");
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
