package per.finance.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import per.finance.model.Investment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InvestmentChartDialog extends JDialog {

    public InvestmentChartDialog(Frame parent, List<Investment> investments) {
        super(parent, "Cửa Sổ Phân Tích Trực Quan - View Chart", true);
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(1, 2, 10, 10));

        // 1. Biểu đồ Tròn (PieChart) - Cơ cấu vốn đầu tư theo loại tài sản
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (Investment inv : investments) {
            pieDataset.setValue(inv.getName() + " (" + inv.getType() + ")", inv.getCurrentAmount());
        }
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Cơ Cấu Danh Mục Đầu Tư", 
                pieDataset, 
                true, true, false
        );
        ChartPanel piePanel = new ChartPanel(pieChart);

        // 2. Biểu đồ Cột (BarChart) - So sánh Vốn ban đầu và Giá trị hiện tại
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (Investment inv : investments) {
            barDataset.addValue(inv.getInitialAmount(), "Vốn Ban Đầu", inv.getName());
            barDataset.addValue(inv.getCurrentAmount(), "Giá Trị Hiện Tại", inv.getName());
        }
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tăng Trưởng Tài Sản (Vốn vs Hiện Tại)", 
                "Tài Sản", 
                "Số Tiền (VNĐ)", 
                barDataset
        );
        ChartPanel barPanel = new ChartPanel(barChart);

        add(piePanel);
        add(barPanel);
    }
}