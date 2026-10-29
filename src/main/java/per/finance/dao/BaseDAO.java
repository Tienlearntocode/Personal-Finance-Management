/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.dao;


import per.finance.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {

    /**
     * Lấy kết nối tới CSDL từ DatabaseConnection
     */
    protected Connection getConnection() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            // DatabaseConnection trả về null khi kết nối lỗi; báo lỗi rõ ràng thay vì để NullPointerException
            throw new SQLException("Không kết nối được cơ sở dữ liệu");
        }
        return conn;
    }

    /**
     * Đóng an toàn ResultSet, PreparedStatement, Connection
     */
    protected void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Đóng an toàn PreparedStatement và Connection
     */
    protected void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null);
    }
}