package com.example.chessgame.chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends  Piece{
    private Bishop bishop;
    private Rook rook;

    public Queen(Type type, int imageResource) {
        super(type, imageResource);
        rook = new Rook(this.type, this.imageResource);
        bishop = new Bishop(this.type, this.imageResource);
    }

    public Queen(Piece piece) {
        super(piece);
        rook = new Rook(this.type, this.imageResource);
        bishop = new Bishop(this.type, this.imageResource);
    }

    @Override
    public List<Square> findAvailableMoves() {
        List<Square> squares = new ArrayList<>();

        bishop.piecePosition = piecePosition;
        rook.piecePosition = piecePosition;

        squares.addAll(rook.findAvailableMoves());
        squares.addAll(bishop.findAvailableMoves());

        this.possibleMoves = squares;
        return squares;
    }
}
