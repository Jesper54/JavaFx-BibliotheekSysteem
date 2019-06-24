package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Asset.DatabaseConnection;
import sample.Asset.Item;

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
    @FXML
    private Button SubmitEdit;
    @FXML
    private Button CancelEdit;


    public void GetItems(Integer id){

        try{
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE id = "+ id +"  ");


        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

}
