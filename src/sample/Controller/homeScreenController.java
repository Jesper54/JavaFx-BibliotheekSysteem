package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import sample.Asset.DatabaseConnection;

import java.sql.PreparedStatement;

public class homeScreenController {
    @FXML
    private Label homeWelcome;

    @FXML
    private TextField homeInputSearch;

    @FXML
    private Button homeSubmitSearch;

    @FXML
    private TableColumn homeTableId;
    private TableColumn homeTableName;
    private TableColumn homeTableDescription;
    private TableColumn homeTableCategory;
    private TableColumn homeTableStock;

    @FXML
    public void initialize()
    {
        try
        {
            String query = "select * from items";
            PreparedStatement statement = DatabaseConnection.conn.prepareStatement(query);



        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    @FXML
    protected void logoutSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/loginScreen.fxml");
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void searchSubmit(ActionEvent event)
    {
        //ZOEK FUNCTIE MAKEN
        System.out.println(homeInputSearch.getText());
    }
}
