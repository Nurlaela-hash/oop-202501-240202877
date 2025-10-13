# Laporan Praktikum Minggu 1

Topik: Paradigma prosedural, OOP, dan fungsional

## Identitas

- Nama : Nurlaela kusumandari
- NIM : 240202877
- Kelas : 3IKKA

---

## Tujuan

- Mahasiswa mampu mendefinisikan paradigma prosedural, OOP, dan fungsional.
- Mahasiswa mampu membandingkan kelebihan dan keterbatasan tiap paradigma.
- Mahasiswa mampu memberikan contoh program sederhana untuk masing-masing paradigma.

---

## Dasar Teori

1. Class adalah blueprint dari objek.
2. Object adalah instansiasi dari class.
3. Enkapsulasi digunakan untuk menyembunyikan data.

---

## Langkah Praktikum

1. **Setup Project**
   - Pastikan sudah menginstall **JDK** (Java Development Kit), **IDE** (misal: IntelliJ IDEA, VS Code, NetBeans), **Git**, **PostgreSQL**, dan **JavaFX** di komputer.
   - Buat folder project `oop-pos-<nim>`.
   - Inisialisasi repositori Git.
   - Buat struktur awal `src/main/java/com/upb/agripos/`.
   - Pastikan semua tools dapat berjalan (uji dengan membuat dan menjalankan program Java sederhana).

2. **Program Sederhana dalam 3 Paradigma**
   - Prosedural: program untuk menghitung total harga dua produk.
   - OOP: class `Produk` dengan atribut nama dan harga, buat minimal tiga objek, lalu hitung total.
   - Fungsional: gunakan `Stream` atau lambda untuk menghitung total harga dari minimal tiga objek.

3. **Commit dan Push**
   - Commit dengan pesan: `week1-setup-hello-pos`.

---

## Kode Program

```java
// Contoh
class Produk {
   String nama;
   int harga;
   Produk(String nama, int harga) {
      this.nama = nama;
      this.harga = harga;
   }
}

public class HelloOOP {
   public static void main(String[] args) {
      String nim = "240202877";
      String namaMhs = "Nurlaela kusumandari";
      Produk[] daftar = {
         new Produk("Beras", 10000),
         new Produk("Pupuk", 15000),
         new Produk("Benih", 12000)
      };
      int total = 0;
      System.out.println("Hello POS World");
      System.out.println("NIM: " + nim + ", Nama: " + namaMhs);
      System.out.println("Daftar Produk:");
      for (Produk p : daftar) {
         System.out.println("- " + p.nama + ": " + p.harga);
         total += p.harga;
      }
      System.out.println("Total harga semua produk: " + total);
   }
}
```

## )

## Hasil Eksekusi

## [Screenshot hasil](screenshots/HelloOOP.png)

## Analisis

(

- Program ini bekerja dengan membuat kelas Produk sebagai template yang memiliki variabel nama dan harga serta konstruktor untuk inisialisasi, lalu di kelas utama HelloOOP melalui metode main() mendefinisikan variabel identitas, membuat array objek Produk, melakukan iterasi dengan for-each untuk menampilkan nama dan harga setiap produk sambil menghitung totalnya, kemudian mencetak total harga ke layar melalui eksekusi oleh JVM.
- ini adalah minggu pertama
- Kendala yang saya hadapi pada kode helloOOP java adalah tidak bisa langsung dipanggil kodenya, dan harus dicompile terlebih dahulu dengan perintah `javac nama-file` pada terminal.  
  )

---

## Kesimpulan

(Tuliskan kesimpulan dari praktikum minggu ini.  
Contoh: _Dengan menggunakan class dan object, program menjadi lebih terstruktur dan mudah dikembangkan._)

---

## Quiz

(1. [Tuliskan kembali pertanyaan 1 dari panduan]  
 **Jawaban:** …

2. [Tuliskan kembali pertanyaan 2 dari panduan]  
   **Jawaban:** …

3. [Tuliskan kembali pertanyaan 3 dari panduan]  
   **Jawaban:** … )
