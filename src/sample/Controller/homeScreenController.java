package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Asset.DatabaseConnection;
import sample.Asset.Item;
import sample.Asset.User;

import javax.management.relation.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class homeScreenController {
    @FXML
    private Label homeWelcome;
    @FXML
    private TextField homeInputSearch;
    @FXML
    private TableView homeTable;
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
    private Button addUser;
    @FXML
    private Button addProduct;
    @FXML
    private Button editProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button homeRentProduct;


    @FXML
    private void initialize()
    {
        String role = User.getRole();
        if(role == "member"){
            addUser.setVisible(false);
            addProduct.setVisible(false);
            editProduct.setVisible(false);
            deleteProduct.setVisible(false);
            homeRentProduct.setVisible(false);
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
    private void editProductSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/editProductScreen.fxml");
    }

    @FXML
    private void deleteProductSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/deleteProductScreen.fxml");
    }
}