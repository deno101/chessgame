package com.example.chessgame.chess;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Type type, int imageResource) {
        super(type, imageResource);
    }

    public Knight(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> findAvailableMoves() {
        List<Square> squares = new ArrayList<>();
        int[] x = {-2, -2, -1, 1, 2, 2, 1, -1};
        int[] y = {-1, 1, 2, 2, 1, -1, -2, -2};

        for (int i = 0; i < x.length; i++) {
            if (piecePosition.x + x[i] >= 0 && piecePosition.x + x[i] <= 7) {
                if (piecePosition.y + y[i] >= 0 && piecePosition.y + y[i] <= 7) {
                    // try and replicate error Kf3
                    if (piecePosition.parentContext.squares[piecePosition.x + x[i]][piecePosition.y + y[i]].piece == null ||
                            piecePosition.parentContext.squares[piecePosition.x + x[i]][piecePosition.y + y[i]].piece.type != piecePosition.piece.type) {
                        squares.add(piecePosition.parentContext.squares[piecePosition.x + x[i]][piecePosition.y + y[i]]);
                    }
                }
            }
        }

        this.possibleMoves = squares;
        return squares;
    }
}
