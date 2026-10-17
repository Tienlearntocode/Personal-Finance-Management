package per.finance.dao;

import per.finance.model.MonthlySummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DashboardDAO extends BaseDAO {

    public double getTotalIncomeByMonth(int month, int year) {
        double total = 0.0;
        // Chú ý: Bảng 'Income' và cột 'income_date' khớp với schema.sql của bạn
        String sql = "SELECT SUM(amount) AS total FROM Income WHERE MONTH(income_date) = ? AND YEAR(income_date) = ?";

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
            System.err.println("Lỗi truy vấn tổng thu nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }

    public double getTotalExpenseByMonth(int month, int year) {
        double total = 0.0;
        // Chú ý: Bảng 'expense' và cột 'expense_date' khớp với schema.sql của bạn
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

    public MonthlySummary getMonthlySummary(int month, int year) {
        double totalIncome = getTotalIncomeByMonth(month, year);
        double totalExpense = getTotalExpenseByMonth(month, year);
        // In trực tiếp ra console để kiểm tra giá trị SQL trả về
        System.out.println("DEBUG SQL -> Tháng: " + month + ", Năm: " + year + " | Tổng Thu: " + totalIncome + " | Tổng Chi: " + totalExpense);

        String monthYearStr = String.format("%d-%02d", year, month);
        return new MonthlySummary(monthYearStr, totalIncome, totalExpense);
    }
    
    public static class CategorySummary {
        private String categoryName;
        private double totalAmount;
        private double percentage;

        public CategorySummary(String categoryName, double totalAmount, double percentage) {
            this.categoryName = categoryName;
            this.totalAmount = totalAmount;
            this.percentage = percentage;
        }

        public String getCategoryName() { return categoryName; }
        public double getTotalAmount() { return totalAmount; }
        public double percentage() { return percentage; }
    }

    /**
     * Lấy danh sách chi tiêu gom theo từng danh mục trong tháng/năm kèm tỷ lệ phần trăm
     */
    public List<CategorySummary> getCategorySummaryByMonth(int month, int year) {
        List<CategorySummary> list = new ArrayList<>();
        double totalExpense = getTotalExpenseByMonth(month, year);

        // Câu lệnh SQL nhóm theo category và tính tổng tiền
        String sql = "SELECT category, SUM(amount) AS total FROM expense " +
                     "WHERE MONTH(expense_date) = ? AND YEAR(expense_date) = ? " +
                     "GROUP BY category ORDER BY total DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String category = rs.getString("category");
                    double amount = rs.getDouble("total");
                    double percentage = (totalExpense > 0) ? (amount / totalExpense) * 100 : 0.0;

                    list.add(new CategorySummary(category, amount, percentage));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}