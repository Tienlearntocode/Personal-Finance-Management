package per.finance.view;

import per.finance.controller.InvestmentController;
import per.finance.model.Investment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvestmentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private InvestmentController controller;

    public InvestmentPanel() {
        controller = new InvestmentController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Khởi tạo bảng danh sách tài sản đầu tư theo yêu cầu
        tableModel = new DefaultTableModel(new String[]{
            "ID", "Tên Tài Sản", "Loại", "Vốn Ban Đầu (VNĐ)", "Giá Trị Hiện Tại (VNĐ)", "Lãi/Lỗ (VNĐ)", "Lợi Nhuận (%)", "Ngày ĐT"
        }, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Thanh công cụ nút bấm chức năng
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Thêm Khoản Đầu Tư");
        JButton btnDelete = new JButton("Xóa");
        JButton btnViewChart = new JButton("Xem Biểu Đồ (View Chart)");
        JButton btnRefresh = new JButton("Làm Mới");

        btnViewChart.setBackground(new Color(41, 128, 185));
        btnViewChart.setForeground(Color.WHITE);
        btnViewChart.setFocusPainted(false);

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnViewChart);
        btnPanel.add(btnRefresh);
        add(btnPanel, BorderLayout.SOUTH);

        // Gắn sự kiện
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> deleteSelected());
        btnViewChart.addActionListener(e -> openChartDialog());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<Investment> list = controller.getAllInvestments();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Investment inv : list) {
            tableModel.addRow(new Object[]{
                inv.getId(),
                inv.getName(),
                inv.getType(),
                String.format("%,.0f", inv.getInitialAmount()),
                String.format("%,.0f", inv.getCurrentAmount()),
                String.format("%,.0f", inv.getProfit()),
                String.format("%.2f%%", inv.getProfitPercentage()),
                sdf.format(inv.getInvestmentDate())
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Khoản Đầu Tư", true);
        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 8, 8));

        JTextField txtName = new JTextField();
        JTextField txtType = new JTextField();
        JTextField txtInitial = new JTextField();
        JTextField txtCurrent = new JTextField();
        JTextField txtDate = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JTextField txtRate = new JTextField();

        dialog.add(new JLabel("  Tên tài sản:")); dialog.add(txtName);
        dialog.add(new JLabel("  Loại (Tiết kiệm/Vàng...):")); dialog.add(txtType);
        dialog.add(new JLabel("  Vốn ban đầu:")); dialog.add(txtInitial);
        dialog.add(new JLabel("  Giá trị hiện tại:")); dialog.add(txtCurrent);
        dialog.add(new JLabel("  Ngày (YYYY-MM-DD):")); dialog.add(txtDate);
        dialog.add(new JLabel("  Tỷ suất dự kiến (%):")); dialog.add(txtRate);

        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(e -> {
            try {
                Investment inv = new Investment(
                    txtName.getText(),
                    txtType.getText(),
                    Double.parseDouble(txtInitial.getText()),
                    Double.parseDouble(txtCurrent.getText()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText()),
                    Double.parseDouble(txtRate.getText())
                );
                controller.addInvestment(inv);
                loadData();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Dữ liệu nhập vào không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(new JLabel());
        dialog.add(btnSave);
        dialog.setVisible(true);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) table.getValueAt(row, 0);
            controller.deleteInvestment(id);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa trên bảng!");
        }
    }

    private void openChartDialog() {
        List<Investment> list = controller.getAllInvestments();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu đầu tư để hiển thị biểu đồ!");
            return;
        }
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        InvestmentChartDialog chartDialog = new InvestmentChartDialog(parentFrame, list);
        chartDialog.setVisible(true);
    }
}