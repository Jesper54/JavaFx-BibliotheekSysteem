package sample.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class newScreenController {

    public void setScreen(String Filename)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Filename));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Home");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e)
        {
            e.getMessage();
        }
    }
}
