package com.agripos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    
    // --- (Metode addProduct tidak perlu diubah, SQL-nya standar) ---
    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getCategory());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getStock());
            stmt.executeUpdate();
        }
    }

    // --- (Metode updateProduct tidak perlu diubah) ---
    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, category=?, price=?, stock=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getCategory());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getStock());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        }
    }

    // --- (Metode getAllProducts tidak perlu diubah) ---
    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id ASC"; // Tambah ORDER BY agar rapi
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(rs.getInt("id"), rs.getString("name"),
                    rs.getString("category"), rs.getDouble("price"), rs.getInt("stock")));
            }
        }
        return list;
    }

    // --- [PENTING] Metode processTransaction Diperbarui untuk PostgreSQL ---
    public void processTransaction(List<Product> cart, List<Integer> qtys) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Insert ke tabel Sales
            // PENTING: PostgreSQL kadang butuh kita sebutkan nama kolom ID yang mau diambil ("id")
            String sqlSale = "INSERT INTO sales (total_amount) VALUES (?)";
            PreparedStatement psSale = conn.prepareStatement(sqlSale, new String[]{"id"});
            
            double total = 0;
            for (int i=0; i<cart.size(); i++) total += cart.get(i).getPrice() * qtys.get(i);
            psSale.setDouble(1, total);
            
            int affectedRows = psSale.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Gagal membuat transaksi sales.");

            // 2. Ambil ID yang baru dibuat
            int saleId = 0;
            try (ResultSet rs = psSale.getGeneratedKeys()) {
                if (rs.next()) {
                    saleId = rs.getInt(1); // Mengambil kolom pertama (id)
                } else {
                    throw new SQLException("Gagal mengambil ID sales.");
                }
            }

            // 3. Insert Detail & Update Stock
            String sqlItem = "INSERT INTO sale_items (sale_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
            String sqlStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
            
            PreparedStatement psItem = conn.prepareStatement(sqlItem);
            PreparedStatement psStock = conn.prepareStatement(sqlStock);

            for (int i=0; i<cart.size(); i++) {
                // Insert Item
                psItem.setInt(1, saleId);
                psItem.setInt(2, cart.get(i).getId());
                psItem.setInt(3, qtys.get(i));
                psItem.setDouble(4, cart.get(i).getPrice() * qtys.get(i));
                psItem.addBatch(); // Gunakan batch untuk performa lebih baik

                // Update Stock
                psStock.setInt(1, qtys.get(i));
                psStock.setInt(2, cart.get(i).getId());
                psStock.addBatch();
            }
            
            psItem.executeBatch();
            psStock.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) { conn.setAutoCommit(true); conn.close(); }
        }
    }
}