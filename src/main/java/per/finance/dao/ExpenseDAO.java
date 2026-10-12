/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import per.finance.config.DatabaseConnection;
import per.finance.model.Expense;
public class ExpenseDAO {
    public static final String TABLE_NAME="expense";

    public static int insert(Expense t){
           int id=-1;
           String sql="INSERT INTO " +TABLE_NAME+" (amount,category,note,expense_date,image_path) VALUES(?,?,?,?,?)";
           try {
               Connection conn=DatabaseConnection.getConnection();
               PreparedStatement ps= conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
               ps.setDouble(1, t.getAmount());
               ps.setString(2, t.getCategory());
               ps.setString(3, t.getNote());
               ps.setDate(4, new java.sql.Date(t.getExpenseDate().getTime()));
               ps.setString(5, t.getImagePath());
               int add=ps.executeUpdate();
               if(add>0){
                   try(ResultSet rs=ps.getGeneratedKeys()) {
                       if(rs.next()){
                           id=rs.getInt(1);
                       }
                   } catch (Exception e) {
                   }
               }
        } catch (SQLException e) {
            e.printStackTrace();
        }
           return id;
    }
}
