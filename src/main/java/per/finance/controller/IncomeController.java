package per.finance.controller;
 
import per.finance.dao.IncomeDAO;
import per.finance.model.Income;
 
import java.sql.Date;
import java.util.List;
 
/**
 * Controller của phần Thu nhập: kiểm tra dữ liệu người dùng nhập,
 * dựng đối tượng Income rồi gọi IncomeDAO.
 * Các hàm thêm/sửa/xóa trả về SUCCESS nếu thành công, ngược lại trả về thông báo lỗi.
 */
public class IncomeController {
 
    public static final String SUCCESS = "OK";
 
    private final IncomeDAO incomeDAO;
 
    public IncomeController() {
        this.incomeDAO = new IncomeDAO();
    }
 
    public List<Income> getAllIncomes() {
        return incomeDAO.getAllIncomes();
    }
 
    /** Quy tắc nghiệp vụ: mỗi nguồn thu tương ứng với một mã danh mục. */
    public int getCategoryIdFromSource(String sourceName) {
        if (sourceName == null) {
            return 3;
        }
        switch (sourceName) {
            case "Lương":
                return 1;
            case "Thưởng":
                return 2;
            default:
                return 3;
        }
    }
 
    public String addIncome(String sourceName, String strAmount, String strDate, String note) {
        try {
            Income income = buildIncome(0, sourceName, strAmount, strDate, note);
            return incomeDAO.addIncome(income)
                    ? SUCCESS
                    : "Thêm thất bại! Vui lòng kiểm tra kết nối cơ sở dữ liệu.";
        } catch (IllegalArgumentException e) {
            return e.getMessage(); // thông báo lỗi nhập liệu
        }
    }
 
    public String updateIncome(int incomeId, String sourceName, String strAmount,
                               String strDate, String note) {
        if (incomeId <= 0) {
            return "Vui lòng chọn khoản thu cần sửa!";
        }
        try {
            Income income = buildIncome(incomeId, sourceName, strAmount, strDate, note);
            return incomeDAO.updateIncome(income) ? SUCCESS : "Cập nhật thất bại!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
 
    public String deleteIncome(int incomeId) {
        if (incomeId <= 0) {
            return "Vui lòng chọn khoản thu cần xóa!";
        }
        return incomeDAO.deleteIncome(incomeId) ? SUCCESS : "Xóa thất bại!";
    }
 
    /**
     * Kiểm tra dữ liệu và dựng đối tượng Income.
     * Nếu dữ liệu sai, ném IllegalArgumentException kèm thông báo để hiển thị cho người dùng.
     */
    private Income buildIncome(int incomeId, String sourceName, String strAmount,
                               String strDate, String note) {
        if (sourceName == null || sourceName.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn nguồn thu!");
        }
 
        if (strAmount == null || strAmount.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập số tiền!");
        }
        double amount;
        try {
            amount = Double.parseDouble(strAmount.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số tiền phải là số hợp lệ!");
        }
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Số tiền phải là số hợp lệ!");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Số tiền phải lớn hơn 0!");
        }
 
        if (strDate == null || strDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập ngày thu!");
        }
        Date incomeDate;
        try {
            incomeDate = Date.valueOf(strDate.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sai định dạng ngày! Hãy nhập theo chuẩn: YYYY-MM-DD");
        }
 
        int categoryId = getCategoryIdFromSource(sourceName);
        String cleanNote = (note == null) ? "" : note.trim();
 
        return new Income(incomeId, sourceName, amount, incomeDate, categoryId, cleanNote);
    }
}