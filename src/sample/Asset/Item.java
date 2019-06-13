package sample.Asset;

public class Item {
    private String Id;
    private String Name;
    private String Author;
    private String Category;
    private String Description;
    private String Stock;

    public Item (String id, String name, String author, String category, String description, String stock) {
        Id = id;
        Name = name;
        Author = author;
        Category = category;
        Description = description;
        Stock = stock;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getAuthor() { return Author; }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getStock() {
        return Stock;
    }
}
