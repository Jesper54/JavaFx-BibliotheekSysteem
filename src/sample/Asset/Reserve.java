package sample.Asset;

public class Reserve {
    private String Id;
    private String Name;
    private String Stock;
    private String Username;

    public Reserve (String id, String name, String stock, String username) {
        Id = id;
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
}
