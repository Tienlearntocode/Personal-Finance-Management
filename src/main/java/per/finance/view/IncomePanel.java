package per.finance.view;

import per.finance.dao.IncomeDAO;
import per.finance.model.Income;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.util.List;

public class IncomePanel extends javax.swing.JPanel {

    private IncomeDAO incomeDAO;
    private DefaultTableModel tableModel;
    private int selectedIncomeId = -1; // Lưu ID của dòng đang chọn để Sửa/Xóa

    public IncomePanel() {
        initComponents();
        incomeDAO = new IncomeDAO();
        
        // Khởi tạo model cho JTable
        tableModel = (DefaultTableModel) jTable1.getModel();
        
        // Tải dữ liệu lên bảng khi mở giao diện
        loadTableData();
        
        // Sự kiện click vào dòng trên bảng để đổ dữ liệu lên các ô nhập liệu
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                if (row >= 0) {
                    selectedIncomeId = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
                    
                    String sourceName = jTable1.getValueAt(row, 1).toString();
                    cboSourceName.setSelectedItem(sourceName); // Chọn item tương ứng
                    
                    txtAmount.setText(jTable1.getValueAt(row, 2).toString());
                    txtDate.setText(jTable1.getValueAt(row, 3).toString());
                    txtCategoryId.setText(jTable1.getValueAt(row, 4).toString());
                    txtNote.setText(jTable1.getValueAt(row, 5) != null ? jTable1.getValueAt(row, 5).toString() : "");
                }
            }
        });
    }

    // Hàm load dữ liệu từ MySQL lên JTable
    public void loadTableData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        List<Income> list = incomeDAO.getAllIncomes();
        for (Income inc : list) {
            Object[] row = {
                inc.getIncomeId(),
                inc.getSourceName(),
                inc.getAmount(),
                inc.getIncomeDate(),
                inc.getCategoryId(),
                inc.getNote()
            };
            tableModel.addRow(row);
        }
    }

    // Hàm xóa trắng các ô nhập liệu
    private void clearForm() {
        selectedIncomeId = -1;
        cboSourceName.setSelectedIndex(0);
        txtAmount.setText("");
        txtDate.setText("");
        updateCategoryIdBySource(); // Reset Category ID theo combo box
        txtNote.setText("");
    }

    // Hàm hỗ trợ tự động lấy Mã danh mục theo Nguồn thu
    private int getCategoryIdFromSource(String sourceName) {
        switch (sourceName) {
            case "Lương":
                return 1;
            case "Thưởng":
                return 2;
            case "Khác":
            default:
                return 3;
        }
    }

    // Cập nhật giá trị hiển thị trên txtCategoryId
    private void updateCategoryIdBySource() {
        String selectedSource = (String) cboSourceName.getSelectedItem();
        int catId = getCategoryIdFromSource(selectedSource);
        txtCategoryId.setText(String.valueOf(catId));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboSourceName = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCategoryId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); 
        jLabel1.setText("QUẢN LÝ KHOẢN THU");

        jLabel2.setText("Nguồn thu:");

        // Khởi tạo ComboBox với 3 nguồn thu cố định
        cboSourceName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lương", "Thưởng", "Khác" }));
        cboSourceName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCategoryIdBySource();
            }
        });

        jLabel3.setText("Số tiền:");

        jLabel4.setText("Ngày thu (YYYY-MM-DD):");

        jLabel5.setText("Mã danh mục (Category ID):");

        txtCategoryId.setEditable(false); // Không cho người dùng sửa tay ID danh mục

        jLabel6.setText("Ghi chú:");

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nguồn thu", "Số tiền", "Ngày thu", "Danh mục", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboSourceName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAmount)
                            .addComponent(txtDate)
                            .addComponent(txtCategoryId)
                            .addComponent(txtNote, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboSourceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete)
                            .addComponent(btnClear))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        // Khởi tạo giá trị Category ID mặc định khi load
        updateCategoryIdBySource();
    }// </editor-fold>                        

    // Sự kiện nút Thêm
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sourceName = cboSourceName.getSelectedItem().toString();
            double amount = Double.parseDouble(txtAmount.getText().trim());
            Date incomeDate = Date.valueOf(txtDate.getText().trim());
            int categoryId = getCategoryIdFromSource(sourceName);
            String note = txtNote.getText().trim();

            Income income = new Income(0, sourceName, amount, incomeDate, categoryId, note);
            boolean success = incomeDAO.addIncome(income);

            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm khoản thu thành công!");
                loadTableData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Số tiền!", "Lỗi định dạng", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng! Hãy nhập theo chuẩn: YYYY-MM-DD", "Lỗi ngày tháng", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Sự kiện nút Sửa (Update)
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedIncomeId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String sourceName = cboSourceName.getSelectedItem().toString();
            double amount = Double.parseDouble(txtAmount.getText().trim());
            Date incomeDate = Date.valueOf(txtDate.getText().trim());
            int categoryId = getCategoryIdFromSource(sourceName);
            String note = txtNote.getText().trim();

            Income income = new Income(selectedIncomeId, sourceName, amount, incomeDate, categoryId, note);
            boolean success = incomeDAO.updateIncome(income);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật khoản thu thành công!");
                loadTableData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Sự kiện nút Xóa
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedIncomeId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khoản thu này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = incomeDAO.deleteIncome(selectedIncomeId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTableData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Sự kiện nút Làm mới form
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        clearForm();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboSourceName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtCategoryId;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtNote;
    // End of variables declaration                   
}