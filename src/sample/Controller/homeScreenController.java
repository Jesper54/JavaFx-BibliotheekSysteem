package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Asset.DatabaseConnection;
import sample.Asset.Item;
import sample.Asset.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class homeScreenController {
    @FXML
    private Label homeWelcome;
    @FXML
    private TextField homeInputSearch;
    @FXML
    private TableView<Item> homeTable;
    @FXML
    private TableColumn col_id;
    @FXML
    private TableColumn col_name;
    @FXML
    private TableColumn col_author;
    @FXML
    private TableColumn col_description;
    @FXML
    private TableColumn col_category;
    @FXML
    private TableColumn col_stock;
    @FXML
    private Tab AddMemberTab;
    @FXML
    private TabPane TabPane;

    @FXML
    private Button addUser;
    @FXML
    private Button addProduct;
    @FXML
    private Button editProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button homeRentProduct;

    public static String ProductId;

    @FXML
    void initialize()
    {
        String role = User.getRole();
        if(role == "member"){
            addUser.setVisible(false);
            addProduct.setVisible(false);
            editProduct.setVisible(false);
            deleteProduct.setVisible(false);
            homeRentProduct.setVisible(false);
            AddMemberTab.isDisabled();
        }

        homeWelcome.setText("Welcome " + User.getName());


        try {
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items");

            while(rs.next()) {
                homeTable.getItems().addAll(new Item(rs.getString("id"),
                                                     rs.getString("name"),
                                                     rs.getString("author"),
                                                     rs.getString("category"),
                                                     rs.getString("description"),
                                                     rs.getString("stock")));
            }
            col_id.setCellValueFactory(new PropertyValueFactory<Item, String>("Id"));
            col_name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
            col_author.setCellValueFactory(new PropertyValueFactory<Item, String>("Author"));
            col_category.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
            col_description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
            col_stock.setCellValueFactory(new PropertyValueFactory<Item, String>("Stock"));
        }
        catch (SQLException e)
        {
            e.getMessage();
        }
    }

    @FXML
    private void logoutSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/loginScreen.fxml");
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void addSubmit(ActionEvent event){
        new newScreenController().setScreen("../View/addUserScreen.fxml");
    }

    @FXML
    private void overviewSubmit(ActionEvent event) {
        homeTable.getItems().clear();
        initialize();
    }

    @FXML //Zoek functie
    private void searchSubmit(ActionEvent event)
    {
        try
        {
            String search = homeInputSearch.getText();
            homeTable.getItems().clear();

            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE name LIKE '%"+ search +"%'  ");

            while(rs.next()) {
                homeTable.getItems().addAll(new Item(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("stock")));

            }
            col_id.setCellValueFactory(new PropertyValueFactory<Item, String>("Id"));
            col_name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
            col_author.setCellValueFactory(new PropertyValueFactory<Item, String>("Author"));
            col_category.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
            col_description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
            col_stock.setCellValueFactory(new PropertyValueFactory<Item, String>("Stock"));
        }
        catch (SQLException sqle)
        {
            sqle.getMessage();
        }
    }

    @FXML
    private void addProductSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/addProductScreen.fxml");
    }

    @FXML
    public void editProductSubmit(ActionEvent event)
    {
        Item item = homeTable.getSelectionModel().getSelectedItem();

        if (item == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setContentText("Please select a table row!");
            alert.showAndWait();
        }
        else
            {
                ProductId = item.getId();
                new newScreenController().setScreen("../View/editProductScreen.fxml");
            }
    }

    @FXML
    private void deleteProductSubmit(ActionEvent event)
    {
        Item item = homeTable.getSelectionModel().getSelectedItem();

        if (item == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setContentText("Please select a table row!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                ProductId = item.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM items WHERE id = "+ ProductId + "");
                homeTable.getItems().clear();
                initialize();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void homeRentSubmit(ActionEvent event)
    {

    }

    @FXML
    private void  homeHandSubmit(ActionEvent event)
    {

    }


}