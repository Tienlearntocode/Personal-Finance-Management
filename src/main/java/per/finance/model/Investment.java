package per.finance.model;

import java.util.Date;

public class Investment {
    private int id;
    private String name;
    private String type;
    private double initialAmount;
    private double currentAmount;
    private Date investmentDate;
    private double returnRate;

    public Investment() {}

    public Investment(int id, String name, String type, double initialAmount, double currentAmount, Date investmentDate, double returnRate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.initialAmount = initialAmount;
        this.currentAmount = currentAmount;
        this.investmentDate = investmentDate;
        this.returnRate = returnRate;
    }

    public Investment(String name, String type, double initialAmount, double currentAmount, Date investmentDate, double returnRate) {
        this.name = name;
        this.type = type;
        this.initialAmount = initialAmount;
        this.currentAmount = currentAmount;
        this.investmentDate = investmentDate;
        this.returnRate = returnRate;
    }

    // Thuật toán tính số tiền Lãi/Lỗ
    public double getProfit() {
        return currentAmount - initialAmount;
    }

    // Thuật toán tính tỷ lệ phần trăm sinh lời (%)
    public double getProfitPercentage() {
        if (initialAmount == 0) return 0;
        return ((currentAmount - initialAmount) / initialAmount) * 100;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getInitialAmount() { return initialAmount; }
    public void setInitialAmount(double initialAmount) { this.initialAmount = initialAmount; }
    public double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; }
    public Date getInvestmentDate() { return investmentDate; }
    public void setInvestmentDate(Date investmentDate) { this.investmentDate = investmentDate; }
    public double getReturnRate() { return returnRate; }
    public void setReturnRate(double returnRate) { this.returnRate = returnRate; }
}