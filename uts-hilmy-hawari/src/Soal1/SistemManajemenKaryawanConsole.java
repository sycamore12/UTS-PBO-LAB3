package Soal1;

import Soal1.controller.Perusahaan;
import Soal1.model.Karyawan;

import java.util.List;
import java.util.Scanner;

public class SistemManajemenKaryawanConsole {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Perusahaan perusahaan = new Perusahaan();

        int pilihan;
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n=== SISTEM MANAJEMEN KARYAWAN ===");
            System.out.println("1. Tambah Karyawan");
            System.out.println("2. Hapus Karyawan");
            System.out.println("3. Ubah Posisi");
            System.out.println("4. Ubah Gaji");
            System.out.println("5. Ubah Divisi");
            System.out.println("6. Tampilkan Semua Karyawan");
            System.out.println("7. Cari Karyawan Berdasarkan Nama");
            System.out.println("8. Cari Karyawan Berdasarkan Posisi");
            System.out.println("9. Cari Karyawan Berdasarkan Divisi");
            System.out.println("10. Lihat Total Gaji Karyawan");
            System.out.println("11. Filter Karyawan Berdasarkan Range Gaji");
            System.out.println("12. Keluar");
            System.out.print("Masukkan pilihan: ");

            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID: ");
                    String id = input.nextLine();
                    System.out.print("Masukkan Nama: ");
                    String nama = input.nextLine();
                    System.out.print("Masukkan Posisi: ");
                    String posisi = input.nextLine();
                    System.out.print("Masukkan Divisi: ");
                    String divisi = input.nextLine();
                    System.out.print("Masukkan Gaji: ");
                    long gaji = input.nextLong();
                    input.nextLine(); // Membersihkan buffer
                    System.out.print("Masukkan Tanggal Bergabung (dd-mm-yyyy): ");
                    String tanggalBergabung = input.nextLine();
                    perusahaan.tambahKaryawan(id, nama, posisi, gaji, divisi, tanggalBergabung);
                    break;

                case 2:
                    System.out.print("Masukkan ID karyawan yang akan dihapus: ");
                    id = input.nextLine();
                    perusahaan.hapusKaryawan(id);
                    break;

                case 3:
                    System.out.print("Masukkan ID karyawan: ");
                    id = input.nextLine();
                    System.out.print("Masukkan posisi baru: ");
                    String posisiBaru = input.nextLine();
                    perusahaan.ubahPosisi(id, posisiBaru);
                    break;

                case 4:
                    System.out.print("Masukkan ID karyawan: ");
                    id = input.nextLine();
                    System.out.print("Masukkan gaji baru: ");
                    long gajiBaru = input.nextLong();
                    perusahaan.ubahGaji(id, gajiBaru);
                    break;

                case 5:
                    System.out.print("Masukkan ID karyawan: ");
                    id = input.nextLine();
                    System.out.print("Masukkan divisi baru: ");
                    String divisiBaru = input.nextLine();
                    perusahaan.ubahDivisi(id, divisiBaru);
                    break;

                case 6:
                    perusahaan.tampilkanSemuaKaryawan();
                    break;

                case 7:
                    System.out.print("Masukkan nama yang dicari: ");
                    String namaCari = input.nextLine();
                    List<Karyawan> hasilNama = perusahaan.cariByNama(namaCari);
                    tampilkanHasilPencarian(hasilNama);
                    break;

                case 8:
                    System.out.print("Masukkan posisi yang dicari: ");
                    String posisiCari = input.nextLine();
                    List<Karyawan> hasilPosisi = perusahaan.cariByPosisi(posisiCari);
                    tampilkanHasilPencarian(hasilPosisi);
                    break;

                case 9:
                    System.out.print("Masukkan divisi yang dicari: ");
                    String divisiCari = input.nextLine();
                    List<Karyawan> hasilDivisi = perusahaan.cariByDivisi(divisiCari);
                    tampilkanHasilPencarian(hasilDivisi);
                    break;

                case 10:
                    long totalGaji = perusahaan.hitungTotalGaji();
                    System.out.println("Total gaji seluruh karyawan: Rp" + totalGaji);
                    break;

                case 11:
                    System.out.print("Masukkan gaji minimum: ");
                    long minGaji = input.nextLong();
                    System.out.print("Masukkan gaji maksimum: ");
                    long maxGaji = input.nextLong();
                    List<Karyawan> hasilFilter = perusahaan.filterByGaji(minGaji, maxGaji);
                    tampilkanHasilPencarian(hasilFilter);
                    break;

                case 12:
                    System.out.println("Terima kasih telah menggunakan sistem manajemen karyawan.");
                    perusahaan.simpanKeFile(); // Menyimpan data terakhir sebelum keluar
                    berjalan = false;
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        input.close();
    }

    // Method untuk menampilkan hasil pencarian
    private static void tampilkanHasilPencarian(List<Karyawan> hasil) {
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada karyawan yang sesuai dengan kriteria pencarian.");
            return;
        }

        System.out.println("\n=== HASIL PENCARIAN ===");
        for (Karyawan k : hasil) {
            System.out.println("\n" + k);
            System.out.println("------------------------");
        }
        System.out.println("Jumlah karyawan yang ditemukan: " + hasil.size());
    }
}