package sample.Controller;

import com.google.protobuf.Value;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.Asset.DatabaseConnection;
import sample.Asset.PasswordEncryption;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class addUserController {
    @FXML
    private TextField addUsername;

    @FXML
    private TextField addPassword;

    @FXML
    private TextField addEmail;


    @FXML
    private ChoiceBox roles;

    @FXML
    void initialize()
    {
        roles.setItems(FXCollections.observableArrayList("Select a role!", "Member", "Admin"));
        roles.getSelectionModel().selectFirst();
    }


    @FXML
    protected void cancelSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/homeScreen.fxml");
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void submitNewUser(ActionEvent event)
    {
        String Password = PasswordEncryption.MD5(addPassword.getText());
        String Email = addEmail.getText();
        String Username = addUsername.getText();

        try {
            if (Password.isEmpty() && Email.isEmpty() && Username.isEmpty() && roles.getValue() == "Select a role!") {
                System.out.println("Please fill in every textfield!");

            } else {
                if (roles.getValue() == "Admin") {
                    int role_id = 1;

                    Statement stmt = DatabaseConnection.conn.createStatement();
                    stmt.executeUpdate("insert into users (rol_id,username,password,email)VALUES('" + role_id + "','" + Username + "','" + Password + "','" + Email + "')");
                } else {
                    int role_id = 0;

                    Statement stmt = DatabaseConnection.conn.createStatement();
                    stmt.executeUpdate("insert into users (rol_id,username,password,email)VALUES('" + role_id + "','" + Username + "','" + Password + "','" + Email + "')");
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
            System.out.println("Inserting into database failed!");
        }
    }
}
