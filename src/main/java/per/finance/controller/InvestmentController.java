package per.finance.controller;

import per.finance.dao.InvestmentDAO;
import per.finance.model.Investment;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvestmentController {

    private final InvestmentDAO investmentDAO;

    public InvestmentController() {
        this.investmentDAO = new InvestmentDAO();
    }

    // Lấy danh sách đầu tư
    public List<Investment> getAllInvestments() {
        return investmentDAO.getAllInvestments();
    }

    // Thêm khoản đầu tư từ đối tượng Investment
    public boolean addInvestment(Investment investment) {
        if (investment == null) {
            return false;
        }

        return investmentDAO.insertInvestment(investment);
    }

    // Thêm khoản đầu tư từ dữ liệu nhập trên giao diện
    public String addInvestment(
            String name,
            String type,
            String initialAmount,
            String currentAmount,
            String investmentDate,
            String returnRate) {

        try {
            Investment investment = createInvestment(
                0,
                name,
                type,
                initialAmount,
                currentAmount,
                investmentDate,
                returnRate
            );

            boolean success =
                investmentDAO.insertInvestment(investment);

            if (success) {
                return null;
            }

            return "Không thể lưu khoản đầu tư. "
                    + "Vui lòng kiểm tra kết nối cơ sở dữ liệu.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // Cập nhật khoản đầu tư từ đối tượng Investment
    public boolean updateInvestment(Investment investment) {
        if (investment == null) {
            return false;
        }

        return investmentDAO.updateInvestment(investment);
    }

    // Cập nhật khoản đầu tư từ dữ liệu nhập trên giao diện
    public String updateInvestment(
            int id,
            String name,
            String type,
            String initialAmount,
            String currentAmount,
            String investmentDate,
            String returnRate) {

        try {
            Investment investment = createInvestment(
                id,
                name,
                type,
                initialAmount,
                currentAmount,
                investmentDate,
                returnRate
            );

            boolean success =
                investmentDAO.updateInvestment(investment);

            if (success) {
                return null;
            }

            return "Không thể cập nhật khoản đầu tư. "
                    + "Vui lòng kiểm tra kết nối cơ sở dữ liệu.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // Kiểm tra dữ liệu và tạo đối tượng Investment
    private Investment createInvestment(
            int id,
            String name,
            String type,
            String initialText,
            String currentText,
            String dateText,
            String rateText) {

        // Kiểm tra tên tài sản
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Tên tài sản không được để trống."
            );
        }

        // Kiểm tra loại tài sản
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Loại tài sản không được để trống."
            );
        }

        // Kiểm tra vốn ban đầu
        double initialAmount =
            parseNumber(initialText, "Vốn ban đầu");

        if (initialAmount <= 0) {
            throw new IllegalArgumentException(
                "Vốn ban đầu phải lớn hơn 0."
            );
        }

        // Kiểm tra giá trị hiện tại
        double currentAmount =
            parseNumber(currentText, "Giá trị hiện tại");

        if (currentAmount < 0) {
            throw new IllegalArgumentException(
                "Giá trị hiện tại không được nhỏ hơn 0."
            );
        }

        // Kiểm tra tỷ suất dự kiến
        double rate =
            parseNumber(rateText, "Tỷ suất dự kiến");

        // Kiểm tra ngày đầu tư
        Date date = parseInvestmentDate(dateText);

        return new Investment(
            id,
            name.trim(),
            type.trim(),
            initialAmount,
            currentAmount,
            date,
            rate
        );
    }

    // Chuyển đổi và kiểm tra từng trường số
    private double parseNumber(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                fieldName + " không được để trống."
            );
        }

        double number;

        try {
            number = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                fieldName + " phải là một số hợp lệ."
            );
        }

        if (!Double.isFinite(number)) {
            throw new IllegalArgumentException(
                fieldName + " phải là một số hữu hạn."
            );
        }

        return number;
    }

    // Kiểm tra ngày theo định dạng yyyy-MM-dd
    private Date parseInvestmentDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Ngày đầu tư không được để trống."
            );
        }

        String text = value.trim();

        if (!text.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException(
                "Ngày đầu tư phải có định dạng YYYY-MM-DD."
            );
        }

        SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

        sdf.setLenient(false);

        ParsePosition position = new ParsePosition(0);
        Date date = sdf.parse(text, position);

        if (date == null
                || position.getIndex() != text.length()) {
            throw new IllegalArgumentException(
                "Ngày đầu tư không hợp lệ."
            );
        }

        return date;
    }

    // Xóa khoản đầu tư
    public boolean deleteInvestment(int id) {
        return investmentDAO.deleteInvestment(id);
    }
}