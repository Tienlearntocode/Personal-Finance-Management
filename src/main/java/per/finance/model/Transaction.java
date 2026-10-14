package per.finance.model;

public class Transaction {
    private int id;
    private String date;
    private String type;     // "Thu" hoặc "Chi"
    private String category;
    private double amount;

    public Transaction(int id, String date, String type, String category, double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
}