package Soal3;

import java.util.Random;

class LotreBoard {
    private char[][] board;
    private boolean[][] revealed;
    private int[][] data;
    private int safeBoxesOpened;
    private final int ROWS = 4;
    private final int COLS = 5;
    private final int TOTAL_BOMBS = 2;

    public LotreBoard() {
        board = new char[ROWS][COLS];
        revealed = new boolean[ROWS][COLS];
        data = new int[ROWS][COLS];
        safeBoxesOpened = 0;

        // Inisialisasi board dengan '*' (belum dibuka)
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = '*';
                revealed[i][j] = false;
                data[i][j] = 0; // Default semua kotak aman
            }
        }
    }

    public void generateBoard() {
        Random random = new Random();
        int bombsPlaced = 0;

        while (bombsPlaced < TOTAL_BOMBS) {
            int row = random.nextInt(ROWS);
            int col = random.nextInt(COLS);

            // Pastikan belum ada bom di posisi ini
            if (data[row][col] != 1) {
                data[row][col] = 1; // 1 berarti bom
                bombsPlaced++;
            }
        }
    }

    public void displayBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean guess(int row, int col) {
        revealed[row][col] = true;

        if (data[row][col] == 1) {
            // Bom ditemukan
            board[row][col] = 'X';
            return false;
        } else {
            // Kotak aman
            board[row][col] = 'O';
            safeBoxesOpened++;
            return true;
        }
    }

    public boolean isGameOver() {
        // Permainan berakhir jika semua kotak aman telah dibuka (menang)
        if (safeBoxesOpened == (ROWS * COLS - TOTAL_BOMBS)) {
            return true;
        }

        // Cek jika ada bom yang dibuka (kalah)
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (revealed[i][j] && data[i][j] == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isWinner() {
        return safeBoxesOpened == (ROWS * COLS - TOTAL_BOMBS);
    }

    public boolean isRevealed(int row, int col) {
        return revealed[row][col];
    }
}