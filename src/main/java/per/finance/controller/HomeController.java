/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.controller;

import per.finance.dao.DashboardDAO;
import per.finance.model.MonthlySummary;
import per.finance.view.HomePanel;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class HomeController {

    private final HomePanel view;
    private final DashboardDAO dashboardDAO;
    private final DecimalFormat currencyFormatter = new DecimalFormat("#,##0 ₫");

    // 1. Khởi tạo liên kết (Constructor)
    public HomeController(HomePanel view) {
        this.view = view;
        this.dashboardDAO = new DashboardDAO(); // Khởi tạo đối tượng DashboardDAO
        
        initController();
    }

    // 3. Đăng ký sự kiện (Event Listeners)
    private void initController() {
        // Lắng nghe sự kiện thay đổi tháng trên JSpinner
        if (view.getSpnMonth() != null) {
            view.getSpnMonth().addChangeListener(e -> loadDashboardData());
        }

        // Lắng nghe sự kiện click nút Làm mới (Refresh) nếu giao diện có nút btnRefresh
        if (view.getBtnRefresh() != null) {
            view.getBtnRefresh().addActionListener(e -> loadDashboardData());
        }

        // Tải dữ liệu ban đầu khi vừa mở màn hình
        loadDashboardData();
    }

    // 2. Hàm tải dữ liệu (loadDashboardData)
    public void loadDashboardData() {
        // Lấy tháng từ JSpinner trên giao diện (hoặc mặc định lấy tháng hiện tại nếu chưa chọn)
        int selectedMonth = LocalDate.now().getMonthValue();
        if (view.getSpnMonth() != null) {
            selectedMonth = (int) view.getSpnMonth().getValue();
        }
        int currentYear = LocalDate.now().getYear();

        // Gọi DashboardDAO lấy đối tượng MonthlySummary chứa tổng thu, tổng chi, số dư
        MonthlySummary summary = dashboardDAO.getMonthlySummary(selectedMonth, currentYear);

        // Cập nhật trực tiếp lên các nhãn (JLabel) hiển thị trên HomePanel
        if (view.getLblExpenseAmount() != null) {
            view.getLblExpenseAmount().setText(currencyFormatter.format(summary.getTotalExpense()));
        }

        if (view.getLblIncomeAmount() != null) {
            view.getLblIncomeAmount().setText(currencyFormatter.format(summary.getTotalIncome()));
        }

        // Nếu giao diện có nhãn hiển thị Số dư (netBalance)
        if (view.getLblNetBalance() != null) {
            view.getLblNetBalance().setText(currencyFormatter.format(summary.getNetBalance()));
        }
    }
}