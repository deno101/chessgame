package com.example.chessgame.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import com.example.chessgame.MainActivity;

import java.util.List;

public class Square extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {
    private static final String TAG = "Square";

    public int x, y;
    public Piece piece = null;
    public MainActivity parentContext;

    private Square(Context context) {
        super(context);
    }

    public Square(int x, int y, Piece piece, MainActivity context) {
        super(context);
        this.parentContext = context;
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
        if (parentContext.gameMode != MainActivity.GameMode.PLAY){
            // Do not try to move piece if in load mode
            return;
        }

        if (parentContext.active_piece != null) {
            // Attempt to move the piece to this square
            if (parentContext.active_piece.possibleMoves.contains(this)) {
                Move move = new Move(parentContext.active_piece.piecePosition, this);
                for (Square s : parentContext.active_piece.possibleMoves) {
                    s.setAlpha(1f);
                }
                move.move();
                parentContext.active_piece = null;
            } else {
                // Exit move and go back to select mode
                for (Square s : parentContext.active_piece.possibleMoves) {
                    s.setAlpha(1f);
                }
                parentContext.active_piece = null;
            }
        }

        if (piece != null && piece.type == parentContext.turn) {
            if (piece.possibleMoves.size() > 0) {
                parentContext.active_piece = piece;
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
