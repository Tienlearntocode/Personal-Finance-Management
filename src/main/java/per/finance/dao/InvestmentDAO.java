package per.finance.dao;

import per.finance.config.DatabaseConnection;
import per.finance.model.Investment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentDAO {
    
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

    public boolean insertInvestment(Investment inv) {
        String sql = "INSERT INTO investments (name, type, initial_amount, current_amount, investment_date, return_rate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inv.getName());
            pstmt.setString(2, inv.getType());
            pstmt.setDouble(3, inv.getInitialAmount());
            pstmt.setDouble(4, inv.getCurrentAmount());
            pstmt.setDate(5, new java.sql.Date(inv.getInvestmentDate().getTime()));
            pstmt.setDouble(6, inv.getReturnRate());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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