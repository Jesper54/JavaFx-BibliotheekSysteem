package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Asset.*;

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
    private TableView<Member> MemberTable;
    @FXML
    private TableColumn MemberId;
    @FXML
    private TableColumn MemberName;
    @FXML
    private TableColumn MemberEmail;
    @FXML
    private TableColumn MemberRole;

    @FXML
    private Tab AddMemberTab;
    @FXML
    private Tab ReserveTab;
    @FXML
    private Tab LendOutTab;
    @FXML
    private Button addUser;
    @FXML
    private Button addProduct;
    @FXML
    private Button editProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button HomeRentProduct;

    @FXML
    private TableView<Reserve> ReserveTable;
    @FXML
    private TableColumn ReserveId;
    @FXML
    private TableColumn ReserveTitle;
    @FXML
    private TableColumn ReserveStock;
    @FXML
    private TableColumn ReserveUser;


    public static String ProductId;

    public static String UserId;

    public static String MemberUserId;

    @FXML
    void initialize()
    {
        String role = User.getRole();
        System.out.println(role);

        if(role.equals("member")){
            addUser.setVisible(false);
            addProduct.setVisible(false);
            editProduct.setVisible(false);
            deleteProduct.setVisible(false);
            AddMemberTab.setDisable(true);
            ReserveTab.setDisable(true);
            LendOutTab.setDisable(true);
        }

        homeWelcome.setText("Welcome " + User.getName());

        UserId = User.getId();

        //HOMETABLE
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

        //MEMBERTABLE
        try {
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users");

            while(res.next()) {
                MemberTable.getItems().addAll(new Member(res.getString("id"),
                        res.getString("rol"),
                        res.getString("username"),
                        res.getString("email")));
            }
            MemberId.setCellValueFactory(new PropertyValueFactory<Member, String>("Id"));
            MemberRole.setCellValueFactory(new PropertyValueFactory<Member, String>("Rol"));
            MemberName.setCellValueFactory(new PropertyValueFactory<Member, String>("Username"));
            MemberEmail.setCellValueFactory(new PropertyValueFactory<Member, String>("Email"));
        }
        catch (SQLException e)
        {
            e.getMessage();
        }

        //RESERVETABLE
        try {
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from lend INNER JOIN items ON lend.item_id = items.id INNER JOIN users ON lend.user_id = users.id");
            while (result.next()) {
                ReserveTable.getItems().addAll(new Reserve(result.getString("id"),
                        result.getString("name"),
                        result.getString("stock"),
                        result.getString("username")
                        ));
            }
            ReserveId.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Id"));
            ReserveTitle.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Name"));
            ReserveUser.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Username"));
            ReserveStock.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Stock"));
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void logoutSubmit(ActionEvent event)
    {
        new newScreenController().setScreen("../View/loginScreen.fxml");
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void addUserSubmit(ActionEvent event){
        new newScreenController().setScreen("../View/addUserScreen.fxml");
    }

    @FXML
    private void overviewSubmit(ActionEvent event) {
        ReserveTable.getItems().clear();
        MemberTable.getItems().clear();
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
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    // Reserveren 0, Uitgeleend 1, Teruggebracht 2.
    private void HomeReserveItem(ActionEvent event)
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
                //RESERVEREN
                ProductId = item.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("insert into lend (item_id,user_id,status)VALUES('" + ProductId + "','" + UserId + "','" + "0" + "')");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Reserveren geslaagd!");
                alert.setContentText("Loop naar de balie medewerker om uw product op te halen");
                alert.showAndWait();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void EditUserSubmit(ActionEvent event)
    {
        Member member = MemberTable.getSelectionModel().getSelectedItem();

        if (member == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setContentText("Please select a table row!");
            alert.showAndWait();
        }
        else
        {
            MemberUserId = member.getId();
            new newScreenController().setScreen("../View/editMemberScreen.fxml");
        }
    }

    @FXML
    private void RefreshUserSubmit(ActionEvent event){
        ReserveTable.getItems().clear();
        MemberTable.getItems().clear();
        homeTable.getItems().clear();
        initialize();
    }

    @FXML
    private void deleteUserSubmit(ActionEvent event){
        Member member = MemberTable.getSelectionModel().getSelectedItem();

        if (member == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setContentText("Please select a table row!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                UserId = member.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM users WHERE id = "+ UserId + "");
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(member.getUsername() + " deleted!");
                alert.showAndWait();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
