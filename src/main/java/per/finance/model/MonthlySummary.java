/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.model;


public class MonthlySummary {
    private String monthYear;   // Tháng/Năm (ví dụ: "2026-10")
    private double totalIncome;  // Tổng thu trong tháng
    private double totalExpense; // Tổng chi trong tháng
    private double netBalance;   // Số dư (Thu - Chi)

    // Constructor không tham số
    public MonthlySummary() {
    }

    // Constructor đầy đủ tham số
    public MonthlySummary(String monthYear, double totalIncome, double totalExpense) {
        this.monthYear = monthYear;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = totalIncome - totalExpense; // Tự động tính số dư
    }

    // Getters và Setters
    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
        this.netBalance = this.totalIncome - this.totalExpense;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
        this.netBalance = this.totalIncome - this.totalExpense;
    }

    public double getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }

    @Override
    public String toString() {
        return "MonthlySummary{" +
                "monthYear='" + monthYear + '\'' +
                ", totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                ", netBalance=" + netBalance +
                '}';
    }
}