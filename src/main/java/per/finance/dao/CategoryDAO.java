/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import per.finance.model.Category;
import per.finance.config.DatabaseConnection;
/**
 *
 * @author Long
 */
public class CategoryDAO {
    
    // lấy all danh mục 
    public List<Category> getAll(){
        List<Category> list= new ArrayList<>();
        String sql="Select * from category ORDER BY name ASC";
        try {
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                list.add(new Category(rs.getInt("id"),rs.getString("name")));
            }
            
        } catch (Exception e) {
        }
        return list;
    }
    //// chèn
    public Category insert(Category c){
        String sql="INSERT INTO category (name) values (?)";
        try {
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getName());
            ///return generated keys nó lấy số id từ mysql
            int run=ps.executeUpdate();
            if(run>0){
                try {
                    ResultSet rs=ps.getGeneratedKeys();
                    if(rs.next()){
                        int generateId=rs.getInt(1);
                        c.setId(generateId);
                        return c;
                    
                }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // update
    public Category update(Category c){
        String sql="UPDATE category SET name = ? where id = ?";
        try {
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1,c.getName());
            ps.setInt(2,c.getId());
            int kq=ps.executeUpdate();
            if(kq>0){
                return c;
            }
                       
        } catch (Exception e) {
            e.printStackTrace();    
            
        }
        return null;
    }
    /// delete
    public boolean  delete(int id){
        String sql="DELETE from category where id = ?";
        try {
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}
