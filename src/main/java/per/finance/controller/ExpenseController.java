/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.controller;

import java.util.Date;
import per.finance.dao.ExpenseDAO;
import per.finance.model.Expense;

/**
 *
 * @author Long
 */
public class ExpenseController {
    private final ExpenseDAO expenseDAO;
    public ExpenseController(){
        this.expenseDAO=new ExpenseDAO();
    }
    public String saveExpense(String strAmount,String category,String note,Date date,String imagePath) {
        ///kiểm tra số tiền rỗng hay ko
        if(strAmount==null || strAmount.trim().isEmpty()){
            return "Vui lòng nhập lại tiền!";
        }
        
        double amount;
        try {
            amount=Double.parseDouble(strAmount.trim());
            
        } catch (NumberFormatException e) {
            return "số tiền phải hợp lệ!";
        }
        if(amount<0){
           return "số tiền phải lớn 0";
        }
        if(date==null){
           return "vui lòng chọn ngày";
        }
        java.sql.Date sqldate = new java.sql.Date(date.getTime());
        Expense exp= new Expense(amount, category, note, sqldate, imagePath);
        int newid= ExpenseDAO.insert(exp);
        if(newid<=0){
            return"DỮ LIỆU KHÔNG LƯU ĐƯỢC!!!!!!!!!";
        }
        return "OK";
    }
    
}
