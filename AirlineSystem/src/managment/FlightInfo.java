package managment;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class FlightInfo extends JFrame{
	
	FlightInfo(){
		getContentPane().setBackground(Color.WHITE);
        // Create JFrame
    	setTitle("FLIGHT INFORMATION");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650); 
        setLayout(null);
        JLabel heading = new JLabel("Flight information");
        heading.setBounds(375, 20, 300, 30);
        heading.setFont(new Font("Arial", Font.PLAIN, 20));
        heading.setForeground(Color.BLUE);
        add(heading);
        
     // Table setup
        String[] columns = {"Flight ID", "Flight Code", "Flight Name", "Origin", "Destination"};

        // Create the JTable with column headers and empty data initially
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable flightTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(50, 80, 850, 450);
        add(scrollPane);
        loadFlightData(model);
     // Set the window location to center
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	// Method to fetch data from MySQL database and populate the JTable
    private void loadFlightData(DefaultTableModel model) {
        String query = "SELECT * FROM flightinfo"; // SQL query to select all data from flightinfo table

        // Get the database connection from DBConnection
        try (Connection conn = DBConnection.getConnection();  // Use the static method to get the connection
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterate over the result set and add rows to the table model
            while (rs.next()) {
                String flightId = rs.getString("flight_id");
                String flightCode = rs.getString("flight_code");
                String flightName = rs.getString("flight_name");
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");

                // Add data to the table model
                model.addRow(new Object[]{flightId, flightCode, flightName, origin, destination});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading flight data from database.");
        }
    }
    
    
	public static void main(String[] args) {
		new FlightInfo();
	}

}