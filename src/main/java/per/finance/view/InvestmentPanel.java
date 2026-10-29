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
        setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // Bảng danh sách khoản đầu tư.
        // Không cho phép sửa trực tiếp trên bảng.
        tableModel = new DefaultTableModel(new String[]{
            "ID",
            "Tên Tài Sản",
            "Loại",
            "Vốn Ban Đầu (VNĐ)",
            "Giá Trị Hiện Tại (VNĐ)",
            "Lãi/Lỗ (VNĐ)",
            "Lợi Nhuận (%)",
            "Ngày ĐT"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Thanh công cụ.
        JPanel btnPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        JButton btnAdd =
            new JButton("Thêm Khoản Đầu Tư");

        JButton btnEdit =
            new JButton("Sửa");

        JButton btnDelete =
            new JButton("Xóa");

        JButton btnViewChart =
            new JButton("Xem Biểu Đồ (View Chart)");

        JButton btnRefresh =
            new JButton("Làm Mới");

        btnViewChart.setBackground(
            new Color(41, 128, 185)
        );
        btnViewChart.setForeground(Color.WHITE);
        btnViewChart.setFocusPainted(false);

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnViewChart);
        btnPanel.add(btnRefresh);

        add(btnPanel, BorderLayout.SOUTH);

        // Gắn sự kiện cho các nút.
        btnAdd.addActionListener(e -> showAddDialog());

        btnEdit.addActionListener(e -> editSelected());

        btnDelete.addActionListener(e -> deleteSelected());

        btnViewChart.addActionListener(e -> openChartDialog());

        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    // ==================== TẢI DỮ LIỆU ====================

    public void loadData() {
        tableModel.setRowCount(0);

        List<Investment> list =
            controller.getAllInvestments();

        SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

        for (Investment inv : list) {
            Date investmentDate = inv.getInvestmentDate();

            String dateText = investmentDate == null
                ? ""
                : sdf.format(investmentDate);

            tableModel.addRow(new Object[]{
                inv.getId(),
                inv.getName(),
                inv.getType(),
                String.format("%,.0f", inv.getInitialAmount()),
                String.format("%,.0f", inv.getCurrentAmount()),
                String.format("%,.0f", inv.getProfit()),
                String.format("%.2f%%", inv.getProfitPercentage()),
                dateText
            });
        }
    }

    // ==================== THÊM KHOẢN ĐẦU TƯ ====================

    private void showAddDialog() {
        JDialog dialog = new JDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            "Thêm Khoản Đầu Tư",
            true
        );

        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 8, 8));

        JTextField txtName = new JTextField();
        JTextField txtType = new JTextField();
        JTextField txtInitial = new JTextField();
        JTextField txtCurrent = new JTextField();

        JTextField txtDate = new JTextField(
            new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date())
        );

        JTextField txtRate = new JTextField();

        dialog.add(new JLabel("  Tên tài sản:"));
        dialog.add(txtName);

        dialog.add(new JLabel("  Loại (Tiết kiệm/Vàng...):"));
        dialog.add(txtType);

        dialog.add(new JLabel("  Vốn ban đầu:"));
        dialog.add(txtInitial);

        dialog.add(new JLabel("  Giá trị hiện tại:"));
        dialog.add(txtCurrent);

        dialog.add(new JLabel("  Ngày (YYYY-MM-DD):"));
        dialog.add(txtDate);

        dialog.add(new JLabel("  Tỷ suất dự kiến (%):"));
        dialog.add(txtRate);

        JButton btnSave = new JButton("Lưu");

        btnSave.addActionListener(e -> {
            // Controller kiểm tra dữ liệu và thực hiện lưu.
            String message = controller.addInvestment(
                txtName.getText(),
                txtType.getText(),
                txtInitial.getText(),
                txtCurrent.getText(),
                txtDate.getText(),
                txtRate.getText()
            );

            // Có lỗi: giữ nguyên cửa sổ để người dùng sửa.
            if (message != null) {
                JOptionPane.showMessageDialog(
                    dialog,
                    message,
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            loadData();
            dialog.dispose();

            JOptionPane.showMessageDialog(
                this,
                "Thêm khoản đầu tư thành công!"
            );
        });

        dialog.add(new JLabel());
        dialog.add(btnSave);

        dialog.setVisible(true);
    }

    // ==================== CHỌN KHOẢN ĐẦU TƯ ĐỂ SỬA ====================

    private void editSelected() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn khoản đầu tư cần sửa!"
            );
            return;
        }

        // Lấy ID từ dòng được chọn.
        int id = Integer.parseInt(
            table.getValueAt(row, 0).toString()
        );

        List<Investment> list =
            controller.getAllInvestments();

        Investment selectedInvestment = null;

        for (Investment inv : list) {
            if (inv.getId() == id) {
                selectedInvestment = inv;
                break;
            }
        }

        if (selectedInvestment == null) {
            JOptionPane.showMessageDialog(
                this,
                "Không tìm thấy khoản đầu tư!"
            );
            return;
        }

        showEditDialog(selectedInvestment);
    }

    // ==================== CỬA SỔ SỬA KHOẢN ĐẦU TƯ ====================

    private void showEditDialog(Investment inv) {
        JDialog dialog = new JDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            "Sửa Khoản Đầu Tư",
            true
        );

        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 8, 8));

        JTextField txtName = new JTextField(
            inv.getName() == null ? "" : inv.getName()
        );

        JTextField txtType = new JTextField(
            inv.getType() == null ? "" : inv.getType()
        );

        JTextField txtInitial = new JTextField(
            String.valueOf(inv.getInitialAmount())
        );

        JTextField txtCurrent = new JTextField(
            String.valueOf(inv.getCurrentAmount())
        );

        Date investmentDate = inv.getInvestmentDate();

        JTextField txtDate = new JTextField(
            investmentDate == null
                ? ""
                : new SimpleDateFormat("yyyy-MM-dd")
                    .format(investmentDate)
        );

        JTextField txtRate = new JTextField(
            String.valueOf(inv.getReturnRate())
        );

        dialog.add(new JLabel("  Tên tài sản:"));
        dialog.add(txtName);

        dialog.add(new JLabel("  Loại (Tiết kiệm/Vàng...):"));
        dialog.add(txtType);

        dialog.add(new JLabel("  Vốn ban đầu:"));
        dialog.add(txtInitial);

        dialog.add(new JLabel("  Giá trị hiện tại:"));
        dialog.add(txtCurrent);

        dialog.add(new JLabel("  Ngày (YYYY-MM-DD):"));
        dialog.add(txtDate);

        dialog.add(new JLabel("  Tỷ suất dự kiến (%):"));
        dialog.add(txtRate);

        JButton btnSave = new JButton("Cập nhật");

        btnSave.addActionListener(e -> {
            // Giữ nguyên ID và gửi dữ liệu cho Controller kiểm tra.
            String message = controller.updateInvestment(
                inv.getId(),
                txtName.getText(),
                txtType.getText(),
                txtInitial.getText(),
                txtCurrent.getText(),
                txtDate.getText(),
                txtRate.getText()
            );

            // Có lỗi: thông báo và không đóng cửa sổ.
            if (message != null) {
                JOptionPane.showMessageDialog(
                    dialog,
                    message,
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            loadData();
            dialog.dispose();

            JOptionPane.showMessageDialog(
                this,
                "Cập nhật khoản đầu tư thành công!"
            );
        });

        dialog.add(new JLabel());
        dialog.add(btnSave);

        dialog.setVisible(true);
    }

    // ==================== XÓA KHOẢN ĐẦU TƯ ====================

    private void deleteSelected() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn dòng cần xóa trên bảng!"
            );
            return;
        }

        // Xác nhận trước khi xóa.
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa khoản đầu tư này không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(
            table.getValueAt(row, 0).toString()
        );

        boolean success = controller.deleteInvestment(id);

        if (success) {
            loadData();

            JOptionPane.showMessageDialog(
                this,
                "Xóa khoản đầu tư thành công!"
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Xóa thất bại. Vui lòng kiểm tra kết nối "
                    + "cơ sở dữ liệu hoặc dữ liệu cần xóa.",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================== HIỂN THỊ BIỂU ĐỒ ====================

    private void openChartDialog() {
        List<Investment> list =
            controller.getAllInvestments();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Chưa có dữ liệu đầu tư để hiển thị biểu đồ!"
            );
            return;
        }

        Frame parentFrame =
            (Frame) SwingUtilities.getWindowAncestor(this);

        InvestmentChartDialog chartDialog =
            new InvestmentChartDialog(parentFrame, list);

        chartDialog.setVisible(true);
    }
}