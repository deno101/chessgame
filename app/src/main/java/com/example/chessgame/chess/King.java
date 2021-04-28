package com.example.chessgame.chess;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Type type, int imageResource) {
        super(type, imageResource);
    }

    public King(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> findAvailableMoves() {
        List<Square> squares = new ArrayList<>();
        int x[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int y[] = {-1, -1, -1, 0, 1, 1, 1, 0};

        for (int i = 0; i < x.length; i++) {
            if (piecePosition.x + x[i] >= 0 && piecePosition.x + x[i] <= 7) {
                if (piecePosition.y + y[i] >= 0 && piecePosition.y + y[i] <= 8) {
                    Square currentSquare = piecePosition.parentContext.squares[x[i]][y[i]];
                    if (currentSquare.piece == null || currentSquare.piece.type != this.type) {
                        squares.add(piecePosition.parentContext.squares[x[i]][y[i]]);
                    }
                }
            }
        }
        this.possibleMoves = squares;
        return squares;
    }
}
