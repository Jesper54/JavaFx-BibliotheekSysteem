package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    @FXML
    private TextField loginEmail;

    @FXML
    private TextField loginPassword;

    @FXML
    private Label errorLogin;

    @FXML
    private void onclickLogin(ActionEvent event)
    {
        String Username = loginEmail.getText();
        String Password = loginPassword.getText();

        if (Password.equals("test") && Username.equals("test@gmail.com")) {
            errorLogin.setText("Login Geslaagd!");
            errorLogin.setTextFill(Color.GREEN);

            try
            {
                String query = "select password from login where username=?";
                PreparedStatement statement = DatabaseConnection.prepareStatement(query);
                statement.setString(1, Username);
                ResultSet result=statement.executeQuery();

                if(result.next()){
                    if( result.getString(1).equalsIgnoreCase(Password)){
                        System.out.println("Logged In");

                        new newScreenController().setScreen("../View/homeScreen.fxml");
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    else{
                        System.out.println("Invalid Password");
                    }
                }
                else{
                    System.out.println("Invalid Username");
                }
            }
            catch (SQLException sqlE)
            {
                sqlE.getMessage();
            }
        }
        else
        {
            errorLogin.setText("Email and/or Password is wrong!");
        }


        System.out.println(Username + Password);
    }
}
