package com.serbalced.minesweeper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Set;

public class Board {
    private int dim;
    private int numBombs;
    public int[][] board;
    private ArrayList<int[]> holes;
    private ArrayList<int[]> flags;
    private OnBoardUpdate updateBoard;

    public Board(int dim, int numBombs, OnBoardUpdate updater){
        this.dim = dim;
        this.numBombs = numBombs;
        createBoard();
        setNumCells();
        this.holes = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.updateBoard = updater;
    }

    private void createBoard(){
        this.board = new int[dim][dim];
        int bombsPlaced = 0;

        for (int r = 0; r < dim; r++){
            for (int c = 0; c < dim; c++){
                board[r][c] = 0;
            }
        }

        while(bombsPlaced < numBombs){
            int row = (int) (Math.random() * dim);
            int col = (int) (Math.random() * dim);

            if (board[row][col] != -1){
                board[row][col] = -1;
                bombsPlaced++;
            }
        }
    }

    private void setNumCells(){
        for (int r = 0; r < dim; r++){
            for (int c = 0; c < dim; c++){
                if (board[r][c] == -1){
                    countBombs(r, c);
                }
            }
        }
    }

    private void countBombs(int r, int c){
        int lastRow = r == 0 ? 0 : r - 1;
        int nextRow = r == dim - 1 ? dim - 1 : r + 1;
        int lastCol = c == 0 ? 0 : c - 1;
        int nextCol = c == dim - 1 ? dim - 1 : c + 1;

        for (int row = lastRow; row <= nextRow; row++){
            for (int col = lastCol; col <= nextCol; col++){
                if (row == r && col == c){
                    continue;
                }

                if (board[row][col] == -1){
                    continue;
                }

                board[row][col]++;
            }
        }
    }

    public boolean dig(int r, int c){
        int pos[] = {r, c};
        holes.add(pos);

        if (board[r][c] == -1){
            return false;
        }

        if (board[r][c] > 0){
            updateBoard.onUpdate(holes);
            return true;
        }

        int lastRow = r == 0 ? 0 : r - 1;
        int nextRow = r == dim - 1 ? dim - 1 : r + 1;
        int lastCol = c == 0 ? 0 : c - 1;
        int nextCol = c == dim - 1 ? dim - 1 : c + 1;

        for (int row = lastRow; row <= nextRow; row++){
            for (int col = lastCol; col <= nextCol; col++){
                boolean keepDiggin = canDig(row, col);

                if (keepDiggin){
                    dig(row, col);
                }
            }
        }
        updateBoard.onUpdate(holes);
        return true;
    }

    public boolean canDig(int r, int c){
        for (int[] i : holes){
            if (i[0] == r && i[1] == c){
                return false;
            }
        }

        return true;
    }

    public void clickOnNum(int r, int c){
        if (!flagsAround(r, c)){
            return;
        }

        int lastRow = r == 0 ? 0 : r - 1;
        int nextRow = r == dim - 1 ? dim - 1 : r + 1;
        int lastCol = c == 0 ? 0 : c - 1;
        int nextCol = c == dim - 1 ? dim - 1 : c + 1;

        for (int row = lastRow; row <= nextRow; row++){
            for (int col = lastCol; col <= nextCol; col++){
                if (r == row && c == col){
                    continue;
                }

                boolean keepDiggin = canDig(row, col);

                if (keepDiggin){
                    holes.add(new int[]{row, col});
                    dig(row, col);
                }
            }
        }
        updateBoard.onUpdate(holes);
    }

    public boolean flagsAround(int r, int c){
        int numFlag = 0;
        int lastRow = r == 0 ? 0 : r - 1;
        int nextRow = r == dim - 1 ? dim - 1 : r + 1;
        int lastCol = c == 0 ? 0 : c - 1;
        int nextCol = c == dim - 1 ? dim - 1 : c + 1;

        for (int row = lastRow; row <= nextRow; row++){
            for (int col = lastCol; col <= nextCol; col++){
                for (int[] pos : this.flags){
                    if (pos[0] == row && pos[1] == col){
                        numFlag++;
                    }
                }
            }
        }

        return numFlag == this.board[r][c];
    }

    public void showAllCells(){
        this.holes.clear();
        for (int r = 0; r < dim; r++){
            for (int c = 0; c < dim; c++){
                if (board[r][c] != -1){
                    int pos[] = {r, c};
                    holes.add(pos);
                }
            }
        }

        this.updateBoard.onUpdate(this.holes);
    }

    public void addFlag(int[] pos){
        flags.add(pos);
    }

    public ArrayList<int[]> getFlags(){
        return this.flags;
    }

    public interface OnBoardUpdate{
        public void onUpdate(ArrayList<int[]> holes);
    }
}
