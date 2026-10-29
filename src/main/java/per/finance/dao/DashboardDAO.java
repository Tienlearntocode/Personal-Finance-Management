package per.finance.dao;

import per.finance.model.CategorySummary;
import per.finance.model.MonthlySummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO extends BaseDAO {

    /** Tổng thu nhập của một tháng/năm (bảng Income, cột income_date). */
    public double getTotalIncomeByMonth(int month, int year) {
        double total = 0.0;
        String sql = "SELECT SUM(amount) AS total FROM Income WHERE MONTH(income_date) = ? AND YEAR(income_date) = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total"); // SUM không có dữ liệu trả về NULL, getDouble đổi thành 0
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn tổng thu nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }

    /** Tổng chi tiêu của một tháng/năm (bảng expense, cột expense_date). */
    public double getTotalExpenseByMonth(int month, int year) {
        double total = 0.0;
        String sql = "SELECT SUM(amount) AS total FROM expense WHERE MONTH(expense_date) = ? AND YEAR(expense_date) = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn tổng chi tiêu: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }

    /** Gộp tổng thu và tổng chi của tháng thành một đối tượng MonthlySummary. */
    public MonthlySummary getMonthlySummary(int month, int year) {
        double totalIncome = getTotalIncomeByMonth(month, year);
        double totalExpense = getTotalExpenseByMonth(month, year);

        String monthYearStr = String.format("%d-%02d", year, month);
        return new MonthlySummary(monthYearStr, totalIncome, totalExpense);
    }

    /**
     * Lấy chi tiêu gom theo danh mục trong tháng/năm, sắp xếp từ cao xuống thấp,
     * kèm tỷ lệ phần trăm so với tổng chi của tháng.
     */
    public List<CategorySummary> getCategorySummaryByMonth(int month, int year) {
        List<String> names = new ArrayList<>();
        List<Double> amounts = new ArrayList<>();
        double totalExpense = 0.0;

        String sql = "SELECT category, SUM(amount) AS total FROM expense "
                   + "WHERE MONTH(expense_date) = ? AND YEAR(expense_date) = ? "
                   + "GROUP BY category ORDER BY total DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    double amount = rs.getDouble("total");
                    names.add(rs.getString("category"));
                    amounts.add(amount);
                    totalExpense += amount; // cộng dồn để khỏi phải truy vấn tổng chi lần nữa
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn chi tiêu theo danh mục: " + e.getMessage());
            e.printStackTrace();
        }

        List<CategorySummary> list = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            double amount = amounts.get(i);
            double percentage = (totalExpense > 0) ? (amount / totalExpense) * 100 : 0.0;
            list.add(new CategorySummary(names.get(i), amount, percentage));
        }
        return list;
    }
}