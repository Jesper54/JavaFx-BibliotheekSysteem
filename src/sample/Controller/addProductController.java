package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import sample.Asset.DatabaseConnection;

import javax.print.DocFlavor;
import java.sql.Statement;

public class addProductController {

    @FXML
    private ChoiceBox createCategory;
    @FXML
    private TextField createTitle;
    @FXML
    private TextField createAuthor;
    @FXML
    private TextArea createDescription;
    @FXML
    private TextField createStock;


    @FXML
    private void initialize()
    {
        createCategory.setItems(FXCollections.observableArrayList("Select a Category!", "Book", "E-book", "Dvd", "Cd", "Magazine"));
        createCategory.getSelectionModel().selectFirst();
    }


    @FXML
    private void createSubmit(ActionEvent event) {

        String Title = createTitle.getText();
        String Author = createAuthor.getText();
        String Description = createDescription.getText();
        String Category = createCategory.getValue().toString();
        String Stock = (createStock.getText());

        if (Stock.matches("[0-9]+")) {
            try {
                if (Title.isEmpty() && Author.isEmpty() && Description.isEmpty() && createCategory.getValue().equals("Select a Category!") && Stock.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText("Please fill in every textfield");
                    alert.showAndWait();

                } else {

                    Statement stmt = DatabaseConnection.conn.createStatement();
                    stmt.executeUpdate("INSERT INTO items (name,author,category,description,stock)VALUES('" + Title + "','" + Author + "','" + Category + "','" + Description + "','" + Stock + "')");

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Product Created!");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.getMessage();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Inserting into the database failed!");
                alert.showAndWait();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Please enter only numbers at the stock textfield");
            alert.showAndWait();
        }
    }


    @FXML
    private void cancelSubmit(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}
