import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
    private String dburl = "jdbc:mysql://localhost:3306/userdb";
    private String dbpassword = "Gtagrand99%";
    private String dbdriver = "com.mysql.cj.jdbc.Driver";  // Correct driver for MySQL 8.0+

    // Method to load the database driver
    public void loadDriver(String dbDriver) {
        try {
            Class.forName(dbDriver);  // Properly load the JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // Print the stack trace if the driver is not found
        }
    }

    // Method to establish a connection to the database
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(dburl, "root", dbpassword); // Fixed: username is 'root' by default
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if connection fails
        }
        return con;  // Return the connection (which might be null if connection failed)
    }

    // Method to insert a Member into the database
    public String insert(Member member) {
        loadDriver(dbdriver);  // Load the driver
        Connection con = getConnection();  // Get the connection

        if (con == null) {  // If the connection is null, return an error message
            return "Connection to database failed!";
        }

        String sql = "INSERT INTO member (uname, password, email, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {  // Use try-with-resources to auto-close resources
            ps.setString(1, member.getUname());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getPhone());

            int rowsAffected = ps.executeUpdate();  // Execute the insert
            if (rowsAffected > 0) {
                return "Registration successful!";
            } else {
                return "Registration failed!";
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if there's a SQL exception
            return "Error occurred during registration.";
        } finally {
            try {
                if (con != null) con.close();  // Close the connection in the finally block
            } catch (SQLException e) {
                e.printStackTrace();  // Handle any errors while closing the connection
            }
        }
    }
}
