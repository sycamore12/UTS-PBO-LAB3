package Soal1.controller;

import Soal1.model.Karyawan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Perusahaan implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Karyawan> daftarKaryawan;
    private final String FILE_PATH = "karyawan_data.ser";

    // Constructor
    public Perusahaan() {
        daftarKaryawan = new ArrayList<>();
        bacaDariFile(); // Membaca data dari file saat instance dibuat
    }

    // Method untuk mencari karyawan berdasarkan ID
    private int cariKaryawan(String id) {
        for (int i = 0; i < daftarKaryawan.size(); i++) {
            if (daftarKaryawan.get(i).getId().equals(id)) {
                return i; // Mengembalikan indeks karyawan jika ditemukan
            }
        }
        return -1; // Mengembalikan -1 jika tidak ditemukan
    }

    // Method untuk menambahkan karyawan baru
    public boolean tambahKaryawan(String id, String nama, String posisi, long gaji, String divisi, String tanggalBergabung) {
        // Validasi ID tidak boleh duplikat
        if (cariKaryawan(id) != -1) {
            System.out.println("Karyawan dengan ID " + id + " sudah ada!");
            return false;
        }

        // Validasi gaji tidak boleh negatif
        if (gaji < 0) {
            System.out.println("Gaji tidak boleh negatif!");
            return false;
        }

        // Tambahkan karyawan baru
        daftarKaryawan.add(new Karyawan(id, nama, posisi, gaji, divisi, tanggalBergabung));
        System.out.println("Karyawan berhasil ditambahkan.");
        simpanKeFile();
        return true;
    }

    // Method untuk menambahkan karyawan (overload untuk kompatibilitas)
    public boolean tambahKaryawan(String id, String nama, String posisi, long gaji) {
        return tambahKaryawan(id, nama, posisi, gaji, "Umum", "01-01-2023");
    }

    // Method untuk menghapus karyawan
    public boolean hapusKaryawan(String id) {
        int indeks = cariKaryawan(id);
        if (indeks == -1) {
            System.out.println("Karyawan dengan ID " + id + " tidak ditemukan!");
            return false;
        }

        daftarKaryawan.remove(indeks);
        System.out.println("Karyawan berhasil dihapus.");
        simpanKeFile();
        return true;
    }

    // Method untuk mengubah posisi karyawan
    public boolean ubahPosisi(String id, String posisiBaru) {
        int indeks = cariKaryawan(id);
        if (indeks == -1) {
            System.out.println("Karyawan dengan ID " + id + " tidak ditemukan!");
            return false;
        }

        daftarKaryawan.get(indeks).setPosisi(posisiBaru);
        System.out.println("Posisi berhasil diubah.");
        simpanKeFile();
        return true;
    }

    // Method untuk mengubah gaji karyawan
    public boolean ubahGaji(String id, long gajiBaru) {
        // Validasi gaji tidak boleh negatif
        if (gajiBaru < 0) {
            System.out.println("Gaji tidak boleh negatif!");
            return false;
        }

        int indeks = cariKaryawan(id);
        if (indeks == -1) {
            System.out.println("Karyawan dengan ID " + id + " tidak ditemukan!");
            return false;
        }

        daftarKaryawan.get(indeks).setGaji(gajiBaru);
        System.out.println("Gaji berhasil diubah.");
        simpanKeFile();
        return true;
    }

    // Method untuk mengubah divisi karyawan
    public boolean ubahDivisi(String id, String divisiBaru) {
        int indeks = cariKaryawan(id);
        if (indeks == -1) {
            System.out.println("Karyawan dengan ID " + id + " tidak ditemukan!");
            return false;
        }

        daftarKaryawan.get(indeks).setDivisi(divisiBaru);
        System.out.println("Divisi berhasil diubah.");
        simpanKeFile();
        return true;
    }

    // Method untuk menampilkan semua karyawan
    public void tampilkanSemuaKaryawan() {
        if (daftarKaryawan.isEmpty()) {
            System.out.println("Tidak ada karyawan yang terdaftar.");
            return;
        }

        System.out.println("\n=== DAFTAR KARYAWAN ===");
        for (Karyawan k : daftarKaryawan) {
            System.out.println("\n" + k);
            System.out.println("------------------------");
        }
    }

    // Method untuk mendapatkan semua karyawan (untuk GUI)
    public List<Karyawan> getDaftarKaryawan() {
        return new ArrayList<>(daftarKaryawan);
    }

    // Method untuk menghitung total gaji
    public long hitungTotalGaji() {
        long total = 0;
        for (Karyawan k : daftarKaryawan) {
            total += k.getGaji();
        }
        return total;
    }

    // Method untuk menyimpan data ke file
    public void simpanKeFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(daftarKaryawan);
            System.out.println("Data berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Error saat menyimpan data: " + e.getMessage());
        }
    }

    // Method untuk membaca data dari file
    @SuppressWarnings("unchecked")
    public void bacaDariFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File data tidak ditemukan. Membuat daftar karyawan baru.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            daftarKaryawan = (List<Karyawan>) obj;
            System.out.println("Data berhasil dimuat dari file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error saat membaca data: " + e.getMessage());
            daftarKaryawan = new ArrayList<>(); // Reset jika terjadi error
        }
    }

    // Method untuk mencari karyawan berdasarkan kriteria nama
    public List<Karyawan> cariByNama(String namaCari) {
        return daftarKaryawan.stream()
                .filter(k -> k.getNama().toLowerCase().contains(namaCari.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Method untuk mencari karyawan berdasarkan kriteria posisi
    public List<Karyawan> cariByPosisi(String posisiCari) {
        return daftarKaryawan.stream()
                .filter(k -> k.getPosisi().toLowerCase().contains(posisiCari.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Method untuk mencari karyawan berdasarkan kriteria divisi
    public List<Karyawan> cariByDivisi(String divisiCari) {
        return daftarKaryawan.stream()
                .filter(k -> k.getDivisi().toLowerCase().contains(divisiCari.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Method untuk filter karyawan berdasarkan range gaji
    public List<Karyawan> filterByGaji(long min, long max) {
        return daftarKaryawan.stream()
                .filter(k -> k.getGaji() >= min && k.getGaji() <= max)
                .collect(Collectors.toList());
    }

    // Method untuk mendapatkan karyawan berdasarkan ID
    public Karyawan getKaryawanById(String id) {
        int index = cariKaryawan(id);
        if (index == -1) {
            return null;
        }
        return daftarKaryawan.get(index);
    }
}