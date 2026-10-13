package per.finance.dao;

import per.finance.config.DatabaseConnection;
import per.finance.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    // Lấy danh sách giao dịch từ MySQL
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Transaction(
                    rs.getInt("id"),
                    rs.getString("date"),
                    rs.getString("type"),
                    rs.getString("category"),
                    rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa giao dịch theo ID
    public boolean deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}