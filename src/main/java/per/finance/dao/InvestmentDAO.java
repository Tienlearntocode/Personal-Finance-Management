package per.finance.dao;

import per.finance.config.DatabaseConnection;
import per.finance.model.Investment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentDAO {

    // Lấy toàn bộ danh sách khoản đầu tư
    public List<Investment> getAllInvestments() {
        List<Investment> list = new ArrayList<>();
        String sql = "SELECT * FROM investments";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Investment(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("initial_amount"),
                    rs.getDouble("current_amount"),
                    rs.getDate("investment_date"),
                    rs.getDouble("return_rate")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm khoản đầu tư
    public boolean insertInvestment(Investment inv) {
        String sql = "INSERT INTO investments "
                + "(name, type, initial_amount, current_amount, "
                + "investment_date, return_rate) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inv.getName());
            pstmt.setString(2, inv.getType());
            pstmt.setDouble(3, inv.getInitialAmount());
            pstmt.setDouble(4, inv.getCurrentAmount());

            if (inv.getInvestmentDate() != null) {
                pstmt.setDate(
                    5,
                    new java.sql.Date(inv.getInvestmentDate().getTime())
                );
            } else {
                pstmt.setNull(5, Types.DATE);
            }

            pstmt.setDouble(6, inv.getReturnRate());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật khoản đầu tư
    public boolean updateInvestment(Investment inv) {
        String sql = "UPDATE investments SET "
                + "name = ?, "
                + "type = ?, "
                + "initial_amount = ?, "
                + "current_amount = ?, "
                + "investment_date = ?, "
                + "return_rate = ? "
                + "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inv.getName());
            pstmt.setString(2, inv.getType());
            pstmt.setDouble(3, inv.getInitialAmount());
            pstmt.setDouble(4, inv.getCurrentAmount());

            if (inv.getInvestmentDate() != null) {
                pstmt.setDate(
                    5,
                    new java.sql.Date(inv.getInvestmentDate().getTime())
                );
            } else {
                pstmt.setNull(5, Types.DATE);
            }

            pstmt.setDouble(6, inv.getReturnRate());
            pstmt.setInt(7, inv.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khoản đầu tư
    public boolean deleteInvestment(int id) {
        String sql = "DELETE FROM investments WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}