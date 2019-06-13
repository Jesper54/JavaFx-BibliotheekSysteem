package sample.Asset;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection conn = null;

    public static void connection() {
        if (conn!=null) {
            throw new Error("Database connection already open.");
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/java_bibliotheeksysteem?user=root");

        } catch (SQLException ex) {
            // error die gebruiker krijgt te zien bij een fout
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Database is not turned on!");
            alert.showAndWait();

//            Uit gefiltert voor het geval dat
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());

            throw new Error("Fout bij verbinden MySQL.");
        }
    }

}
