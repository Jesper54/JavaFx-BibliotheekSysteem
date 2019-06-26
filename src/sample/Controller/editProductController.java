package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import sample.Asset.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class editProductController {

    @FXML
    private TextField editTitle;
    @FXML
    private TextField editAuthor;
    @FXML
    private TextField editStock;
    @FXML
    private ChoiceBox editCat;
    @FXML
    private TextArea editDesc;



    public void initialize()
    {
        System.out.println("Product id : " + homeScreenController.ProductId);
        String Id = homeScreenController.ProductId;

        try
        {
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE id = " + Id );

            while(rs.next()){
               String title = rs.getString(2);
               String author = rs.getString(3);
               String category = rs.getString(4);
               String description = rs.getString(5);
               String stock = rs.getString(6);

               editTitle.setText(title);
               editAuthor.setText(author);
               editCat.setItems(FXCollections.observableArrayList(" "+ category + " ", "Book", "E-book", "Dvd", "Cd", "Magazine"));
               editCat.getSelectionModel().selectFirst();
               editDesc.setText(description);
               editStock.setText(stock);

            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void SubmitEditNew(ActionEvent event) {
        String NewTitle = editTitle.getText();
        String NewAuthor = editAuthor.getText();
        String NewCat = editCat.getValue().toString();
        String NewDesc = editDesc.getText();
        String NewStock = editStock.getText();

        if (NewStock.matches("[0-9]+")) {
            try {
                String Id = homeScreenController.ProductId;
                if (NewTitle.isEmpty() && NewAuthor.isEmpty() && NewDesc.isEmpty() && NewStock.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText("Please fill in every textfield");
                    alert.showAndWait();

                } else {

                    Statement stmt = DatabaseConnection.conn.createStatement();
                    stmt.executeUpdate("UPDATE items SET name = "+ "'" + NewTitle + "'" +", author = "+ "'" + NewAuthor + "'" +", category = "+ "'" + NewCat + "'" +", description = "+ "'" + NewDesc + "'" +", stock = "+ "'" + NewStock + "'" +" WHERE id = "+ "'" + Id + "'" +" ");

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Product Updated!");
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
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Please enter only numbers at the stock textfield");
            alert.showAndWait();
        }
    }

    public void CancelEditNew(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
