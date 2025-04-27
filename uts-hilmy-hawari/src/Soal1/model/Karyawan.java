package Soal1.model;

import java.io.Serializable;

public class Karyawan implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nama;
    private String posisi;
    private long gaji;
    private String divisi;
    private String tanggalBergabung;

    // Constructor untuk membuat objek Karyawan baru
    public Karyawan(String id, String nama, String posisi, long gaji, String divisi, String tanggalBergabung) {
        this.id = id;
        this.nama = nama;
        this.posisi = posisi;
        this.gaji = gaji;
        this.divisi = divisi;
        this.tanggalBergabung = tanggalBergabung;
    }

    // Constructor simpel untuk kompatibilitas
    public Karyawan(String id, String nama, String posisi, long gaji) {
        this(id, nama, posisi, gaji, "Umum", "01-01-2023");
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public long getGaji() {
        return gaji;
    }

    public void setGaji(long gaji) {
        if (gaji >= 0) {
            this.gaji = gaji;
        } else {
            System.out.println("Gaji tidak boleh negatif!");
        }
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getTanggalBergabung() {
        return tanggalBergabung;
    }

    public void setTanggalBergabung(String tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }

    // Method untuk menampilkan informasi karyawan
    @Override
    public String toString() {
        return "ID: " + id +
                "\nNama: " + nama +
                "\nPosisi: " + posisi +
                "\nDivisi: " + divisi +
                "\nGaji: " + gaji +
                "\nTanggal Bergabung: " + tanggalBergabung;
    }
}
