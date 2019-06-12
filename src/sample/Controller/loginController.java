package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/homeScreen.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Home");
                stage.setScene(new Scene(root1));
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();

            }
            catch (IOException e)
            {
                e.getMessage();
            }
        }
        else if (Password.equals("") || Username.equals(""))
        {
            errorLogin.setText("Email and/or Password is wrong!");
        }

        else
        {
            errorLogin.setText("Strange Error!");
        }

        System.out.println(Username + Password);
    }
}
