-- Tạo Database
CREATE DATABASE IF NOT EXISTS personal_finance_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE personal_finance_db;

-- 1. Bảng Danh mục (Categories: Ăn uống, Thuê nhà, Lương, v.v.)
CREATE TABLE IF NOT EXISTS categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    category_type ENUM('EXPENSE', 'INCOME') NOT NULL
);

-- 2. Bảng Giao dịch thu chi (Transactions: Lưu Expense và Income)
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    transaction_type ENUM('EXPENSE', 'INCOME') NOT NULL,
    category_id INT,
    transaction_date DATE NOT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL
);

-- 3. Bảng Đầu tư (Investments)
CREATE TABLE IF NOT EXISTS investments (
    investment_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_name VARCHAR(100) NOT NULL,       -- Tên tài sản: Cổ phiếu, Tiết kiệm, Vàng
    amount_invested DOUBLE NOT NULL,        -- Số tiền đầu tư gốc
    current_value DOUBLE NOT NULL,          -- Giá trị hiện tại
    start_date DATE NOT NULL,
    note VARCHAR(255)
);

-- Dữ liệu mẫu ban đầu để giao diện chạy thử có sẵn số liệu
INSERT INTO categories (category_name, category_type) VALUES
('Food & Drink', 'EXPENSE'),
('Rental', 'EXPENSE'),
('Transportation', 'EXPENSE'),
('Others', 'EXPENSE'),
('Salary', 'INCOME'),
('Bonus', 'INCOME');

-- Dữ liệu giao dịch mẫu tháng 5
INSERT INTO transactions (amount, transaction_type, category_id, transaction_date, note) VALUES
(578000, 'EXPENSE', 1, '2026-05-10', 'Ăn uống liên hoan'),
(1400000, 'EXPENSE', 2, '2026-05-01', 'Tiền phòng trọ'),
(130000, 'EXPENSE', 3, '2026-05-05', 'Xăng xe'),
(1026000, 'EXPENSE', 4, '2026-05-15', 'Mua sắm lặt vặt'),
(1540000, 'INCOME', 5, '2026-05-05', 'Lương làm thêm');
