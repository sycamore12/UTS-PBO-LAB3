package Soal2;

// Class untuk mencatat info kendaraan
class Kendaraan {
    private String jenisKendaraan;
    private int lamaJam;
    private double biayaParkir;

    // Constructor
    public Kendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    // Method overloading untuk menghitung biaya parkir
    // 1. Durasi parkir manual
    public void hitungBiaya(int jam) {
        this.lamaJam = jam;
        hitungBiayaParkir();
    }

    // 2. Berdasarkan jam masuk dan keluar
    public void hitungBiaya(int jamMasuk, int jamKeluar) {
        if (jamKeluar < jamMasuk) {
            jamKeluar += 24; // Asumsi parkir melewati tengah malam
        }
        this.lamaJam = jamKeluar - jamMasuk;
        hitungBiayaParkir();
    }

    // Method untuk menghitung biaya berdasarkan jenis kendaraan
    private void hitungBiayaParkir() {
        double tarif = 0;

        // Menentukan tarif sesuai jenis kendaraan
        if (jenisKendaraan.equalsIgnoreCase("Motor")) {
            tarif = 2000.0;
        } else if (jenisKendaraan.equalsIgnoreCase("Mobil")) {
            tarif = 5000.0;
        } else if (jenisKendaraan.equalsIgnoreCase("Truk")) {
            tarif = 8000.0;
        }

        // Menghitung biaya total
        biayaParkir = tarif * lamaJam;

        // Diskon 10% jika parkir lebih dari 5 jam
        if (lamaJam > 5) {
            biayaParkir = biayaParkir * 0.9; // Diskon 10%
        }
    }

    // Getter methods
    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public int getLamaJam() {
        return lamaJam;
    }

    public double getBiayaParkir() {
        return biayaParkir;
    }

    // Method untuk menampilkan ringkasan
    public void tampilkanRingkasan() {
        System.out.println("----- PARKING SUMMARY -----");
        System.out.println("Vehicle Type\t: " + jenisKendaraan);
        System.out.println("Parking Time\t: " + lamaJam + " hour(s)");
        System.out.println("Total Fee\t: Rp" + biayaParkir);
        System.out.println();
    }
}