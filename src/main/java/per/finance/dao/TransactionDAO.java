package per.finance.dao;

import per.finance.config.DatabaseConnection;
import per.finance.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        // Truy vấn thêm cột note từ bảng expense
        String sql = "SELECT id, DATE_FORMAT(expense_date, '%Y-%m-%d') AS trans_date, 'Chi tiêu' AS trans_type, category, note, amount, image_path "
                   + "FROM expense "
                   + "ORDER BY expense_date DESC, id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Transaction(
                    rs.getInt("id"),
                    rs.getString("trans_date"),
                    rs.getString("trans_type"),
                    rs.getString("category"),
                    rs.getString("note") != null ? rs.getString("note") : "",
                    rs.getDouble("amount"),
                    rs.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteTransaction(int id) {
        String sql = "DELETE FROM expense WHERE id = ?";
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