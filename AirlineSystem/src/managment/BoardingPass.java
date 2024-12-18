package managment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class BoardingPass extends JFrame implements ActionListener {
    private JTextField txtPnr;
    private JLabel txtFname, txtSurname, txtNationality, lblSrc, lblDest, lblFname, lblFcode, lblDate;
    private JButton fetchButton;
    private Connection conn;

    public BoardingPass() {
        try {
            conn = DBConnection.getConnection(); // Get the database connection
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            System.exit(1);
        }

        setTitle("Boarding Pass");
        setSize(800, 500); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Add heading
        JLabel heading = new JLabel("BOARDING PASS");
        heading.setBounds(250, 10, 400, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(Color.RED);
        add(heading);
        
     // Add the image
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("managment/images/pass.png"));
        if (image.getImageLoadStatus() == MediaTracker.COMPLETE) {
            JLabel lblImage = new JLabel(image);
            lblImage.setBounds(450, 400, 300, 25);
            add(lblImage);
        } else {
            System.err.println("Image not found or failed to load.");
        }
        
        // Add PNR Details
        JLabel lblPnr = new JLabel("PNR:");
        lblPnr.setBounds(60, 80, 150, 25);
        lblPnr.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblPnr);

        txtPnr = new JTextField();
        txtPnr.setBounds(220, 80, 150, 25);
        add(txtPnr);

        fetchButton = new JButton("Fetch");
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.addActionListener(this);
        add(fetchButton);

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(60, 120, 150, 25);
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblName);

        txtFname = new JLabel();
        txtFname.setBounds(220, 120, 150, 25);
        add(txtFname);

        // Surname
        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setBounds(380, 120, 150, 25);
        lblSurname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblSurname);

        txtSurname = new JLabel();
        txtSurname.setBounds(540, 120, 150, 25);
        add(txtSurname);

        // Nationality
        JLabel lblNationality = new JLabel("Nationality:");
        lblNationality.setBounds(60, 160, 150, 25);
        lblNationality.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblNationality);

        txtNationality = new JLabel();
        txtNationality.setBounds(220, 160, 150, 25);
        add(txtNationality);

        // Source
        JLabel lblSource = new JLabel("Origin:");
        lblSource.setBounds(60, 200, 150, 25);
        lblSource.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblSource);

        lblSrc = new JLabel();
        lblSrc.setBounds(220, 200, 150, 25);
        add(lblSrc);

        // Destination
        JLabel lblDestination = new JLabel("Destination:");
        lblDestination.setBounds(380, 200, 150, 25);
        lblDestination.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblDestination);

        lblDest = new JLabel();
        lblDest.setBounds(540, 200, 150, 25);
        add(lblDest);

        // Flight Name
        JLabel lblFlightName = new JLabel("Flight Name:");
        lblFlightName.setBounds(60, 240, 150, 25);
        lblFlightName.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblFlightName);

        lblFname = new JLabel();
        lblFname.setBounds(220, 240, 150, 25);
        add(lblFname);

        // Flight Code
        JLabel lblFlightCode = new JLabel("Flight Code:");
        lblFlightCode.setBounds(380, 240, 150, 25);
        lblFlightCode.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblFlightCode);

        lblFcode = new JLabel();
        lblFcode.setBounds(540, 240, 150, 25);
        add(lblFcode);

        // Date
        JLabel lblDateLabel = new JLabel("Date:");
        lblDateLabel.setBounds(60, 280, 150, 25);
        lblDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblDateLabel);

        lblDate = new JLabel();
        lblDate.setBounds(220, 280, 150, 25);
        add(lblDate);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            fetchDetails();
        }
    }

    private void fetchDetails() {
        String pnr = txtPnr.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid PNR.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = """
            SELECT r.name, r.surname, r.nationality, r.origin, r.destination, r.date, fi.flight_name, fi.flight_code
            FROM reservations r
            JOIN flightinfo fi ON r.flight_code = fi.flight_code
            WHERE r.PNR = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pnr);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtFname.setText(rs.getString("name"));
                    txtSurname.setText(rs.getString("surname"));
                    txtNationality.setText(rs.getString("nationality"));
                    lblSrc.setText(rs.getString("origin"));
                    lblDest.setText(rs.getString("destination"));
                    lblDate.setText(rs.getString("date"));
                    lblFname.setText(rs.getString("flight_name"));
                    lblFcode.setText(rs.getString("flight_code"));
                } else {
                    JOptionPane.showMessageDialog(this, "No details found for the provided PNR.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}


