package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import sample.Asset.DatabaseConnection;

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

        // FILTER MAKEN!!!!!
        Integer Stock = Integer.parseInt(createStock.getText());

            try {

                if (Title.isEmpty() && Author.isEmpty() && Stock.toString().isEmpty() && Description.isEmpty()) {
                    System.out.println("Please fill in every textfield!");

                } else {
                    Statement stmt = DatabaseConnection.conn.createStatement();
                    stmt.executeUpdate("insert into items (name,author,category,description,stock)VALUES('" + Title + "','" + Author + "','" + Category + "','" + Description + "','" + Stock + "')");
                }
            } catch (Exception e) {
                e.getMessage();
                System.out.println("Inserting into database failed!");
            }

        }

    @FXML
    private void cancelSubmit(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}
