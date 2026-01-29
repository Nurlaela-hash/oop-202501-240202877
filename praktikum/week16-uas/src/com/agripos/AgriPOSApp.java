package com.agripos;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class AgriPOSApp extends Application {
    private ProductDAO dao = new ProductDAO();
    
    // Data List
    private ObservableList<Product> products = FXCollections.observableArrayList();
    // Menggunakan CartItem (Class Baru di bawah) agar tabel bisa menampilkan subtotal
    private ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    
    // UI Components untuk Kalkulasi
    private Label lblTotal = new Label("Total: Rp 0");
    private Label lblKembalian = new Label("Kembalian: Rp 0");
    private TextField tBayar = new TextField();
    private double totalTransaction = 0;

    // UI Components untuk Edit Gudang
    private TextField tId = new TextField(); // Hidden field untuk menyimpan ID saat edit
    private TextField tName = new TextField(); 
    private TextField tCat = new TextField(); 
    private TextField tPrice = new TextField(); 
    private TextField tStock = new TextField();
    
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        TabPane tabs = new TabPane();
        tabs.getTabs().addAll(new Tab("Kasir", createPOS()), new Tab("Gudang", createInventory()));
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        stage.setScene(new Scene(tabs, 950, 650));
        stage.setTitle("Agri-POS System V2");
        stage.show();
        refreshData();
    }

    // ================== TAB GUDANG (INVENTORY) ==================
    private VBox createInventory() {
        VBox layout = new VBox(10); layout.setPadding(new Insets(15));
        
        tName.setPromptText("Nama Produk");
        tCat.setPromptText("Kategori");
        tPrice.setPromptText("Harga");
        tStock.setPromptText("Stok");
        
        Button btnAdd = new Button("Tambah Baru");
        Button btnUpdate = new Button("Update / Simpan Edit");
        Button btnClear = new Button("Batal / Bersihkan");
        
        btnAdd.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnUpdate.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");

        TableView<Product> table = new TableView<>();
        table.getColumns().add(col("ID", "id"));
        table.getColumns().add(col("Nama", "name"));
        table.getColumns().add(col("Kategori", "category"));
        table.getColumns().add(col("Harga", "price"));
        table.getColumns().add(col("Stok", "stock"));
        table.setItems(products); 
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Event: Klik Tabel untuk Edit
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tId.setText(String.valueOf(newSelection.getId()));
                tName.setText(newSelection.getName());
                tCat.setText(newSelection.getCategory());
                tPrice.setText(String.valueOf((int)newSelection.getPrice()));
                tStock.setText(String.valueOf(newSelection.getStock()));
                btnAdd.setDisable(true); // Matikan tombol tambah saat mode edit
                btnUpdate.setDisable(false);
            }
        });

        // Aksi Tambah
        btnAdd.setOnAction(e -> {
            try {
                dao.addProduct(new Product(tName.getText(), tCat.getText(), 
                    Double.parseDouble(tPrice.getText()), Integer.parseInt(tStock.getText())));
                refreshData(); clearForm();
            } catch (Exception ex) { alert("Error: " + ex.getMessage()); }
        });

        // Aksi Update (Edit)
        btnUpdate.setOnAction(e -> {
            if (tId.getText().isEmpty()) return;
            try {
                int id = Integer.parseInt(tId.getText());
                dao.updateProduct(new Product(id, tName.getText(), tCat.getText(), 
                    Double.parseDouble(tPrice.getText()), Integer.parseInt(tStock.getText())));
                refreshData(); clearForm();
                alert("Data berhasil diupdate!");
            } catch (Exception ex) { alert("Error Update: " + ex.getMessage()); }
        });

        // Aksi Clear
        btnClear.setOnAction(e -> {
            table.getSelectionModel().clearSelection();
            clearForm();
        });

        HBox form = new HBox(10, tName, tCat, tPrice, tStock);
        HBox buttons = new HBox(10, btnAdd, btnUpdate, btnClear);
        
        layout.getChildren().addAll(new Label("Manajemen Gudang"), form, buttons, table);
        
        clearForm(); // Set state awal
        return layout;
    }
    
    private void clearForm() {
        tId.clear(); tName.clear(); tCat.clear(); tPrice.clear(); tStock.clear();
        // Cari tombol update dan disable, enable add (perlu akses variabel, dilakukan via logic)
        // Di sini kita asumsikan state default
    }

    // ================== TAB KASIR (POS) ==================
    private BorderPane createPOS() {
        BorderPane layout = new BorderPane(); layout.setPadding(new Insets(15));
        
        ComboBox<Product> cmb = new ComboBox<>(products); 
        cmb.setPromptText("Pilih Produk"); cmb.setPrefWidth(250);
        
        TextField tQty = new TextField(); tQty.setPromptText("Jml"); tQty.setPrefWidth(60);
        Button btnAdd = new Button("Tambah ke Keranjang");

        // Tabel Keranjang menggunakan Class 'CartItem'
        TableView<CartItem> table = new TableView<>(cartItems);
        table.getColumns().add(colCart("Produk", "productName"));
        table.getColumns().add(colCart("Harga Satuan", "unitPrice"));
        table.getColumns().add(colCart("Jml", "quantity"));
        table.getColumns().add(colCart("Subtotal", "subtotal")); // Kolom Subtotal
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // Panel Pembayaran (Kanan Bawah)
        VBox payPanel = new VBox(10);
        payPanel.setPadding(new Insets(20));
        payPanel.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7;");
        
        lblTotal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        lblKembalian.setStyle("-fx-font-size: 16px; -fx-text-fill: blue;");
        
        tBayar.setPromptText("Input Uang Tunai");
        Button btnPay = new Button("PROSES BAYAR"); 
        btnPay.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        btnPay.setPrefWidth(200);

        // Logic Hitung Kembalian Otomatis
        tBayar.textProperty().addListener((obs, oldVal, newVal) -> {
            calculateChange();
        });

        btnAdd.setOnAction(e -> {
            Product p = cmb.getValue();
            if (p != null && !tQty.getText().isEmpty()) {
                try {
                    int q = Integer.parseInt(tQty.getText());
                    if (q <= 0) { alert("Jumlah harus > 0"); return; }
                    if (q > p.getStock()) { alert("Stok tidak cukup! Sisa: " + p.getStock()); return; }
                    
                    // Tambahkan ke CartItem Wrapper
                    cartItems.add(new CartItem(p, q));
                    recalculateTotal();
                    tQty.clear(); cmb.getSelectionModel().clearSelection();
                } catch (NumberFormatException ex) { alert("Masukkan angka valid"); }
            }
        });

        btnPay.setOnAction(e -> {
            if (cartItems.isEmpty()) { alert("Keranjang kosong!"); return; }
            double bayar = 0;
            try { bayar = Double.parseDouble(tBayar.getText()); } catch(Exception ex) {}
            
            if (bayar < totalTransaction) {
                alert("Uang tunai kurang!");
                return;
            }

            try {
                // Konversi CartItem kembali ke List<Product> dan List<Integer> untuk DAO
                List<Product> pList = new ArrayList<>();
                List<Integer> qList = new ArrayList<>();
                for (CartItem item : cartItems) {
                    pList.add(item.getProduct());
                    qList.add(item.getQuantity());
                }

                dao.processTransaction(pList, qList);
                alert("Transaksi Berhasil!\nKembalian: Rp " + (long)(bayar - totalTransaction));
                
                // Reset
                cartItems.clear(); 
                tBayar.clear(); 
                recalculateTotal(); 
                refreshData(); // Refresh stok di gudang
            } catch (Exception ex) { alert("Gagal DB: " + ex.getMessage()); ex.printStackTrace(); }
        });

        HBox topInput = new HBox(10, cmb, tQty, btnAdd);
        topInput.setAlignment(Pos.CENTER_LEFT);
        
        payPanel.getChildren().addAll(lblTotal, new Separator(), new Label("Bayar (Cash):"), tBayar, lblKembalian, new Separator(), btnPay);
        
        layout.setTop(topInput);
        layout.setCenter(table);
        layout.setRight(payPanel);
        BorderPane.setMargin(table, new Insets(10,0,0,0));
        
        return layout;
    }

    // Helper: Hitung Total Keranjang
    private void recalculateTotal() {
        totalTransaction = 0;
        for (CartItem item : cartItems) {
            totalTransaction += item.getSubtotal();
        }
        lblTotal.setText("Total: Rp " + String.format("%,.0f", totalTransaction));
        calculateChange(); // update kembalian juga jika total berubah
    }

    // Helper: Hitung Kembalian
    private void calculateChange() {
        try {
            double bayar = Double.parseDouble(tBayar.getText());
            double kembalian = bayar - totalTransaction;
            lblKembalian.setText("Kembalian: Rp " + String.format("%,.0f", kembalian));
            if (kembalian < 0) lblKembalian.setStyle("-fx-text-fill: red;");
            else lblKembalian.setStyle("-fx-text-fill: blue;");
        } catch (NumberFormatException e) {
            lblKembalian.setText("Kembalian: Rp 0");
        }
    }

    // Helper Columns
    private <T> TableColumn<Product, T> col(String title, String prop) {
        TableColumn<Product, T> c = new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(prop));
        return c;
    }
    
    // Helper Column khusus Cart (karena pakai class berbeda)
    private <T> TableColumn<CartItem, T> colCart(String title, String prop) {
        TableColumn<CartItem, T> c = new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(prop));
        return c;
    }

    private void refreshData() {
        try { products.setAll(dao.getAllProducts()); } catch (Exception e) {}
    }

    private void alert(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).show(); }

    // ================== INNER CLASS UNTUK KERANJANG ==================
    // Ini memperbaiki bug tampilan harga agar bisa menampilkan subtotal per baris
    public static class CartItem {
        private Product product;
        private int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public String getProductName() { return product.getName(); }
        public double getUnitPrice() { return product.getPrice(); }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return product.getPrice() * quantity; }
    }
}