# Praktikum OOP – Agri-POS (20251)

Repositori ini adalah **template praktikum** untuk mata kuliah *Pemrograman Berorientasi Objek (OOP)* semester gasal 2025/2026, dengan studi kasus **Agri-POS (Point of Sales Toko Pertanian)**.

Mahasiswa akan membangun aplikasi kasir pertanian secara bertahap, mulai dari konsep dasar OOP hingga aplikasi terintegrasi dengan basis data dan GUI.

---

## Instruksi untuk Mahasiswa
1. **Fork** repositori ini ke akun Git masing-masing.
2. **Rename** hasil fork menjadi:
   ```
   oop-20251-<nim>
   ```
   Contoh: `oop-20251-2310112345`
3. **Clone** ke komputer lokal:
   ```bash
   git clone https://github.com/<username>/oop-20251-<nim>.git
   ```
4. Kerjakan tugas mingguan sesuai instruksi di folder `docs/`.
5. Simpan kode program di dalam folder `src/` dengan struktur yang rapi.
6. Setiap minggu lakukan **commit & push** dengan format pesan:
   - `week1-setup-hello-pos`
   - `week2-class-object`
   - `week3-inheritance`
   - dst.
7. Setiap tugas wajib menyertakan:

   - **Kode program**

   - **Screenshot hasil eksekusi**

   - **Commit log** sesuai instruksi

   - **Laporan singkat** (PDF/Markdown) berisi penjelasan kode dan hasil percobaan


---

## Struktur Folder
```
oop-202501/
 ├─ README.md                # Panduan umum repositori
 ├─ docs/                    # Panduan praktikum mingguan (Markdown)
 │   ├─ 00_pendahuluan.md
 │   ├─ 01_bab1_paradigma_setup.md
 │   ├─ 02_bab2_class_object.md
 │   └─ ...
 ├─ praktikum/               # Folder tugas mingguan
 │   ├─ week1-setup-hello-pos/
 │   ├─ week2-class-object/
 │   ├─ week3-inheritance/
 │   ├─ week4-polymorphism/
 │   ├─ week5-abstraction-interface/
 │   ├─ week6-uml-solid/
 │   ├─ week7-koleksi-keranjang/
 │   ├─ week8-uts/
 │   ├─ week9-exception-handling/
 │   ├─ week10-pattern-testing/
 │   ├─ week11-dao-database/
 │   ├─ week12-gui-dasar/
 │   ├─ week13-gui-lanjutan/
 │   ├─ week14-integrasi-individu/
 │   ├─ week15-proyek-kelompok/
 │   └─ week16-uas/
 ├─ sql/                     # Skema dan seed database PostgreSQL
 │   ├─ schema.sql
 │   └─ seed.sql
 └─ src/                     # Source code Java Tugas Akhir/ integrasi
    ├─ main/java/com/upb/agripos/
    └─ test/java/com/upb/agripos/
```

---

## Sistem Penilaian
Mengacu pada RPS:
- Tugas (Quiz, Laporan): **27%**  
- Penilaian Partisipatif: **5%**  
- UTS: **14%**  
- Proyek (Praktikum): **34%**  
- UAS: **20%**

---

## Referensi Utama
- Liang, Y. D. *Introduction to Java Programming and Data Structures*.  
- Horstmann, C. S. *Core Java Volume I – Fundamentals*.  
- Schildt, H. *Java: The Complete Reference*.  
- PostgreSQL Official Documentation.  
- OpenJFX Documentation.  

---

## Lisensi
Proyek ini menggunakan lisensi MIT. Silakan gunakan dan modifikasi dengan tetap mencantumkan atribusi.
