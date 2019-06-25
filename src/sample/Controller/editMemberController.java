package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import sample.Asset.DatabaseConnection;
import sample.Asset.PasswordEncryption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class editMemberController {

    @FXML
    private TextField editName;
    @FXML
    private TextField editEmail;
    @FXML
    private ChoiceBox editUserRole;

    public void initialize()
    {
        System.out.println("Member id : " + homeScreenController.MemberUserId);
        String Id = homeScreenController.MemberUserId;

        try
        {
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + Id );

            while(rs.next()){
                String username = rs.getString(3);
                String role = rs.getString(2);
                String email = rs.getString(5);

                editName.setText(username);
                editUserRole.setItems(FXCollections.observableArrayList(" "+ role + " ", "member", "admin"));
                editUserRole.getSelectionModel().selectFirst();
                editEmail.setText(email);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void SubmitEditUser(ActionEvent event)
    {
        String Name = editName.getText();
        String Email = editEmail.getText();
        String Role = editUserRole.getValue().toString();

        try {
            String Id = homeScreenController.MemberUserId;
            if (Name.isEmpty() && Email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Please fill in every textfield");
                alert.showAndWait();

            } else {

                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("UPDATE users SET username = "+ "'" + Name + "'" +", rol = "+ "'"+Role+"'" +", email = "+ "'" + Email + "'" +" WHERE id = "+ "'" + Id + "'" +" ");



                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Member Updated!");
                alert.showAndWait();

            }
        } catch (Exception e) {
            e.getMessage();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Inserting into the database failed!" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void CancelEditUser(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
