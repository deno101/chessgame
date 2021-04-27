package com.example.chessgame.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import com.example.chessgame.MainActivity;

import java.util.List;

public class Square extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {
    private static final String TAG = "Square";
    private final char[] FILES = {'1', '2', '3', '4', '5', '6', '7', '8'};
    private final char[] RANKS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public int x, y;
    public Piece piece = null;

    private Square(Context context) {
        super(context);
    }

    public Square(int x, int y, Piece piece, Context context) {
        super(context);
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.setOnClickListener(this);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public void onClick(View v) {
        if (MainActivity.active_piece != null) {
            // Attempt to move the piece to this square
            if (MainActivity.active_piece.possibleMoves.contains(this)) {
                MainActivity.active_piece.moveTo(this);
            } else {
                // Exit move and go back to select mode
                for (Square s : MainActivity.active_piece.possibleMoves) {
                    s.setAlpha(1f);
                }
                MainActivity.active_piece = null;
            }
        }

        if (piece != null && piece.type == MainActivity.turn) {
            piece.findAvailableMoves();
            if (piece.possibleMoves.size() > 0) {
                MainActivity.active_piece = piece;
                for (Square s : piece.possibleMoves) {
                    s.setAlpha(0.5f);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
