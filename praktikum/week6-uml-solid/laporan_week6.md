# Laporan Praktikum Minggu 6
Topik: Desain Arsitektur Sistem dengan UML dan Prinsip SOLID

## Identitas
- Nama  : Nurlaela Kusumandari
- NIM   : 240202877
- Kelas : 3IKKA

---

## Tujuan
- Mahasiswa mampu mengidentifikasi kebutuhan sistem ke dalam diagram UML.
- Mahasiswa mampu menggambar UML Class Diagram dengan relasi antar class yang tepat.
- Mahasiswa mampu menjelaskan prinsip desain OOP (SOLID).
- Mahasiswa mampu menerapkan minimal dua prinsip SOLID dalam kode program.

---

## Deskripsi Singkat Sistem
Agri-POS merupakan sistem kasir digital yang dirancang untuk mendukung proses penjualan produk pertanian seperti benih, pupuk, alat, dan obat pertanian. Sistem ini digunakan oleh dua aktor utama, yaitu **Kasir** dan **Admin**, dengan hak akses yang berbeda.

Kasir bertanggung jawab dalam proses transaksi penjualan, mulai dari memilih produk, menambahkan ke keranjang, menghitung total, hingga menyelesaikan pembayaran menggunakan metode tunai maupun e-wallet. Admin memiliki kewenangan untuk mengelola data produk serta melihat laporan penjualan harian maupun periodik.

Sistem Agri-POS dirancang menggunakan pendekatan **Object-Oriented Programming (OOP)**, dimodelkan dengan **Unified Modeling Language (UML)**, serta menerapkan prinsip **SOLID** agar sistem bersifat modular, mudah dikembangkan, dan mudah dipelihara.

---

## Penjelasan Diagram
### 1. Use Case Diagram
Use Case Diagram menggambarkan hubungan antara aktor dan fungsionalitas sistem Agri-POS.

**Aktor:**
- Kasir
- Admin

**Use Case utama:**
- Login
- Kelola Produk
- Checkout & Pembayaran
- Cetak Struk
- Lihat Laporan Penjualan

Use Case Diagram memastikan seluruh kebutuhan fungsional sistem tercakup dan menggambarkan batasan tanggung jawab masing-masing aktor.

---

### 2. Activity Diagram (Proses Checkout)
Activity Diagram menggambarkan alur aktivitas proses **Checkout** menggunakan swimlane **Kasir** dan **Sistem**.

Alur proses:
1. Kasir scan produk.
2. Sistem menghitung total pembayaran.
3. Kasir memilih metode pembayaran.
4. Sistem memproses pembayaran.
5. Jika pembayaran berhasil, struk ditampilkan atau dicetak.

Alur alternatif:
- Jika saldo tidak mencukupi, pembayaran gagal dan kasir diminta memilih metode pembayaran lain.

---

### 3. Sequence Diagram (Proses Pembayaran)
Sequence Diagram menggambarkan interaksi antar objek dalam proses pembayaran.

Objek utama:
- Kasir
- UI
- PaymentService
- PaymentMethod

Diagram menggunakan fragment **alt** untuk menggambarkan:
- Skenario pembayaran berhasil
- Skenario pembayaran gagal (saldo tidak mencukupi)

Sequence Diagram membantu memahami urutan pesan dan dependensi antar komponen sistem.

---

### 4. Class Diagram
Class Diagram menggambarkan struktur class dalam sistem Agri-POS, termasuk atribut, method, visibility, dan relasi antar class.

Paket utama:
- `com.upb.agripos.product`
- `com.upb.agripos.service`
- `com.upb.agripos.payment`
- `com.upb.agripos.repo`

Class:
- `Product`
- `CheckoutService`
- `PaymentService`
- `ProductService`
- `PaymentFactory`
- `PaymentMethod` (interface)
- `CashPayment`
- `EWalletPayment`
- `ProductRepository` (interface)
- `JdbcProductRepository`

Class Diagram menunjukkan penerapan relasi association, inheritance, dan composition secara jelas.

---

## Penjelasan Penerapan Prinsip SOLID

### 1. Single Responsibility Principle (SRP)
Setiap class hanya memiliki satu tanggung jawab.
Contoh:
- `ProductService` bertanggung jawab mengelola data produk.
- `PaymentService` bertanggung jawab memproses pembayaran.

### 2. Open/Closed Principle (OCP)
Class dapat diperluas tanpa mengubah kode yang sudah ada.
Contoh:
- Penambahan metode pembayaran baru dapat dilakukan dengan membuat class baru yang mengimplementasikan `PaymentMethod`.

### 3. Liskov Substitution Principle (LSP)
Class turunan dapat menggantikan class induknya tanpa mengubah perilaku sistem.
Contoh:
- `CashPayment` dan `EWalletPayment` dapat digunakan sebagai `PaymentMethod`.

### 4. Interface Segregation Principle (ISP)
Interface dibuat spesifik dan tidak memaksa class mengimplementasikan method yang tidak dibutuhkan.
Contoh:
- Interface `PaymentMethod` hanya berisi method `pay(amount)`.

### 5. Dependency Inversion Principle (DIP)
High-level module bergantung pada abstraksi, bukan implementasi konkret.
Contoh:
- `PaymentService` bergantung pada interface `PaymentMethod`, bukan pada `CashPayment` atau `EWalletPayment`.

---

## Kesimpulan dan Refleksi
Dengan menggunakan UML dan prinsip SOLID, desain sistem Agri-POS menjadi lebih terstruktur, modular, dan mudah dikembangkan. Penerapan prinsip SOLID membantu meminimalkan ketergantungan antar class serta meningkatkan kualitas desain perangkat lunak. Desain ini juga memudahkan pengembangan fitur baru di masa depan tanpa mengubah struktur sistem yang sudah ada.

---

## Quiz
1. Jelaskan perbedaan aggregation dan composition serta berikan contoh penerapannya pada desain Anda.  
   **Jawaban:**  
   Aggregation adalah hubungan lemah antara objek, di mana objek bagian dapat berdiri sendiri, contohnya relasi antara Admin dan Laporan Penjualan.  
   Composition adalah hubungan kuat antara objek, di mana objek bagian tidak dapat berdiri sendiri tanpa objek induk, contohnya relasi antara Transaksi dan TransactionItem.

2. Bagaimana prinsip Open/Closed dapat memastikan sistem mudah dikembangkan?  
   **Jawaban:**  
   Prinsip Open/Closed memastikan sistem mudah dikembangkan karena penambahan fitur baru dilakukan dengan membuat class baru tanpa memodifikasi class yang sudah ada.

3. Mengapa Dependency Inversion Principle (DIP) meningkatkan testability? Berikan contoh penerapannya.  
   **Jawaban:**  
   DIP meningkatkan testability karena dependensi menggunakan interface sehingga dapat diganti dengan implementasi tiruan saat pengujian. Contohnya, `PaymentService` dapat diuji menggunakan `PaymentMethod` versi tiruan tanpa bergantung pada proses pembayaran yang sebenarnya.
