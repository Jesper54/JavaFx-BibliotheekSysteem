package sample.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection conn = null;

    public static void connection() {
        if (conn!=null) {
            throw new Error("Database connection already open.");
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/java_bibliotheeksysteem?user=root");
            System.out.println("Connection Opened");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new Error("Fout bij verbinden MySQL.");
        }
    }

}
