package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sample.Asset.DatabaseConnection;
import sample.Asset.PasswordEncryption;
import sample.Asset.User;

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
    protected void onclickLogin(ActionEvent event)
    {
        String Email = loginEmail.getText();
        String Password = loginPassword.getText();

            try
            {
                String query = "select * from users where email=?";

                PreparedStatement statement = DatabaseConnection.conn.prepareStatement(query);
                statement.setString(1, Email);
                ResultSet result=statement.executeQuery();
                if(result.next()){
                    if( result.getString(4).equals(PasswordEncryption.MD5(Password))){
                        errorLogin.setText("Login Geslaagd!");
                        errorLogin.setTextFill(Color.GREEN);

                        if (result.getString(2).equals("admin")){
                            User.setRole("admin");
                            User.setId(result.getString(1));
                            System.out.println("Role set to admin!");
                        }
                        else{
                            User.setRole("member");
                            System.out.println("Role set to member!");
                        }

                        User.setName(result.getString(3));

                        new newScreenController().setScreen("../View/homeScreen.fxml");
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    else{
                        errorLogin.setText("Wrong Password!");
                    }
                }
                else{
                    errorLogin.setText("Wrong Email");
                }
            }
            catch (SQLException sqlE)
            {
                System.out.println(sqlE.getMessage());

            }
        }
    }