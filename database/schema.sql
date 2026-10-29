-- 1. Xóa database cũ nếu có để làm sạch từ đầu và tạo mới
DROP DATABASE IF EXISTS personal_finance;
CREATE DATABASE personal_finance 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE personal_finance;

CREATE TABLE expense (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    category VARCHAR(100) NOT NULL,
    expense_date DATE NOT NULL,
    note TEXT,
    image_path VARCHAR(500) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_expense_amount CHECK (amount > 0)
);

-- 3. Bảng Income (Phục vụ IncomeDAO và IncomePanel - đúng tên class & thuộc tính)
CREATE TABLE Income (
    income_id INT AUTO_INCREMENT PRIMARY KEY,
    source_name VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    income_date DATE NOT NULL,
    category_id INT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_income_amount CHECK (amount > 0)
);

-- 4. Bảng investments (Phục vụ InvestmentDAO và InvestmentPanel)
CREATE TABLE investments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    initial_amount DOUBLE NOT NULL,
    current_amount DOUBLE NOT NULL,
    investment_date DATE NOT NULL,
    return_rate DOUBLE NOT NULL
);

-- 5. Thêm dữ liệu mẫu ban đầu để kiểm tra hiển thị
INSERT INTO expense (amount, category, expense_date, note, image_path) VALUES
(288888, 'Học Phí', '2026-10-15', 'Đóng tiền học phí kì 1', NULL),
(20000, 'Mua sắm', '2026-10-20', 'Mua đồ dùng cá nhân', NULL),
(4536, 'Đầu Tư', '2026-10-08', 'Nạp tiền game', NULL);

INSERT INTO Income (source_name, amount, income_date, category_id, note) VALUES
('Lương tháng 10', 10000000, '2026-10-05', 1, 'Lương công ty'),
('Thưởng dự án', 2000000, '2026-10-12', 2, 'Thưởng hoàn thành sớm');

INSERT INTO investments (name, type, initial_amount, current_amount, investment_date, return_rate) VALUES
('Cổ phiếu FPT', 'Cổ phiếu', 10000000, 12500000, '2026-05-01', 25.0),
('Tiết kiệm Vietcombank', 'Tiết kiệm', 20000000, 20800000, '2026-01-15', 4.0);