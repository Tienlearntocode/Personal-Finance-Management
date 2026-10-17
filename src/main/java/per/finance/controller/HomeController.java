package per.finance.controller;

import per.finance.dao.DashboardDAO;
import per.finance.model.MonthlySummary;
import per.finance.view.HomePanel;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JFrame;
import per.finance.view.ExpenseChartDialog;
public class HomeController {

    private final HomePanel view;
    private final DashboardDAO dashboardDAO;
    private final DecimalFormat currencyFormatter = new DecimalFormat("#,##0 ₫");

    public HomeController(HomePanel view) {
        this.view = view;
        this.dashboardDAO = new DashboardDAO();
        
        initController();
    }

    private void initController() {
        // Lắng nghe sự kiện thay đổi trên JMonthChooser
        if (view.getMonthChooser() != null) {
            view.getMonthChooser().addPropertyChangeListener("month", evt -> loadDashboardData());
        }

        // Lắng nghe sự kiện thay đổi trên JYearChooser
        if (view.getYearChooser() != null) {
            view.getYearChooser().addPropertyChangeListener("year", evt -> loadDashboardData());
        }

        // Nút làm mới (Nút bình thường mới dùng addActionListener)
        if (view.getBtnRefresh() != null) {
            view.getBtnRefresh().addActionListener(e -> loadDashboardData());
        }

        // Gán giá trị mặc định ban đầu là Tháng 10 và Năm 2026
        if (view.getMonthChooser() != null) {
            view.getMonthChooser().setMonth(9); // 9 tương ứng với Tháng 10 (tính từ 0)
        }
        if (view.getYearChooser() != null) {
            view.getYearChooser().setYear(2026);
        }

        // Tải dữ liệu ban đầu
        loadDashboardData();
    }

    public void loadDashboardData() {
        int selectedMonth = LocalDate.now().getMonthValue();
        int selectedYear = LocalDate.now().getYear();

        if (view.getMonthChooser() != null) {
            selectedMonth = view.getMonthChooser().getMonth() + 1; 
        }
        if (view.getYearChooser() != null) {
            selectedYear = view.getYearChooser().getYear();
        }

        // 1. Lấy tổng thu, tổng chi
        MonthlySummary summary = dashboardDAO.getMonthlySummary(selectedMonth, selectedYear);

        // Hiển thị tổng chi tiêu & tổng thu nhập
        if (view.getLblExpenseAmount() != null) {
            view.getLblExpenseAmount().setText(currencyFormatter.format(summary.getTotalExpense()));
        }
        if (view.getLblIncomeAmount() != null) {
            view.getLblIncomeAmount().setText(currencyFormatter.format(summary.getTotalIncome()));
        }
        if (view.getLblNetBalance() != null) {
            view.getLblNetBalance().setText(currencyFormatter.format(summary.getNetBalance()));
        }

        // 2. Lấy dữ liệu danh mục chi tiêu và đổ vào JTable của HomePanel
        List<DashboardDAO.CategorySummary> categoryList = dashboardDAO.getCategorySummaryByMonth(selectedMonth, selectedYear);
        
        // Giả sử bảng trên HomePanel có tên getter là getTblCategory() hoặc getTableCategory()
        // Bạn kiểm tra tên hàm get Table trong HomePanel của bạn (ví dụ: view.getTblCategory())
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) view.getTblCategory().getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trên bảng

        java.text.DecimalFormat percentFormatter = new java.text.DecimalFormat("#0.1'% '");

        for (DashboardDAO.CategorySummary cat : categoryList) {
            Object[] row = new Object[] {
                cat.getCategoryName(),
                currencyFormatter.format(cat.getTotalAmount()),
                percentFormatter.format(cat.percentage())
            };
            model.addRow(row);
        }
        // Thêm đoạn này vào trong hàm initController() của HomeController.java:
        if (view.getBtnViewChart() != null) {
            view.getBtnViewChart().addActionListener(e -> {
                int month = view.getMonthChooser().getMonth() + 1;
                int year = view.getYearChooser().getYear();

                // Lấy danh sách danh mục chi tiêu của tháng/năm đó
                List<DashboardDAO.CategorySummary> list = dashboardDAO.getCategorySummaryByMonth(month, year);

                // Tìm JFrame chứa HomePanel để làm parent cho Dialog
                JFrame parentFrame = (JFrame ) javax.swing.SwingUtilities.getWindowAncestor(view);

                // Mở cửa sổ biểu đồ
                ExpenseChartDialog chartDialog = new ExpenseChartDialog(parentFrame, list, month, year);
                chartDialog.setVisible(true);
            });
        }
    }
}