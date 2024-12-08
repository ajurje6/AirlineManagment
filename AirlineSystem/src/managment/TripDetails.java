package managment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class TripDetails extends JFrame implements ActionListener {
    private JTable table;
    private JTextField txtPnr;
    private JButton show;
    private Connection conn;

    public TripDetails() {
        // Establish database connection
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            System.exit(1);
        }

        // JFrame setup
        setTitle("Trip Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Trip Details");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(0, 102, 204));
        heading.setBounds(375, 20, 300, 30);
        add(heading);

        JLabel lblPnr = new JLabel("PNR Number:");
        lblPnr.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPnr.setBounds(50, 100, 200, 25);
        add(lblPnr);

        txtPnr = new JTextField();
        txtPnr.setBounds(200, 100, 200, 30);
        txtPnr.setFont(new Font("Arial", Font.PLAIN, 16));
        add(txtPnr);

        show = new JButton("Show Details");
        show.setBackground(Color.RED);
        show.setForeground(Color.WHITE);
        show.setFont(new Font("Arial", Font.BOLD, 16));
        show.setBounds(420, 100, 150, 30);
        show.addActionListener(this);
        add(show);

        // Table to display results
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 200, 850, 350);
        add(jsp);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == show) {
            fetchTripDetails();
        }
    }

    private void fetchTripDetails() {
        String pnr = txtPnr.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid PNR number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM reservations WHERE PNR = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pnr);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.isBeforeFirst()) { // No data found
                    JOptionPane.showMessageDialog(this, "No details found for the entered PNR.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    
                    // If the table model is empty, initialize it with column names
                    if (model.getColumnCount() == 0) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        String[] columnNames = new String[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            columnNames[i - 1] = metaData.getColumnName(i);
                        }
                        model.setColumnIdentifiers(columnNames);
                    }

                    // Append rows to the table
                    while (rs.next()) {
                        Object[] rowData = new Object[model.getColumnCount()];
                        for (int i = 0; i < rowData.length; i++) {
                            rowData[i] = rs.getObject(i + 1);
                        }
                        model.addRow(rowData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
  

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Column names
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // Data rows
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        return model;
    }

    public static void main(String[] args) {
        new TripDetails();
    }
}

