/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    
private static final String URL = "jdbc:mysql://localhost:3306/personal_finance?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "123456"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Nạp JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Mở kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Chưa nạp được Driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Kết nối thất bại! Kiểm tra lại URL, User hoặc Password.");
            e.printStackTrace();
        }
        return conn;
    }

    // Hàm main để test thử kết nối trực tiếp
    public static void main(String[] args) {
        Connection c = getConnection();
        if (c != null) {
            System.out.println("-> Kết nối MySQL thành công!");
        } else {
            System.out.println("-> Kết nối thất bại!");
        }
    }
}
