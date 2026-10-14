package per.finance.model;

import java.sql.Date;

public class Income {
    private int incomeId;
    private String sourceName;
    private double amount;
    private Date incomeDate;
    private int categoryId;
    private String note;

    public Income() {}

    public Income(int incomeId, String sourceName, double amount, Date incomeDate, int categoryId, String note) {
        this.incomeId = incomeId;
        this.sourceName = sourceName;
        this.amount = amount;
        this.incomeDate = incomeDate;
        this.categoryId = categoryId;
        this.note = note;
    }

    public int getIncomeId() { return incomeId; }
    public void setIncomeId(int incomeId) { this.incomeId = incomeId; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getIncomeDate() { return incomeDate; }
    public void setIncomeDate(Date incomeDate) { this.incomeDate = incomeDate; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}