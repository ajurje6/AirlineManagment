package managment;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Booking extends JFrame implements ActionListener {

    private JTextField txtPassengerId;
    private JLabel tfname, tfsurname, tfnationality, tfphone, tfaddress, labelfcode, labelflightname;
    private JButton bookflight, fetchButton, fetchFlightButton;
    private Choice sourceChoice, destinationChoice;
    private JDateChooser dcdate;

    // Class-level connection object
    private Connection conn;

    public Booking() {
        try {
            conn = DBConnection.getConnection(); // Initialize connection once
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            System.exit(1);
        }

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(300, 20, 500, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblPassengerId = new JLabel("Passenger ID");
        lblPassengerId.setBounds(60, 80, 150, 25);
        lblPassengerId.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblPassengerId);

        txtPassengerId = new JTextField();
        txtPassengerId.setBounds(220, 80, 150, 25);
        add(txtPassengerId);

        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);

        JLabel lblsurname = new JLabel("Surname");
        lblsurname.setBounds(60, 160, 150, 25);
        lblsurname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblsurname);

        tfsurname = new JLabel();
        tfsurname.setBounds(220, 160, 150, 25);
        add(tfsurname);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 190, 150, 25);
        lblphone.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblphone);

        tfphone = new JLabel();
        tfphone.setBounds(220, 190, 150, 25);
        add(tfphone);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 220, 150, 25);
        lblnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 220, 150, 25);
        add(tfnationality);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 250, 150, 25);
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JLabel();
        tfaddress.setBounds(220, 250, 150, 25);
        add(tfaddress);

        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 300, 150, 25);
        lblsource.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblsource);

        sourceChoice = new Choice();
        sourceChoice.setBounds(220, 300, 150, 25);
        add(sourceChoice);

        JLabel lbldestination = new JLabel("Destination");
        lbldestination.setBounds(60, 330, 150, 25);
        lbldestination.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbldestination);

        destinationChoice = new Choice();
        destinationChoice.setBounds(220, 330, 150, 25);
        add(destinationChoice);

        populateChoices();

        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 360, 150, 25);
        lbldate.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(220, 360, 150, 25);
        add(dcdate);

        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 400, 150, 25);
        lblfcode.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(220, 400, 150, 25);
        add(labelfcode);

        JLabel lblflightname = new JLabel("Flight Name");
        lblflightname.setBounds(60, 430, 150, 25);
        lblflightname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblflightname);

        labelflightname = new JLabel();
        labelflightname.setBounds(220, 430, 150, 25);
        add(labelflightname);

        fetchFlightButton = new JButton("Fetch Flight");
        fetchFlightButton.setBackground(Color.BLACK);
        fetchFlightButton.setForeground(Color.WHITE);
        fetchFlightButton.setBounds(380, 330, 120, 25);
        fetchFlightButton.addActionListener(this);
        add(fetchFlightButton);

        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 480, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocation(200, 100);
        setVisible(true);
    }

    private void populateChoices() {
        Set<String> uniqueSources = new HashSet<>();
        Set<String> uniqueDestinations = new HashSet<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT origin, destination FROM flightinfo")) {
            while (rs.next()) {
                uniqueSources.add(rs.getString("origin"));
                uniqueDestinations.add(rs.getString("destination"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String source : uniqueSources) {
            sourceChoice.add(source);
        }

        for (String destination : uniqueDestinations) {
            destinationChoice.add(destination);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == fetchButton) {
                fetchPassenger();
            } else if (ae.getSource() == bookflight) {
                bookFlight();
            } else if (ae.getSource() == fetchFlightButton) {
                fetchFlightDetails();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchPassenger() throws SQLException {
        String passengerId = txtPassengerId.getText().trim();
        if (passengerId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid Passenger ID.");
            return;
        }

        String query = "SELECT * FROM passenger WHERE passenger_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, passengerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfsurname.setText(rs.getString("surname"));
                    tfphone.setText(rs.getString("phone"));
                    tfnationality.setText(rs.getString("nationality"));
                    tfaddress.setText(rs.getString("address"));
                } else {
                    JOptionPane.showMessageDialog(null, "Passenger not found.");
                }
            }
        }
    }

    private void fetchFlightDetails() throws SQLException {
        String source = sourceChoice.getSelectedItem();
        String destination = destinationChoice.getSelectedItem();

        if (source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select both source and destination.");
            return;
        }

        String query = "SELECT flight_name, flight_code FROM flightinfo WHERE origin = ? AND destination = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, source);
            ps.setString(2, destination);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    labelflightname.setText(rs.getString("flight_name"));
                    labelfcode.setText(rs.getString("flight_code"));
                } else {
                    JOptionPane.showMessageDialog(null, "No flight found for the selected route.");
                }
            }
        }
    }

    private void bookFlight() throws SQLException {
        String travelDate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText().trim();
        if (travelDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a valid travel date.");
            return;
        }

        String query = "INSERT INTO reservations (name, surname, nationality, phone, address, origin, destination, date, flight_code, PNR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String PNR = "PNR-" + new Random().nextInt(100000);

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, tfname.getText());
            ps.setString(2, tfsurname.getText());
            ps.setString(3, tfnationality.getText());
            ps.setString(4, tfphone.getText());
            ps.setString(5, tfaddress.getText());
            ps.setString(6, sourceChoice.getSelectedItem());
            ps.setString(7, destinationChoice.getSelectedItem());
            ps.setString(8, travelDate);
            ps.setString(9, labelfcode.getText());
            ps.setString(10, PNR);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Flight booked successfully! Your PNR is " + PNR);
        }
    }

    public static void main(String[] args) {
        new Booking();
    }
}
