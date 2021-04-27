package com.example.chessgame.chess;

import android.util.Log;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public boolean first_move = true;
    private static final String TAG = "Pawn";

    public Pawn(Type type, int imageResource) {
        super(type, imageResource);
    }

    @Override
    public List<Square> findAvailableMoves() {
        List<Square> squares = new ArrayList<Square>();
        // if next column has no piece
        if (piecePosition.piece.type == Type.WHITE) {
            if (MainActivity.squares[piecePosition.x - 1][piecePosition.y].piece == null) {
                squares.add(MainActivity.squares[piecePosition.x - 1][piecePosition.y]);
                if (first_move && MainActivity.squares[piecePosition.x - 2][piecePosition.y].piece == null) {
                    squares.add(MainActivity.squares[piecePosition.x - 2][piecePosition.y]);
                }
            }

            // if next column has no piece
            if ((piecePosition.y + 1) <= 7) {
                if (MainActivity.squares[piecePosition.x - 1][piecePosition.y + 1].piece != null && MainActivity.squares[piecePosition.x + 1][piecePosition.y + 1].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x - 1][piecePosition.y + 1]);
                }
            }

            if ((piecePosition.y - 1) >= 0) {
                if (MainActivity.squares[piecePosition.x - 1][piecePosition.y - 1].piece != null && MainActivity.squares[piecePosition.x + 1][piecePosition.y - 1].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x - 1][piecePosition.y - 1]);
                }
            }
        } else if (piecePosition.piece.type == Type.BLACK) {
            if (MainActivity.squares[piecePosition.x + 1][piecePosition.y].piece == null) {
                squares.add(MainActivity.squares[piecePosition.x + 1][piecePosition.y]);
                if (first_move && MainActivity.squares[piecePosition.x + 2][piecePosition.y].piece == null) {
                    squares.add(MainActivity.squares[piecePosition.x + 2][piecePosition.y]);
                }
            }

            // if next column has no piece
            if ((piecePosition.y + 1) <= 7) {
                if (MainActivity.squares[piecePosition.x + 1][piecePosition.y + 1].piece != null && MainActivity.squares[piecePosition.x + 1][piecePosition.y + 1].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x + 1][piecePosition.y + 1]);
                }
            }

            if ((piecePosition.y - 1) >= 0) {
                if (MainActivity.squares[piecePosition.x + 1][piecePosition.y - 1].piece != null && MainActivity.squares[piecePosition.x + 1][piecePosition.y - 1].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x + 1][piecePosition.y - 1]);
                }
            }
        }
        this.possibleMoves = squares;
        return squares;
    }
}
