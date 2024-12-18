package managment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {
    Home() {
        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set the frame to full-screen size
        setTitle("Home Page");
        setSize(screenWidth, screenHeight);
        setLayout(null); // Absolute positioning
        setLocationRelativeTo(null); // Centering window (though not necessary for full-screen)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit

        // Loading and scaling the image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/managment/images/homeBackground.jpg"));
        Image img = backgroundImage.getImage();
        Image scaledImg = img.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(scaledImg);

        // Creating a JLabel with the image
        JLabel backgroundLbl = new JLabel(backgroundImage);
        backgroundLbl.setBounds(0, 0, screenWidth, screenHeight); // Match the frame size
        add(backgroundLbl);

        JLabel welcomeLbl = new JLabel("Welcome to Croatia Airlines", SwingConstants.CENTER);
        welcomeLbl.setFont(new Font("Arial", Font.BOLD, 36)); // Font and size
        welcomeLbl.setForeground(Color.WHITE); // Text color
        welcomeLbl.setBounds(screenWidth / 4, 20, screenWidth / 2, 50); // Position at top-center
        backgroundLbl.add(welcomeLbl); // Add the label to the background

        // Adding the menu bar
        setJMenuBar(createMenuBar());

        setVisible(true); // Make the frame visible
    }

    // Method to create the menu bar
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Creating parent menus with specific colors
        JMenu detailsMenu = createColoredMenu("Details", Color.RED);
        JMenu ticketMenu = createColoredMenu("Ticket", Color.BLUE);
        JMenu flightMenu = createColoredMenu("Flight", Color.RED);
        JMenu customerMenu = createColoredMenu("Customer", Color.BLUE);

        // Creating menu items for "Details" menu
        JMenuItem tripDetailsMenuItem = new JMenuItem("Trip Details");

        // Adding menu items to "Details" menu
        detailsMenu.add(tripDetailsMenuItem);

        // Creating menu items for "Ticket" menu
        JMenuItem boardingPassMenuItem = new JMenuItem("Boarding Pass");
        JMenuItem bookingMenuItem = new JMenuItem("Booking");
        JMenuItem cancelTicketMenuItem = new JMenuItem("Cancel Ticket");

        // Adding menu items to "Ticket" menu
        ticketMenu.add(boardingPassMenuItem);
        ticketMenu.add(bookingMenuItem);
        ticketMenu.add(cancelTicketMenuItem);

        // Creating menu items for "Flight" menu
        JMenuItem flightInfoMenuItem = new JMenuItem("Flight Info");

        // Adding menu item to "Flight" menu
        flightMenu.add(flightInfoMenuItem);

        // Creating menu items for "Customer" menu
        JMenuItem addCustomerMenuItem = new JMenuItem("Add Customer Details");

        // Adding menu item to "Customer" menu
        customerMenu.add(addCustomerMenuItem);

        // Adding menus to the menu bar with spacing
        menuBar.add(detailsMenu);
        menuBar.add(Box.createHorizontalStrut(50)); // Spacing
        menuBar.add(ticketMenu);
        menuBar.add(Box.createHorizontalStrut(50)); // Spacing
        menuBar.add(flightMenu);
        menuBar.add(Box.createHorizontalStrut(50)); // Spacing
        menuBar.add(customerMenu);

        // Adding action listeners to menu items
        addCustomerMenuItem.addActionListener(this);
        flightInfoMenuItem.addActionListener(this);
        tripDetailsMenuItem.addActionListener(this);
        bookingMenuItem.addActionListener(this);
        cancelTicketMenuItem.addActionListener(this);
        boardingPassMenuItem.addActionListener(this);
        return menuBar;
    }

    // Method to create a colored menu
    private JMenu createColoredMenu(String text, Color color) {
        JMenu menu = new JMenu(text);
        menu.setForeground(color); // Set the text color
        menu.setFont(new Font("Arial", Font.BOLD, 16)); // Set the font and size
        return menu;
    }

    // Action listener method
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();

        if (text.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (text.equals("Flight Info")) {
            new FlightInfo();
        } else if (text.equals("Booking")) {
            new Booking();
        } else if (text.equals("Trip Details")) {
            new TripDetails();
        } else if (text.equals("Cancel Ticket")) {
        new Cancel();
        }else if (text.equals("Boarding Pass")) {
        new BoardingPass();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}

