package Soal1;

import Soal1.controller.Perusahaan;

public class SistemManajemenKaryawanMain {
    public static void main(String[] args) {
        java.util.Scanner input = new java.util.Scanner(System.in);
        Perusahaan perusahaan = new Perusahaan();

        int pilihan;
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n=== SISTEM MANAJEMEN KARYAWAN ===");
            System.out.println("1. Tambah Karyawan");
            System.out.println("2. Hapus Karyawan");
            System.out.println("3. Ubah Posisi");
            System.out.println("4. Ubah Gaji");
            System.out.println("5. Tampilkan Semua Karyawan");
            System.out.println("6. Keluar");
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
                    System.out.print("Masukkan Gaji: ");
                    long gaji = input.nextLong();
                    perusahaan.tambahKaryawan(id, nama, posisi, gaji);
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
                    perusahaan.tampilkanSemuaKaryawan();
                    break;

                case 6:
                    System.out.println("Terima kasih telah menggunakan sistem manajemen karyawan.");
                    berjalan = false;
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        input.close();
    }
}