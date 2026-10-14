/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.dao;


import per.finance.model.MonthlySummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO extends BaseDAO {

    /**
     * Lấy tổng thu nhập trong tháng và năm chỉ định
     */
    public double getTotalIncomeByMonth(int month, int year) {
        double total = 0.0;
        String sql = "SELECT SUM(amount) AS total FROM incomes WHERE MONTH(transaction_date) = ? AND YEAR(transaction_date) = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return total;
    }

    /**
     * Lấy tổng chi tiêu trong tháng và năm chỉ định
     */
    public double getTotalExpenseByMonth(int month, int year) {
        double total = 0.0;
        String sql = "SELECT SUM(amount) AS total FROM expenses WHERE MONTH(transaction_date) = ? AND YEAR(transaction_date) = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return total;
    }

    /**
     * Trả về đối tượng MonthlySummary tổng hợp toàn bộ thông tin thu/chi/số dư của tháng
     */
    public MonthlySummary getMonthlySummary(int month, int year) {
        double totalIncome = getTotalIncomeByMonth(month, year);
        double totalExpense = getTotalExpenseByMonth(month, year);
        String monthYearStr = String.format("%d-%02d", year, month);

        return new MonthlySummary(monthYearStr, totalIncome, totalExpense);
    }
}