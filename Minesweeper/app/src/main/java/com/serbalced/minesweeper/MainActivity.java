package com.serbalced.minesweeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Board.OnBoardUpdate, DifficultySelection.OnDiffChange, BombSelection.OnBombSel {
    int dim = 8;
    int numBombs = 15;
    int flags = 0;
    ConstraintLayout cl;
    GridLayout gl;
    Board board;
    Button cells[][];
    MainActivity main = this;
    boolean gameRunning;
    int bombImg = 0;
    int imgsBombs[] = {R.drawable.default_bomb, R.drawable.c4, R.drawable.tnt};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl = findViewById(R.id.constraintLayout);
        cl.post(() -> {
            play();
        });
    }

    public void play(){
        gameRunning = true;
        flags = 0;

        board = new Board(dim, numBombs, main);
        cells = new Button[dim][dim];

        int width = cl.getWidth();
        int height = cl.getHeight();

        gl = findViewById(R.id.tablero);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0, 0, 0, 0);
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        gl.setLayoutParams(params);
        gl.setRowCount(dim);
        gl.setColumnCount(dim);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width / dim, height / dim);
        lp.setMargins(0, 0, 0, 0);

        for (int r = 0; r < dim; r++){
            for (int c = 0; c < dim; c++){
                Button b = new Button(getApplicationContext());
                b.setLayoutParams(lp);
                b.setBackground(
                        getDrawable(R.drawable.cell)
                );

                b.setTypeface(null, Typeface.BOLD);

                b.setOnClickListener(clickListener(r, c));
                b.setOnLongClickListener(longClickListener(r, c));

                cells[r][c] = b;

                gl.addView(b);
            }
        }
    }

    public void lose(){
        Toast.makeText(
                this,
                "PIERDES",
                Toast.LENGTH_LONG
        ).show();

        for (int[] pos : board.getBombs()){
            cells[pos[0]][pos[1]].setBackground(
                    getDrawable(imgsBombs[bombImg])
            );
        }

        gameRunning = false;
    }

    public void win(){
        Toast.makeText(
                this,
                R.string.win,
                Toast.LENGTH_LONG
        ).show();

        board.showAllCells();
        gameRunning = false;
    }

    public void reset(){
        board = new Board(dim, numBombs, main);
        flags = 0;

        for (int r = 0; r < dim; r++){
            for (int c = 0; c < dim; c++){
                cells[r][c].setText("");
                cells[r][c].setBackground(
                        getDrawable(R.drawable.cell)
                );

                cells[r][c].setOnClickListener(clickListener(r, c));
                cells[r][c].setOnLongClickListener(longClickListener(r, c));
            }
        }

        gameRunning = true;
    }

    public View.OnClickListener clickListener(int r, int c){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameRunning){
                    return;
                }

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp.start();

                boolean safe = board.dig(r, c);
                if (!safe){
                    bombFound(v);
                    lose();
                }
            }
        };
    }

    public View.OnLongClickListener longClickListener(int r, int c){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!gameRunning){
                    return true;
                }

                if (board.board[r][c] != -1){
                    lose();

                    cells[r][c].setBackground(
                            getDrawable(R.drawable.free_cell)
                    );
                    if (board.board[r][c] > 0) {
                        cells[r][c].setText(
                                board.board[r][c] + ""
                        );
                    }
                    cells[r][c].setTextColor(setCellColor(new int[]{r, c}));

                    return true;
                }

                v.setBackground(
                        getDrawable(R.drawable.flag)
                );
                flags++;
                board.addFlag(new int[]{r, c});

                if (flags == numBombs){
                    win();
                }

                v.setOnLongClickListener(view -> {
                    //HAGO QUE EL BOTON NO HAGA NADA
                    return true;
                });
                v.setOnClickListener(view -> {
                    //HAGO QUE EL BOTON NO HAGA NADA
                });

                return true;
            }
        };
    }

    public void bombFound(View v){
        v.setBackground(
                getDrawable(imgsBombs[bombImg])
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //momento en el que se crea el menu de opciones
        getMenuInflater().inflate(
                R.menu.mi_menu,
                menu
        );
        return true; // true: mostrar 3 puntitos false no
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnReset){
            reset();
        }
        if (item.getItemId() == R.id.btnDiff){
            DifficultySelection diffDialog = new DifficultySelection();
            diffDialog.show(getSupportFragmentManager(), "difficulty_selection");
        }
        if (item.getItemId() == R.id.btnIns){
            InstructionsDialog ins = new InstructionsDialog();
            ins.show(getSupportFragmentManager(), "instructions");
        }
        if (item.getItemId() == R.id.btnBombs){
            BombSelection bs = new BombSelection();
            bs.show(getSupportFragmentManager(), "bomb_sel");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdate(ArrayList<int[]> holes) {
        for (int[] i : holes){
            if (board.board[i[0]][i[1]] >= 0){
                cells[i[0]][i[1]].setBackground(
                        getDrawable(R.drawable.free_cell)
                );
            }

            if (board.board[i[0]][i[1]] == 0){
                cells[i[0]][i[1]].setOnClickListener(view -> {
                    return;
                });
                cells[i[0]][i[1]].setOnLongClickListener(view -> {
                    return false;
                });
            }

            if (board.board[i[0]][i[1]] > 0){
                cells[i[0]][i[1]].setText(
                        board.board[i[0]][i[1]] + ""
                );

                cells[i[0]][i[1]].setOnClickListener(view -> {
                    board.clickOnNum(i[0], i[1]);
                });

                cells[i[0]][i[1]].setTextColor(setCellColor(i));
            }
        }
    }

    public int setCellColor(int[] i){
        switch (board.board[i[0]][i[1]]){
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return (
                        getColor(R.color.dBlue)
                );
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.CYAN;
            case 7:
                return Color.BLACK;
            case 8:
                return Color.LTGRAY;
        }

        return Color.BLACK;
    }

    @Override
    public void onDiffChange(int dim, int bombs) {
        this.dim = dim;
        this.numBombs = bombs;
        gl.removeAllViews();
        play();
    }

    @Override
    public void onBombSel(int pos) {
        bombImg = pos;
    }
}