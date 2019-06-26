package sample.Asset;

public class Reserve {
    private String Id;
    private String Name;
    private String Stock;
    private String Username;
    private String Item_Id;

    public Reserve (String id, String item_id, String name, String stock, String username) {
        Id = id;
        Item_Id = item_id;
        Name = name;
        Stock = stock;
        Username = username;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getStock() {
        return Stock;
    }

    public String getUsername() {
        return Username;
    }

    public String getItem_Id() {
        return Item_Id;
    }
}
