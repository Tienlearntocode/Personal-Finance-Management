/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package per.finance.config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author haphong
 */
public class DatabaseConnection {

    private static final String HOST_NAME="localhost";
    private static final String DB_NAME="personal_finance";
    private static final String user="root";
    private static final String PASSWORD="mysql";
    private static final String url="jdbc:mysql://"+HOST_NAME+":3306/"+DB_NAME+"?useSSL=false&serverTimezone=UTC";
    public static Connection getConnection(){
        Connection conn=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection(url, user, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
}
