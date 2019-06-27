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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    private TabPane TabPane;
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
    private TextField MemberInputSearch;
    @FXML
    private TextField ReserveInputSearch;
    @FXML
    private TextField LendInputSearch;

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
    @FXML
    private TableColumn ReserveItemId;

    @FXML
    private TableView<Lend> LendTable;
    @FXML
    private TableColumn LendId;
    @FXML
    private TableColumn LendTitle;
    @FXML
    private TableColumn LendStock;
    @FXML
    private TableColumn LendUsername;
    @FXML
    private TableColumn LendStartDate;
    @FXML
    private TableColumn LendEndDate;
    @FXML
    private TableColumn LendFine;


    public static String ProductId;

    public static String UserId;

    public static String MemberUserId;

    public static String ReserveProductId;

    public static String ReserveProductItemId;

    public static String LendRemoveId;

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
            TabPane.getTabs().remove(AddMemberTab);
            TabPane.getTabs().remove(ReserveTab);
            TabPane.getTabs().remove(LendOutTab);
        }

        homeWelcome.setText("Welkom " + User.getName());

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
        catch (SQLException e) {
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
            ResultSet result = stmt.executeQuery("SELECT * from lend INNER JOIN items ON lend.item_id = items.id INNER JOIN users ON lend.user_id = users.id WHERE lend.status = 0");
            while (result.next()) {
                ReserveTable.getItems().addAll(new Reserve(result.getString("id"),
                        result.getString("item_id"),
                        result.getString("name"),
                        result.getString("stock"),
                        result.getString("username")
                        ));
            }
           ReserveId.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Id"));
           ReserveItemId.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Item_Id"));
           ReserveTitle.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Name"));
           ReserveStock.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Stock"));
           ReserveUser.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Username"));
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        //LENDTABLE
        try{
            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from lend INNER JOIN items ON lend.item_id = items.id INNER JOIN users ON lend.user_id = users.id WHERE lend.status = 1");
            while (rs.next()) {

                // Boete Berekenen via datum
                SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String EndDate = rs.getString("enddate");
                LocalDate localDate = LocalDate.now();
                Date CurrentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                try {
                    Date date1 = DateFormat.parse(EndDate);
                    Date date2 = CurrentDate;
                    long Difference = date2.getTime() - date1.getTime();
                    long Converted = (TimeUnit.DAYS.convert(Difference, TimeUnit.MILLISECONDS));

                    // Converten van long naar integer voor berekening
                    String Converted1 = String.valueOf(Converted);
                    Integer ConvertedDate = Integer.valueOf(Converted1);

                    // Kijken of hij telaat is en hoeveel euro boete hij heeft
                    if (ConvertedDate > 0){
                        Integer Fine = ConvertedDate;
                        Statement stmt1 = DatabaseConnection.conn.createStatement();
                        stmt1.executeUpdate("UPDATE lend SET fine =  "+ "'" + "€"+ConvertedDate+",-" + "'" +" WHERE id = "+ rs.getString("id") +" ");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                LendTable.getItems().addAll(new Lend(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("stock"),
                        rs.getString("username"),
                        rs.getString("startdate"),
                        rs.getString("enddate"),
                        rs.getString("fine")
                        ));
            }
            LendId.setCellValueFactory(new PropertyValueFactory<Lend, String>("Id"));
            LendTitle.setCellValueFactory(new  PropertyValueFactory<Lend, String>("Name"));
            LendStock.setCellValueFactory(new PropertyValueFactory<Lend, String>("Stock"));
            LendUsername.setCellValueFactory(new PropertyValueFactory<Lend, String>("Username"));
            LendStartDate.setCellValueFactory(new PropertyValueFactory<Lend, String>("StartDate"));
            LendEndDate.setCellValueFactory(new PropertyValueFactory<Lend, String>("EndDate"));
            LendFine.setCellValueFactory(new PropertyValueFactory<Lend, String>("Fine"));

        }
        catch (SQLException e) {
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
        LendTable.getItems().clear();
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
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
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
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                ProductId = item.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM items WHERE id = "+ ProductId + "");
                Statement stmt2 = DatabaseConnection.conn.createStatement();
                stmt2.executeUpdate("DELETE FROM lend WHERE item_id = "+ ProductId + "");

                LendTable.getItems().clear();
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gelukt!");
                alert.setHeaderText("Product verwijderd");
                alert.setContentText("Artikel: "+ item.getName());
                alert.showAndWait();
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
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                //RESERVEREN
                ProductId = item.getId();

                Integer checkStock = Integer.parseInt(item.getStock());

                if(checkStock < 1)
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Niet gelukt!");
                    alert.setTitle("Product niet op voorraad!");
                    alert.setContentText("U kunt het product niet reserveren, wacht totdat het product weer op voorraad is!");
                    alert.showAndWait();
                }
                else
                {
                    Statement stmt = DatabaseConnection.conn.createStatement();
                    stmt.executeUpdate("insert into lend (item_id,user_id,status)VALUES('" + ProductId + "','" + UserId + "','" + "0" + "')");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Gelukt!");
                    alert.setHeaderText("Reserveren geslaagd!");
                    alert.setContentText("Loop naar de balie medewerker om uw product op te halen");
                    alert.showAndWait();

                    LendTable.getItems().clear();
                    ReserveTable.getItems().clear();
                    homeTable.getItems().clear();
                    MemberTable.getItems().clear();
                    initialize();
                }
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
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
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
        LendTable.getItems().clear();
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
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                UserId = member.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM users WHERE id = "+ UserId + "");
                LendTable.getItems().clear();
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gelukt!");
                alert.setHeaderText(member.getUsername() + " deleted!");
                alert.showAndWait();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void ReserveDeleteSubmit(ActionEvent event){

        Reserve reserve = ReserveTable.getSelectionModel().getSelectedItem();

        if (reserve == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                ReserveProductId = reserve.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM lend WHERE id = "+ ReserveProductId + "");
                LendTable.getItems().clear();
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gelukt");
                alert.setHeaderText(reserve.getName() + " reservering verwijderd!");
                alert.showAndWait();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void RentProduct(ActionEvent event){
        Reserve reserve = ReserveTable.getSelectionModel().getSelectedItem();

        if (reserve == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                ReserveProductId = reserve.getId();
                ReserveProductItemId = reserve.getItem_Id();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("UPDATE lend, items SET lend.status = 1, lend.startdate = CURRENT_DATE, lend.enddate = CURRENT_DATE + INTERVAL 7 DAY, items.stock = items.stock -1 WHERE lend.id = "+ ReserveProductId +" AND items.id = "+ ReserveProductItemId +"");
                LendTable.getItems().clear();
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gelukt!");
                alert.setHeaderText("Product Uitgeleend");
                alert.setHeaderText(reserve.getName()+ " is uitgeleend");
                alert.setContentText("");
                alert.showAndWait();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void HandInProduct(ActionEvent event){
        Lend lend = LendTable.getSelectionModel().getSelectedItem();

        if (lend == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                String Fine = lend.getFine();

                LendRemoveId = lend.getId();
                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM lend WHERE id = "+ LendRemoveId +"");
                Statement stmt1 = DatabaseConnection.conn.createStatement();
                stmt1.executeUpdate("UPDATE items SET stock = stock + 1");
                LendTable.getItems().clear();
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                if(Fine.equals("Geen Boete")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product Ingeleverd");
                    alert.setHeaderText("Product succesvol terug gebracht");
                    alert.setContentText("Artikel: " + lend.getName());
                    alert.showAndWait();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product Ingeleverd");
                    alert.setHeaderText("Product succesvol terug gebracht");
                    alert.setContentText(lend.getUsername() + " moet nog een boete betalen! "+lend.getFine() +"\nArtikel: " + lend.getName());
                    alert.showAndWait();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void MemberSearch(ActionEvent event)
    {
        try
        {
            String search = MemberInputSearch.getText();
            MemberTable.getItems().clear();

            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username LIKE '%"+ search +"%'  ");

            while(rs.next()) {
                MemberTable.getItems().addAll(new Member(rs.getString("id"),
                        rs.getString("rol"),
                        rs.getString("username"),
                        rs.getString("email")));
            }
            MemberId.setCellValueFactory(new PropertyValueFactory<Member, String>("Id"));
            MemberRole.setCellValueFactory(new PropertyValueFactory<Member, String>("Rol"));
            MemberName.setCellValueFactory(new PropertyValueFactory<Member, String>("Username"));
            MemberEmail.setCellValueFactory(new PropertyValueFactory<Member, String>("Email"));
        }
        catch (SQLException sqle)
        {
            sqle.getMessage();
        }
    }

    @FXML
    private void ReserveSearch(ActionEvent event)
    {
        try
        {
            String search = ReserveInputSearch.getText();
            ReserveTable.getItems().clear();

            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from lend INNER JOIN items ON lend.item_id = items.id INNER JOIN users ON lend.user_id = users.id WHERE lend.status = 0 AND users.username LIKE '%"+ search +"%'  ");

            while (rs.next()) {
                ReserveTable.getItems().addAll(new Reserve(rs.getString("id"),
                        rs.getString("item_id"),
                        rs.getString("name"),
                        rs.getString("stock"),
                        rs.getString("username")
                ));
            }
            ReserveId.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Id"));
            ReserveItemId.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Item_Id"));
            ReserveTitle.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Name"));
            ReserveStock.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Stock"));
            ReserveUser.setCellValueFactory(new PropertyValueFactory<Reserve, String>("Username"));
        }
        catch (SQLException sqle)
        {
            sqle.getMessage();
        }
    }

    @FXML
    private void LendSearch(ActionEvent event)
    {
        try {
            String search = LendInputSearch.getText();
            LendTable.getItems().clear();

            Statement stmt = DatabaseConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from lend INNER JOIN items ON lend.item_id = items.id INNER JOIN users ON lend.user_id = users.id WHERE lend.status = 1 AND users.username LIKE '%" + search + "%'  ");

            while(rs.next()) {
                LendTable.getItems().addAll(new Lend(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("stock"),
                        rs.getString("username"),
                        rs.getString("startdate"),
                        rs.getString("enddate"),
                        rs.getString("fine")
                ));
            }
            LendId.setCellValueFactory(new PropertyValueFactory<Lend, String>("Id"));
            LendTitle.setCellValueFactory(new  PropertyValueFactory<Lend, String>("Name"));
            LendStock.setCellValueFactory(new PropertyValueFactory<Lend, String>("Stock"));
            LendUsername.setCellValueFactory(new PropertyValueFactory<Lend, String>("Username"));
            LendStartDate.setCellValueFactory(new PropertyValueFactory<Lend, String>("StartDate"));
            LendEndDate.setCellValueFactory(new PropertyValueFactory<Lend, String>("EndDate"));
            LendFine.setCellValueFactory(new PropertyValueFactory<Lend, String>("Fine"));
        }
        catch (SQLException sqle)
        {
            sqle.getMessage();
        }
    }

    @FXML
    private void DeleteHandInProduct(ActionEvent even)
    {
        Lend lend = LendTable.getSelectionModel().getSelectedItem();
        LendRemoveId = lend.getId();
        if (lend == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Geen rij");
            alert.setHeaderText("Geen rij gevonden");
            alert.setContentText("Selecteer een rij!");
            alert.showAndWait();
        }
        else
        {
            try
            {

                Statement stmt = DatabaseConnection.conn.createStatement();
                stmt.executeUpdate("DELETE FROM lend WHERE id = "+ LendRemoveId +"");
                LendTable.getItems().clear();
                ReserveTable.getItems().clear();
                homeTable.getItems().clear();
                MemberTable.getItems().clear();
                initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gelukt!");
                alert.setHeaderText("Product Verwijderd");
                alert.setContentText("Dit product is verwijderd omdat het kapot is\nof omdat hij niet meer is ingeleverd.");
                alert.showAndWait();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
