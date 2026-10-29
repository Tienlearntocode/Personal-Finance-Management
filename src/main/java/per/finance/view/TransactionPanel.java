package per.finance.view;

import per.finance.dao.TransactionDAO;
import per.finance.model.Transaction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TransactionPanel extends JPanel {
    private JTable table;
    private TransactionTableModel tableModel;
    private TableRowSorter<TransactionTableModel> sorter;
    private TransactionDAO dao = new TransactionDAO();

    public TransactionPanel() {
        setLayout(new BorderLayout(10, 10));

        loadDataToTable();

        // Thanh công cụ phía trên (Tìm kiếm & Xuất CSV)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtFilter = new JTextField(15);
        JButton btnFilter = new JButton("Lọc");
        JButton btnExport = new JButton("Xuất file CSV");

        btnFilter.addActionListener(e -> {
            String text = txtFilter.getText().trim();
            if (text.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        btnExport.addActionListener(e -> exportToCSV());

        topPanel.add(new JLabel("Tìm kiếm:"));
        topPanel.add(txtFilter);
        topPanel.add(btnFilter);
        topPanel.add(btnExport);

        // Thanh thao tác phía dưới (Xóa & Tải lại)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDelete = new JButton("Xóa giao dịch");
        JButton btnRefresh = new JButton("Tải lại");

        btnDelete.addActionListener(e -> deleteSelectedTransaction());
        btnRefresh.addActionListener(e -> refreshTableData());

        bottomPanel.add(btnDelete);
        bottomPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Mỗi khi màn hình hiện lại (ví dụ quay về từ tab khác), tải lại bảng cho khớp CSDL
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                refreshTableData();
            }
        });
    
    }
    
    private void loadDataToTable() {
        List<Transaction> list = dao.getAllTransactions();
        tableModel = new TransactionTableModel(list);
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
    }

    private void refreshTableData() {
        tableModel.setData(dao.getAllTransactions());
    }

    private void deleteSelectedTransaction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch cần xóa!");
            return;
        }
        int modelRow = table.convertRowIndexToModel(selectedRow);
        int id = (int) tableModel.getValueAt(modelRow, 0);
        String type = (String) tableModel.getValueAt(modelRow, 2); // Cột Loại: "Chi tiêu" hoặc "Thu nhập"

        if (JOptionPane.showConfirmDialog(this, "Xóa giao dịch " + type + " ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (dao.deleteTransaction(id, type)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                refreshTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath().endsWith(".csv") ? file.getAbsolutePath() : file.getAbsolutePath() + ".csv";
            try (FileWriter writer = new FileWriter(path)) {
                for (int c = 0; c < tableModel.getColumnCount(); c++) {
                    writer.write(tableModel.getColumnName(c) + (c == tableModel.getColumnCount() - 1 ? "" : ","));
                }
                writer.write("\n");
                for (int r = 0; r < table.getRowCount(); r++) {
                    for (int c = 0; c < table.getColumnCount(); c++) {
                        Object val = table.getValueAt(r, c);
                        writer.write((val != null ? val.toString() : "") + (c == table.getColumnCount() - 1 ? "" : ","));
                    }
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(this, "Xuất file CSV thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private class TransactionTableModel extends AbstractTableModel {
        private final String[] columnNames = {"ID", "Ngày", "Loại", "Danh mục", "Ghi chú", "Số tiền", "Ảnh hóa đơn"};
        private List<Transaction> data;

        public TransactionTableModel(List<Transaction> data) { this.data = data; }
        public void setData(List<Transaction> data) { this.data = data; fireTableDataChanged(); }

        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return columnNames.length; }
        @Override public String getColumnName(int col) { return columnNames[col]; }
        @Override public Object getValueAt(int row, int col) {
            Transaction t = data.get(row);
            switch (col) {
                case 0: return t.getId();
                case 1: return t.getDate();
                case 2: return t.getType();
                case 3: return t.getCategory();
                case 4: return t.getNote();
                case 5: return t.getAmount();
                case 6: return t.getImagePath() != null ? t.getImagePath() : "Không có";
                default: return null;
            }
        }
    }
    
}