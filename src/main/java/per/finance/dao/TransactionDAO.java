package per.finance.dao;

import per.finance.config.DatabaseConnection;
import per.finance.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();

        // Ghép bản ghi từ cả 2 bảng expense và incomes, sắp xếp theo ngày giảm dần
        String sql = "SELECT id, DATE_FORMAT(expense_date, '%Y-%m-%d') AS trans_date, 'Chi tiêu' AS trans_type, category, note, amount, image_path "
           + "FROM expense "
           + "UNION ALL "
           + "SELECT income_id AS id, DATE_FORMAT(income_date, '%Y-%m-%d') AS trans_date, 'Thu nhập' AS trans_type, source_name AS category, note, amount, NULL AS image_path "
           + "FROM Income "
           + "ORDER BY trans_date DESC, id DESC";
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

    // Xóa giao dịch: Kiểm tra loại giao dịch hoặc xóa theo ID tương ứng
    public boolean deleteTransaction(int id, String type) {
        String tableName = "Thu nhập".equalsIgnoreCase(type) ? "incomes" : "expense";
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

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
