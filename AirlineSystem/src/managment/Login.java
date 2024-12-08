package managment;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    JButton btnLogin, btnCancel, btnResetFields;
    JTextField txtUsername;
    JPasswordField txtPassword;

    Login() {
        getContentPane().setBackground(Color.GRAY);
        // Sets up the frame
        setLayout(null); // Absolute positioning
        setSize(450, 350);
        setTitle("Login to the system");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
        setLocationRelativeTo(null); // Centering window
        setVisible(true);

        // Components
        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");
        btnResetFields = new JButton("Reset fields");

        // Adding components to the frame
        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnLogin);
        add(btnCancel);
        add(btnResetFields);

        // Positioning
        lblUsername.setBounds(30, 35, 175, 25);
        lblPassword.setBounds(30, 105, 175, 25);
        txtUsername.setBounds(120, 35, 235, 25);
        txtPassword.setBounds(120, 105, 235, 25);
        btnResetFields.setBounds(80, 200, 130, 25);
        btnLogin.setBounds(240, 200, 130, 25);
        btnCancel.setBounds(100, 250, 250, 30);

        // Adding action listeners for the buttons
        btnResetFields.addActionListener(this);
        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    // Action handling
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword()); // Use getPassword() for secure password handling

            try {
                DBConnection c = new DBConnection();
                // Using PreparedStatement to prevent SQL injection
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement pstmt = c.getConnection().prepareStatement(query);

                // Setting the parameters for the query
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                // Execute the query
                ResultSet rs = pstmt.executeQuery();

                // Check if login is valid
                if (rs.next()) {
                    System.out.println("Valid");
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    setVisible(false);
                }

            } catch (SQLException es) {
                es.printStackTrace();
            }

        } else if (e.getSource() == btnCancel) {
            setVisible(false);
        } else if (e.getSource() == btnResetFields) {
            txtUsername.setText("");
            txtPassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

