package com.example.chessgame.chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
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
    public void findAvailableMoves() {
        List<Square> squares = new ArrayList<>();
        List<Square> defendingPieces = new ArrayList<>();

        bishop.piecePosition = piecePosition;
        rook.piecePosition = piecePosition;
        rook.findAvailableMoves();
        bishop.findAvailableMoves();

        squares.addAll(rook.possibleMoves);
        squares.addAll(bishop.possibleMoves);
        defendingPieces.addAll(rook.piecesDefending);
        defendingPieces.addAll(bishop.piecesDefending);

        this.possibleMoves = squares;
        this.piecesDefending = defendingPieces;
    }

    /**
     * Returns the square between an attacking piece and the king
     * essential to find if check can be blocked
     *
     * @param king
     */
    @Override
    public List<Square> getAttackVector(Piece king) {
        List<Square> squares = rook.getAttackVector(king);
        squares.addAll(bishop.getAttackVector(king));

        return squares;
    }
}
