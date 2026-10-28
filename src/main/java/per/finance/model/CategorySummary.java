package per.finance.model;

/**
 * Tổng chi tiêu của một danh mục trong tháng, kèm tỷ lệ phần trăm so với tổng chi.
 */
public class CategorySummary {
    private final String categoryName;
    private final double totalAmount;
    private final double percentage;

    public CategorySummary(String categoryName, double totalAmount, double percentage) {
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
        this.percentage = percentage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPercentage() {
        return percentage;
    }
}