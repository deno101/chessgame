package com.example.chessgame.chess;

import android.util.Log;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Type type, int imageResource) {
        super(type, imageResource);
    }
    public boolean isInCheck, isInCheckMate;

    public King(Piece piece) {
        super(piece);
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
                        for (Piece p: opponents){
                            Log.d("", "findAvailableMoves: ");
                            if (p.piecesDefending.contains(availableSquare)){
                                isDefendedPiece = true;
                                break;
                            }
                        }
                        if (!isDefendedPiece){
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

    public void checkIfCheckOrCheckmate() {
        List<Piece> opponents = this.type == Type.BLACK ? piecePosition.parentContext.whitePieces : piecePosition.parentContext.blackPieces;
        for (Piece p: opponents){
            if (p.possibleMoves.contains(this.piecePosition)){
                isInCheck = true;
                if (this.possibleMoves.size() == 0){
                    isInCheckMate = true;
                }else {
                    isInCheckMate = false;
                }
                return;
            }
        }
        isInCheckMate = false;
        isInCheck = false;
    }
}
