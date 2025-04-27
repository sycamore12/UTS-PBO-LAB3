package Soal2;

import java.util.ArrayList;
import java.util.Scanner;

public class ParkirChan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();

        // Tampilkan header
        System.out.println("======== Welcome to ParkingChan ========");

        boolean tambahLagi = true;

        while (tambahLagi) {
            // Input jenis kendaraan
            System.out.print("Enter vehicle type (Motor/Mobil/Truk) : ");
            String jenisKendaraan = input.next();

            // Buat objek kendaraan
            Kendaraan kendaraan = new Kendaraan(jenisKendaraan);

            // Input metode durasi
            System.out.print("Enter Duration (Manual/Time): ");
            String metodeDurasi = input.next();

            if (metodeDurasi.equalsIgnoreCase("Manual")) {
                // Input durasi manual
                System.out.print("Enter Duration (in hour): ");
                int jam = input.nextInt();
                kendaraan.hitungBiaya(jam);
            } else if (metodeDurasi.equalsIgnoreCase("Time")) {
                // Input jam masuk dan keluar
                System.out.print("Enter entry time  : ");
                int jamMasuk = input.nextInt();
                System.out.print("Enter exit time   : ");
                int jamKeluar = input.nextInt();
                kendaraan.hitungBiaya(jamMasuk, jamKeluar);
            }

            // Tampilkan ringkasan
            kendaraan.tampilkanRingkasan();

            // Tambahkan ke daftar
            daftarKendaraan.add(kendaraan);

            // Tanya tambah kendaraan lagi?
            System.out.print("Add another vehicle? (y/n): ");
            String jawaban = input.next();
            tambahLagi = jawaban.equalsIgnoreCase("y");
            System.out.println();
        }

        // Tampilkan ringkasan akhir
        System.out.println("======== FINAL REPORT ========");
        System.out.println("Total Vehicle Final\t: " + daftarKendaraan.size());

        // Hitung total biaya parkir
        double totalBiaya = 0;
        for (Kendaraan k : daftarKendaraan) {
            totalBiaya += k.getBiayaParkir();
        }

        System.out.println("Total Parking Fees Final\t: " + totalBiaya);
        System.out.println("Thank You....");

        input.close();
    }
}