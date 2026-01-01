package StokTakipJava;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3307/stok_takip?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "1905060707e"; 

        return DriverManager.getConnection(url, user, password);
    }
}