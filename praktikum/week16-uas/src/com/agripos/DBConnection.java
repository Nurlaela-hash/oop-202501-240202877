package com.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // 1. Ubah protokol ke jdbc:postgresql
    // 2. Port default PostgreSQL adalah 5432
    // 3. Sesuaikan nama database (di screenshot kamu namanya 'agri_pos')
    private static final String URL = "jdbc:postgresql://localhost:5432/agri_pos";
    
    // Default user PostgreSQL biasanya 'postgres'
    private static final String USER = "postgres"; 
    
    // MASUKKAN PASSWORD POSTGRES KAMU DI SINI
    private static final String PASS = "1234"; 

    public static Connection getConnection() throws SQLException {
        try {
            // Opsional: Memastikan driver PostgreSQL terpanggil
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}