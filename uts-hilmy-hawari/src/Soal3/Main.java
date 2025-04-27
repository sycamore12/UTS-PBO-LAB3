package Soal3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LotreBoard board = new LotreBoard();

        board.generateBoard();

        System.out.println("Welcome to E-Lottery Gosok");
        board.displayBoard();

        while (!board.isGameOver()) {
            System.out.print("Masukkan tebakan anda (baris dan kolom) : ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Validasi input
            if (row < 0 || row >= 4 || col < 0 || col >= 5) {
                System.out.println("Input tidak valid! Baris (0-3) dan Kolom (0-4)");
                continue;
            }

            // Cek apakah kotak sudah dibuka sebelumnya
            if (board.isRevealed(row, col)) {
                System.out.println("Kotak telah dibuka sebelumnya!");
                continue;
            }

            boolean safe = board.guess(row, col);

            if (!safe) {
                System.out.println("BOOM! Anda menemukan bom! Permainan berakhir.");
                board.displayBoard();
                break;
            }

            board.displayBoard();
        }

        if (board.isWinner()) {
            System.out.println("Selamat anda menang");
            board.displayBoard();
        }

        scanner.close();
    }
}
