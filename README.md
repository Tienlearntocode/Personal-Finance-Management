# Personal Finance Management - Java Swing Project

Dự án môn học Lập trình Java Swing - Quản lý chi tiêu cá nhân.

---

## 1. Yêu cầu môi trường
* **JDK:** Java 17 hoặc Java 21 LTS
* **IDE:** Apache NetBeans (Maven Project)
* **Database:** MySQL Server 8.0 (Port mặc định: 3306)

---

## 2. Hướng dẫn khởi chạy cho thành viên
1. **Clone dự án:** Clone repository về máy tính và mở bằng Apache NetBeans.
2. **Khởi tạo Database:** 
   * Mở MySQL Workbench / Navicat / HeidiSQL.
   * Mở và chạy toàn bộ mã trong file `database/schema.sql`.
3. **Cấu hình kết nối:**
   * Mở file `src/main/java/finance/config/DatabaseConnection.java`.
   * Kiểm tra thông số `USER` và `PASSWORD` cho khớp với MySQL cá nhân.
4. **Chạy ứng dụng:**
   * Tìm đến `finance.view.MainFrame` -> Chuột phải chọn **Run File** (Shift + F6).

---

## 3. Quy tắc làm việc với Git (Chống xung đột)
* **Không làm việc trực tiếp trên nhánh `main`.**
* Mỗi bạn tự tạo nhánh theo module phụ trách:
  * Bạn 2 (Khoản chi): `git checkout -b feature/expense`
  * Bạn 3 (Khoản thu): `git checkout -b feature/income`
  * Bạn 4 (Giao dịch): `git checkout -b feature/transaction`
  * Bạn 5 (Đầu tư): `git checkout -b feature/investment`
* Chỉ code trong các file Panel, DAO, Controller của mình. Không tự ý sửa `MainFrame.java`.
* Khi hoàn thành thì push nhánh cá nhân lên và tạo **Pull Request** để Trưởng nhóm review & merge.
