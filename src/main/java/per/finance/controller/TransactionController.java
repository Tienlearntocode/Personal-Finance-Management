package per.finance.controller;

import per.finance.dao.TransactionDAO;
import per.finance.model.Transaction;
import java.util.List;

public class TransactionController {
    private TransactionDAO transactionDAO;

    public TransactionController() {
        this.transactionDAO = new TransactionDAO();
    }

    // Lấy danh sách toàn bộ giao dịch cho View hiển thị
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    // Xử lý nghiệp vụ xóa giao dịch (hỗ trợ phân biệt loại thu/chi)
    public boolean deleteTransaction(int id, String type) {
        if (id <= 0 || type == null||type.trim().isEmpty()) { 
            return false;
        }
        return transactionDAO.deleteTransaction(id, type);
    }
}