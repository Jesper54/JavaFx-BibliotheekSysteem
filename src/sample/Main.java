package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Asset.DatabaseConnection;
import sample.Asset.PasswordEncryption;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DatabaseConnection.connection();
        Parent root = FXMLLoader.load(getClass().getResource("View/loginScreen.fxml"));
        primaryStage.setTitle("Library System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
