# AirlineManagment
Airline managment system made using Java swing and MYSQL
Use: name=admin password=admin for login
### Setting Up the Database
Download the database dump file from the repository.
Create a new MySQL database (if not already created)
Import the dump file into the database
Ensure the tables and data are correctly set up by checking the database
### Configuring the Database Connection
 Open the `DBConnection.java` file.
 Set the connection URL, username, and password according to your local environment. For example:
   public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "yourpassword");
   }
Replace localhost, mydatabase, and yourpassword with the correct values for your setup
Database dump contains some sample data
