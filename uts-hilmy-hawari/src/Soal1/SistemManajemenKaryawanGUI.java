package Soal1;

import Soal1.controller.Perusahaan;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Soal1.model.Karyawan;

import java.util.List;
import java.util.Optional;

public class SistemManajemenKaryawanGUI extends Application {
    private Perusahaan perusahaan;
    private TableView<Karyawan> tableView;
    private TextField tfId, tfNama, tfPosisi, tfGaji, tfDivisi, tfTanggalBergabung;
    private TextField tfCari;
    private ComboBox<String> cbKriteriaCari;

    @Override
    public void start(Stage primaryStage) {
        perusahaan = new Perusahaan();

        // Membuat layout utama
        BorderPane root = new BorderPane();

        // Form untuk input data karyawan
        GridPane formGrid = createFormPane();

        // Tabel untuk menampilkan data karyawan
        tableView = createTableView();

        // Panel untuk aksi (tambah, hapus, ubah)
        HBox actionButtons = createActionPane();

        // Panel untuk pencarian
        HBox searchPane = createSearchPane();

        // Panel untuk tombol lainnya
        HBox otherButtons = createOtherButtonsPane();

        // Menyusun layout
        VBox leftVBox = new VBox(10);
        leftVBox.setPadding(new Insets(10));
        leftVBox.getChildren().addAll(formGrid, actionButtons, searchPane, otherButtons);

        root.setLeft(leftVBox);
        root.setCenter(tableView);

        // Membuat scene
        Scene scene = new Scene(root, 1000, 600);

        // Menampilkan window
        primaryStage.setTitle("Sistem Manajemen Karyawan");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Memuat data awal
        refreshTable();
    }

    private GridPane createFormPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        // Label
        grid.add(new Label("ID:"), 0, 0);
        grid.add(new Label("Nama:"), 0, 1);
        grid.add(new Label("Posisi:"), 0, 2);
        grid.add(new Label("Divisi:"), 0, 3);
        grid.add(new Label("Gaji:"), 0, 4);
        grid.add(new Label("Tanggal Bergabung:"), 0, 5);

        // TextField
        tfId = new TextField();
        tfNama = new TextField();
        tfPosisi = new TextField();
        tfDivisi = new TextField();
        tfGaji = new TextField();
        tfTanggalBergabung = new TextField();

        grid.add(tfId, 1, 0);
        grid.add(tfNama, 1, 1);
        grid.add(tfPosisi, 1, 2);
        grid.add(tfDivisi, 1, 3);
        grid.add(tfGaji, 1, 4);
        grid.add(tfTanggalBergabung, 1, 5);

