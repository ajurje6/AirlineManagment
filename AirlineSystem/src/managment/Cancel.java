package managment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Random;

public class Cancel extends JFrame implements ActionListener {
    private JTextField txtPnr;
    private JLabel lblFlightName, lblSurname, lblCancelNo, lblFlightCode, lblDate;
    private JButton fetchButton, cancelButton;
    private Connection conn;

    public Cancel() {
        Random random = new Random();

        try {
            conn = DBConnection.getConnection(); // Get database connection
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            System.exit(1);
        }

        setTitle("Ticket Cancellation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Ticket Cancellation");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(0, 102, 204));
        heading.setBounds(325, 20, 300, 30);
        add(heading);

        JLabel lblPnr = new JLabel("PNR Number:");
        lblPnr.setBounds(60, 80, 150, 25);
        lblPnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblPnr);

        txtPnr = new JTextField();
        txtPnr.setBounds(220, 80, 150, 25);
        add(txtPnr);

        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(60, 130, 150, 25);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblName);

        lblFlightName = new JLabel("");
        lblFlightName.setBounds(220, 130, 150, 25);
        add(lblFlightName);

        JLabel lblSurnameLabel = new JLabel("Surname:");
        lblSurnameLabel.setBounds(60, 180, 150, 25);
        lblSurnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblSurnameLabel);

        lblSurname = new JLabel("");
        lblSurname.setBounds(220, 180, 150, 25);
        add(lblSurname);

        JLabel lblCancellation = new JLabel("Cancellation No:");
        lblCancellation.setBounds(60, 230, 150, 25);
        lblCancellation.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblCancellation);

        lblCancelNo = new JLabel("" + random.nextInt(1000000));
        lblCancelNo.setBounds(220, 230, 150, 25);
        add(lblCancelNo);

        JLabel lblAddress = new JLabel("Flight Code:");
        lblAddress.setBounds(60, 280, 150, 25);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblAddress);

        lblFlightCode = new JLabel("");
        lblFlightCode.setBounds(220, 280, 150, 25);
        add(lblFlightCode);

        JLabel lblDateLabel = new JLabel("Date:");
        lblDateLabel.setBounds(60, 330, 150, 25);
        lblDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblDateLabel);

        lblDate = new JLabel("");
        lblDate.setBounds(220, 330, 150, 25);
        add(lblDate);

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(220, 380, 150, 25);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            fetchTicketDetails();
        } else if (ae.getSource() == cancelButton) {
            cancelTicket();
        }
    }

    private void fetchTicketDetails() {
        String pnr = txtPnr.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid PNR number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT name, surname, flight_code, date FROM reservations WHERE PNR = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pnr);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblFlightName.setText(rs.getString("name"));
                    lblSurname.setText(rs.getString("surname"));
                    lblFlightCode.setText(rs.getString("flight_code"));
                    lblDate.setText(rs.getString("date"));
                } else {
                    JOptionPane.showMessageDialog(this, "No ticket found for the provided PNR.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelTicket() {
        String pnr = txtPnr.getText().trim();
        String name = lblFlightName.getText();
        String surname = lblSurname.getText();
        String cancelNo = lblCancelNo.getText();
        String flightCode = lblFlightCode.getText();
        String date = lblDate.getText();

        if (pnr.isEmpty() || name.isEmpty() || surname.isEmpty() || flightCode.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ticket details to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String insertQuery = "INSERT INTO cancel (cancel_id, name, surname, cancel_number, date, flight_code, PNR) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        String deleteQuery = "DELETE FROM reservations WHERE PNR = ?";
        try (PreparedStatement insertPs = conn.prepareStatement(insertQuery);
             PreparedStatement deletePs = conn.prepareStatement(deleteQuery)) {

            // Insert cancellation record
            insertPs.setString(1, name);
            insertPs.setString(2, surname);
            insertPs.setString(3, cancelNo);
            insertPs.setString(4, date);
            insertPs.setString(5, flightCode);
            insertPs.setString(6, pnr);
            insertPs.executeUpdate();

            // Delete the ticket record
            deletePs.setString(1, pnr);
            deletePs.executeUpdate();

            JOptionPane.showMessageDialog(this, "Ticket successfully cancelled.", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while cancelling the ticket.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Cancel();
    }
}
