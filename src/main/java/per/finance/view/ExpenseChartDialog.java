/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.view;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import per.finance.dao.DashboardDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExpenseChartDialog extends JDialog {

    public ExpenseChartDialog(Frame parent, List<DashboardDAO.CategorySummary> categoryList, int month, int year) {
        super(parent, "Biểu Đồ Phân Bổ Chi Tiêu - Tháng " + month + "/" + year, true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Tạo dữ liệu cho biểu đồ tròn
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (DashboardDAO.CategorySummary cat : categoryList) {
            if (cat.getTotalAmount() > 0) {
                dataset.setValue(cat.getCategoryName(), cat.getTotalAmount());
            }
        }

        // Tạo biểu đồ tròn
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Tỷ Lệ Chi Tiêu Theo Danh Mục (Tháng " + month + "/" + year + ")",
                dataset,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setMouseWheelEnabled(true);

        add(chartPanel, BorderLayout.CENTER);
    }
}