        return grid;
    }

    private TableView<Karyawan> createTableView() {
        TableView<Karyawan> table = new TableView<>();

        TableColumn<Karyawan, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Karyawan, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Karyawan, String> posisiCol = new TableColumn<>("Posisi");
        posisiCol.setCellValueFactory(new PropertyValueFactory<>("posisi"));

        TableColumn<Karyawan, String> divisiCol = new TableColumn<>("Divisi");
        divisiCol.setCellValueFactory(new PropertyValueFactory<>("divisi"));

        TableColumn<Karyawan, Long> gajiCol = new TableColumn<>("Gaji");
        gajiCol.setCellValueFactory(new PropertyValueFactory<>("gaji"));

        TableColumn<Karyawan, String> tanggalCol = new TableColumn<>("Tanggal Bergabung");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggalBergabung"));

        table.getColumns().addAll(idCol, namaCol, posisiCol, divisiCol, gajiCol, tanggalCol);

        // Menambahkan event handler untuk selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfId.setText(newSelection.getId());
                tfNama.setText(newSelection.getNama());
                tfPosisi.setText(newSelection.getPosisi());
                tfDivisi.setText(newSelection.getDivisi());
                tfGaji.setText(String.valueOf(newSelection.getGaji()));
                tfTanggalBergabung.setText(newSelection.getTanggalBergabung());

                // Disable ID field saat mengedit
                tfId.setDisable(true);
            }
        });

        return table;
    }

    private HBox createActionPane() {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10, 0, 0, 0));

        Button btnTambah = new Button("Tambah");
        Button btnClear = new Button("Clear");
        Button btnHapus = new Button("Hapus");
        Button btnUpdate = new Button("Update");

        btnTambah.setPrefWidth(90);
        btnClear.setPrefWidth(90);
        btnHapus.setPrefWidth(90);
        btnUpdate.setPrefWidth(90);

        // Event handler untuk tambah
        btnTambah.setOnAction(e -> {
            try {
                String id = tfId.getText();
                String nama = tfNama.getText();
                String posisi = tfPosisi.getText();
                String divisi = tfDivisi.getText();
                long gaji = Long.parseLong(tfGaji.getText());
                String tanggal = tfTanggalBergabung.getText();

                if (id.isEmpty() || nama.isEmpty() || posisi.isEmpty() || divisi.isEmpty() || tanggal.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Semua field harus diisi!");
                    return;
                }

                boolean berhasil = perusahaan.tambahKaryawan(id, nama, posisi, gaji, divisi, tanggal);
                if (berhasil) {
                    clearFields();
                    refreshTable();
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Gaji harus berupa angka!");
            }
        });

        // Event handler untuk clear
        btnClear.setOnAction(e -> {
            clearFields();
            tfId.setDisable(false);
        });

        // Event handler untuk hapus
        btnHapus.setOnAction(e -> {
            Karyawan selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih karyawan terlebih dahulu!");
                return;
            }

            Optional<ButtonType> result = showConfirmation("Konfirmasi", "Apakah anda yakin ingin menghapus karyawan ini?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean berhasil = perusahaan.hapusKaryawan(selected.getId());
                if (berhasil) {
                    clearFields();
                    refreshTable();
                    tfId.setDisable(false);
                }
            }
        });

        // Event handler untuk update
        btnUpdate.setOnAction(e -> {
            Karyawan selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih karyawan terlebih dahulu!");
                return;
            }

            try {
                String id = tfId.getText();
                String nama = tfNama.getText();
                String posisi = tfPosisi.getText();
                String divisi = tfDivisi.getText();
                long gaji = Long.parseLong(tfGaji.getText());
                String tanggal = tfTanggalBergabung.getText();

                // Update data karyawan
                Karyawan karyawan = perusahaan.getKaryawanById(id);
                if (karyawan != null) {
                    karyawan.setNama(nama);
                    karyawan.setPosisi(posisi);
                    karyawan.setDivisi(divisi);
                    karyawan.setGaji(gaji);
                    karyawan.setTanggalBergabung(tanggal);

                    perusahaan.simpanKeFile();
                    refreshTable();
                    showAlert(Alert.AlertType.INFORMATION, "Informasi", "Data karyawan berhasil diupdate!");
                    clearFields();
                    tfId.setDisable(false);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Gaji harus berupa angka!");
            }
        });

        hbox.getChildren().addAll(btnTambah, btnClear, btnHapus, btnUpdate);
        return hbox;
    }

    private HBox createSearchPane() {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10, 0, 0, 0));

        Label lblCari = new Label("Cari:");
        tfCari = new TextField();
        tfCari.setPrefWidth(150);

        cbKriteriaCari = new ComboBox<>();
        cbKriteriaCari.getItems().addAll("ID", "Nama", "Posisi", "Divisi");
        cbKriteriaCari.setValue("Nama");

        Button btnCari = new Button("Cari");
        Button btnReset = new Button("Reset");

        btnCari.setOnAction(e -> {
            String kriteria = cbKriteriaCari.getValue();
            String nilai = tfCari.getText().trim();

            if (nilai.isEmpty()) {
                refreshTable();
                return;
            }

            List<Karyawan> hasil = null;

            switch (kriteria) {
                case "ID":
                    Karyawan k = perusahaan.getKaryawanById(nilai);
                    if (k != null) {
                        hasil = List.of(k);
                    } else {
                        hasil = List.of();
                    }
                    break;
                case "Nama":
                    hasil = perusahaan.cariByNama(nilai);
                    break;
                case "Posisi":
                    hasil = perusahaan.cariByPosisi(nilai);
                    break;
                case "Divisi":
                    hasil = perusahaan.cariByDivisi(nilai);
                    break;
            }

            if (hasil != null) {
                updateTable(hasil);
            }
        });

        btnReset.setOnAction(e -> {
            tfCari.clear();
            refreshTable();
        });

        hbox.getChildren().addAll(lblCari, tfCari, cbKriteriaCari, btnCari, btnReset);
        return hbox;
    }

    private HBox createOtherButtonsPane() {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10, 0, 0, 0));

        Button btnTotalGaji = new Button("Total Gaji");
        Button btnFilterGaji = new Button("Filter Gaji");
        Button btnExport = new Button("Export Data");
        Button btnKeluar = new Button("Keluar");

        btnTotalGaji.setPrefWidth(90);
        btnFilterGaji.setPrefWidth(90);
        btnExport.setPrefWidth(90);
        btnKeluar.setPrefWidth(90);

        // Event handler untuk total gaji
        btnTotalGaji.setOnAction(e -> {
            long totalGaji = perusahaan.hitungTotalGaji();
            showAlert(Alert.AlertType.INFORMATION, "Total Gaji",
                    "Total gaji seluruh karyawan: Rp" + totalGaji);
        });

        // Event handler untuk filter gaji
        btnFilterGaji.setOnAction(e -> {
            // Membuat dialog untuk input range gaji
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Filter Berdasarkan Gaji");
            dialog.setHeaderText("Masukkan range gaji");

            ButtonType filterButtonType = new ButtonType("Filter", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(filterButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField minField = new TextField();
            minField.setPromptText("Gaji Minimum");
            TextField maxField = new TextField();
            maxField.setPromptText("Gaji Maksimum");

            grid.add(new Label("Gaji Minimum:"), 0, 0);
            grid.add(minField, 1, 0);
            grid.add(new Label("Gaji Maksimum:"), 0, 1);
            grid.add(maxField, 1, 1);

            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == filterButtonType) {
                try {
                    long min = Long.parseLong(minField.getText());
                    long max = Long.parseLong(maxField.getText());

                    if (min < 0 || max < 0 || min > max) {
                        showAlert(Alert.AlertType.ERROR, "Error",
                                "Range gaji tidak valid. Pastikan min ≤ max dan keduanya positif.");
                        return;
                    }

                    List<Karyawan> filtered = perusahaan.filterByGaji(min, max);
                    updateTable(filtered);

                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Masukkan harus berupa angka!");
                }
            }
        });

        // Event handler untuk export data
        btnExport.setOnAction(e -> {
            perusahaan.simpanKeFile();
            showAlert(Alert.AlertType.INFORMATION, "Export Data",
                    "Data berhasil disimpan ke file " + "karyawan_data.ser");
        });

        // Event handler untuk keluar
        btnKeluar.setOnAction(e -> {
            Optional<ButtonType> result = showConfirmation("Konfirmasi",
                    "Apakah anda yakin ingin keluar? Data akan disimpan secara otomatis.");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                perusahaan.simpanKeFile();
                javafx.application.Platform.exit();
            }
        });

        hbox.getChildren().addAll(btnTotalGaji, btnFilterGaji, btnExport, btnKeluar);
        return hbox;
    }

    private void clearFields() {
        tfId.clear();
        tfNama.clear();
        tfPosisi.clear();
        tfDivisi.clear();
        tfGaji.clear();
        tfTanggalBergabung.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    private void refreshTable() {
        List<Karyawan> daftarKaryawan = perusahaan.getDaftarKaryawan();
        updateTable(daftarKaryawan);
    }

    private void updateTable(List<Karyawan> daftarKaryawan) {
        ObservableList<Karyawan> data = FXCollections.observableArrayList(daftarKaryawan);
        tableView.setItems(data);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Main class yang menyediakan pilihan apakah ingin menggunakan versi Console atau GUI
class SistemManajemenKaryawan {
    public static void main(String[] args) {
        System.out.println("=== SISTEM MANAJEMEN KARYAWAN ===");
        System.out.println("1. Jalankan versi Console");
        System.out.println("2. Jalankan versi GUI");
        System.out.print("Pilih mode: ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                SistemManajemenKaryawanConsole.main(null);
                break;
            case 2:
                Application.launch(SistemManajemenKaryawanGUI.class);
                break;
            default:
                System.out.println("Pilihan tidak valid. Program berhenti.");
        }

        scanner.close();
    }
}