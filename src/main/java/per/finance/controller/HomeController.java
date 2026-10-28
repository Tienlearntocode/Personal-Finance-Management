package per.finance.controller;

import per.finance.dao.DashboardDAO;
import per.finance.model.CategorySummary;
import per.finance.model.MonthlySummary;
import per.finance.view.ExpenseChartDialog;
import per.finance.view.HomePanel;

import java.awt.Frame;
import java.awt.Window;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Controller của trang Tổng quan (Home): nhận thao tác từ HomePanel,
 * lấy dữ liệu từ DashboardDAO rồi đổ lại lên giao diện.
 */
public class HomeController {

    private final HomePanel view;
    private final DashboardDAO dashboardDAO;
    private final DecimalFormat currencyFormatter = new DecimalFormat("#,##0 ₫");
    private final DecimalFormat percentFormatter = new DecimalFormat("#0.0'%'");

    public HomeController(HomePanel view) {
        this.view = view;
        this.dashboardDAO = new DashboardDAO();
        initController();
    }

    private void initController() {
        // 1. Đặt tháng/năm mặc định theo ngày hiện tại TRƯỚC khi đăng ký listener,
        //    để việc gán giá trị này không kích hoạt tải dữ liệu thừa.
        LocalDate today = LocalDate.now();
        view.getMonthChooser().setMonth(today.getMonthValue() - 1); // JMonthChooser tính từ 0
        view.getYearChooser().setYear(today.getYear());

        // 2. Đăng ký sự kiện (mỗi sự kiện chỉ đăng ký MỘT lần tại đây)
        view.getMonthChooser().addPropertyChangeListener("month", evt -> loadDashboardData());
        view.getYearChooser().addPropertyChangeListener("year", evt -> loadDashboardData());
        view.getBtnViewChart().addActionListener(e -> openChart());

        // 3. Tải dữ liệu lần đầu
        loadDashboardData();
    }

    /** Tải lại toàn bộ số liệu theo tháng/năm đang chọn. */
    public void loadDashboardData() {
        int month = getSelectedMonth();
        int year = getSelectedYear();

        // Tổng thu, tổng chi, số dư
        MonthlySummary summary = dashboardDAO.getMonthlySummary(month, year);
        view.getLblExpenseAmount().setText(currencyFormatter.format(summary.getTotalExpense()));
        view.getLblIncomeAmount().setText(currencyFormatter.format(summary.getTotalIncome()));
        view.getLblNetBalance().setText(currencyFormatter.format(summary.getNetBalance()));

        // Chi tiêu theo danh mục
        fillCategoryTable(dashboardDAO.getCategorySummaryByMonth(month, year));
    }

    /** Đổ danh sách danh mục chi tiêu vào JTable của HomePanel. */
    private void fillCategoryTable(List<CategorySummary> categoryList) {
        DefaultTableModel model = (DefaultTableModel) view.getTblCategory().getModel();
        model.setRowCount(0); // xóa dữ liệu cũ

        for (CategorySummary cat : categoryList) {
            model.addRow(new Object[]{
                cat.getCategoryName(),
                currencyFormatter.format(cat.getTotalAmount()),
                percentFormatter.format(cat.getPercentage())
            });
        }
    }

    /** Mở cửa sổ biểu đồ tròn phân bổ chi tiêu của tháng/năm đang chọn. */
    private void openChart() {
        int month = getSelectedMonth();
        int year = getSelectedYear();

        List<CategorySummary> list = dashboardDAO.getCategorySummaryByMonth(month, year);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Tháng " + month + "/" + year + " chưa có chi tiêu để vẽ biểu đồ!");
            return;
        }

        // Cửa sổ cha có thể không phải JFrame nên kiểm tra kiểu trước khi ép
        Window window = SwingUtilities.getWindowAncestor(view);
        Frame parentFrame = (window instanceof Frame) ? (Frame) window : null;

        new ExpenseChartDialog(parentFrame, list, month, year).setVisible(true);
    }

    private int getSelectedMonth() {
        return view.getMonthChooser().getMonth() + 1; // đổi về 1-12
    }

    private int getSelectedYear() {
        return view.getYearChooser().getYear();
    }
}