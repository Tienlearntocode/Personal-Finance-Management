/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.model;

import java.util.Date;

public class Expense {
   
    private int id;
    private double amount;
    private String category;
     private String note;
    private Date expenseDate;
    private String imagePath;
    
     public Expense() {

    }
     
     // dùng để thêm mới
    
    

    public Expense(double amount, String category, String note, Date expenseDate, String imagePath) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.expenseDate = expenseDate;
        this.imagePath = imagePath;
    }
    /////  Constructor đầy đủ tham số (dùng khi load từ database lên)

    public Expense(int id, double amount, String category, String note, Date expenseDate, String imagePath) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.expenseDate = expenseDate;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    
}
