package com.example.chessgame.chess;

import android.util.Log;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public interface UndoCheck{
        void undo();
    }


    public King(Type type, int imageResource) {
        super(type, imageResource);
        validBlockMoves = new ArrayList<>();
    }

    public boolean isInCheck, isInCheckMate, bgChanged;
    public List<Square> validBlockMoves;
    public UndoCheck undoCheck;

    public King(Piece piece) {
        super(piece);
        if (piece instanceof King) {
            validBlockMoves = ((King) piece).validBlockMoves;
        }
    }

    public void setUndoCheck(UndoCheck undoCheck){
        this.undoCheck = undoCheck;
    }

    @Override
    public void findAvailableMoves() {
        List<Square> availableSquares = new ArrayList<>();
        List<Square> defendingPieces = new ArrayList<>();

        int x[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int y[] = {-1, -1, -1, 0, 1, 1, 1, 0};
        List<Piece> opponents = this.type == Type.BLACK ? piecePosition.parentContext.whitePieces : piecePosition.parentContext.blackPieces;

        for (int i = 0; i < x.length; i++) {
            if (piecePosition.x + x[i] >= 0 && piecePosition.x + x[i] <= 7) {
                if (piecePosition.y + y[i] >= 0 && piecePosition.y + y[i] <= 8) {
                    Square availableSquare = piecePosition.parentContext.squares[piecePosition.x + x[i]][piecePosition.y + y[i]];
                    if (availableSquare.piece == null) {
                        boolean isAttackedSquare = false;
                        for (Piece p : opponents) {
                            if (p.possibleMoves.contains(availableSquare)) {
                                isAttackedSquare = true;
                                break;
                            }
                        }
                        if (!isAttackedSquare) {
                            availableSquares.add(availableSquare);
                        }
                    } else if (availableSquare.piece.type != this.type) {
                        boolean isDefendedPiece = false;
                        for (Piece p : opponents) {
                            Log.d("", "findAvailableMoves: ");
                            if (p.piecesDefending.contains(availableSquare)) {
                                isDefendedPiece = true;
                                break;
                            }
                        }
                        if (!isDefendedPiece) {
                            availableSquares.add(availableSquare);
                        }
                    } else if (availableSquare.piece.type == this.type) {
                        defendingPieces.add(availableSquare);
                    }
                }
            }
        }
        this.possibleMoves = availableSquares;
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
        return new ArrayList<Square>();
    }

    public void checkIfCheckOrCheckmate() {
        List<Piece> opponents = this.type == Type.BLACK ? piecePosition.parentContext.whitePieces : piecePosition.parentContext.blackPieces;
        List<Piece> attackers = new ArrayList<>();
        for (Piece p : opponents) {
            if (p.possibleMoves.contains(this.piecePosition)) {
                attackers.add(p);
            }
        }

        if (attackers.size() == 0) {
            // then the piece is neither in check or in checkmate
            isInCheckMate = false;
            isInCheck = false;
        } else if (attackers.size() == 1) {
            // the piece is in check but checkmate only if no block or capture
            isInCheck = true;
            List<Piece> myPieces = this.type == Type.WHITE ? piecePosition.parentContext.whitePieces : piecePosition.parentContext.blackPieces;

            for (Piece p : myPieces) {
                for (Square s : attackers.get(0).getAttackVector(this)) {
                    if(p.possibleMoves.contains(s)){
                     this.validBlockMoves.add(s);
                    }
                }
                isInCheckMate = false;
            }

            if (validBlockMoves.size() == 0){
                isInCheckMate = true;
            }
        } else if (attackers.size() >= 2) {
            isInCheckMate = true;
            isInCheck = true;
        }
    }
}
