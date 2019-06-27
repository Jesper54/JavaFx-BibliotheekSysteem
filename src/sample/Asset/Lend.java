package sample.Asset;

public class Lend {
    private String Id;
    private String Name;
    private String Stock;
    private String Username;
    private String StartDate;
    private String EndDate;
    private String Fine;

    public Lend (String id, String name, String stock, String username, String startDate, String endDate, String fine) {
        Id = id;
        Name = name;
        Stock = stock;
        Username = username;
        StartDate = startDate;
        EndDate = endDate;
        Fine = fine;
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

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getFine() {
        return Fine;
    }
}
