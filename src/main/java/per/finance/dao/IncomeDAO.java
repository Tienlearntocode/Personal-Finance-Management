package per.finance.dao;

import per.finance.model.Income;
import per.finance.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAO {

    // 1. Thêm khoản thu mới
    public boolean addIncome(Income income) {
        String sql = "INSERT INTO Income (source_name, amount, income_date, category_id, note) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, income.getSourceName());
            pstmt.setDouble(2, income.getAmount());
            
            pstmt.setDate(3, income.getIncomeDate());
            
            pstmt.setInt(4, income.getCategoryId());
            pstmt.setString(5, income.getNote());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Lấy danh sách toàn bộ khoản thu để đổ lên JTable
    public List<Income> getAllIncomes() {
        List<Income> list = new ArrayList<>();
        String sql = "SELECT income_id, source_name, amount, income_date, category_id, note "
                   + "FROM Income ORDER BY income_date DESC, income_id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Income income = new Income(
                    rs.getInt("income_id"),
                    rs.getString("source_name"),
                    rs.getDouble("amount"),
                    rs.getDate("income_date"),
                    rs.getInt("category_id"),
                    rs.getString("note")
                );
                list.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Cập nhật (Sửa) thông tin khoản thu
    public boolean updateIncome(Income income) {
        String sql = "UPDATE Income SET source_name = ?, amount = ?, income_date = ?, category_id = ?, note = ? WHERE income_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, income.getSourceName());
            pstmt.setDouble(2, income.getAmount());
            
            pstmt.setDate(3, income.getIncomeDate());
            
            pstmt.setInt(4, income.getCategoryId());
            pstmt.setString(5, income.getNote());
            pstmt.setInt(6, income.getIncomeId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Xóa khoản thu theo ID
    public boolean deleteIncome(int incomeId) {
        String sql = "DELETE FROM Income WHERE income_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, incomeId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}