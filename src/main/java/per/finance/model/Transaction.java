package per.finance.model;

public class Transaction {
    private int id;
    private String date;
    private String type;     
    private String category;
    private String note;    
    private double amount;
    private String imagePath;

    // Constructor đầy đủ tham số
    public Transaction(int id, String date, String type, String category, String note, double amount, String imagePath) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.note = note;
        this.amount = amount;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getNote() { return note; }
    public double getAmount() { return amount; }
    public String getImagePath() { return imagePath; }
}