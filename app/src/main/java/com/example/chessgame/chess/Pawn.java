package com.example.chessgame.chess;

import android.util.Log;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final String TAG = "Pawn";

    public Pawn(Type type, int imageResource) {
        super(type, imageResource);
    }

    public Pawn(Piece piece) {
        super(piece);
    }

    @Override
    public void findAvailableMoves() {
        List<Square> squares = new ArrayList<Square>();
        List<Square> defendingPieces = new ArrayList<>();

        MainActivity mainActivity = piecePosition.parentContext;

        // if next column has no piece
        if (piecePosition.piece.type == Type.WHITE) {
            if (mainActivity.squares[piecePosition.x - 1][piecePosition.y].piece == null) {
                squares.add(mainActivity.squares[piecePosition.x - 1][piecePosition.y]);
                if (first_move && mainActivity.squares[piecePosition.x - 2][piecePosition.y].piece == null) {
                    squares.add(mainActivity.squares[piecePosition.x - 2][piecePosition.y]);
                }
            }

            // if next column has no piece
            if ((piecePosition.y + 1) <= 7) {
                if (mainActivity.squares[piecePosition.x - 1][piecePosition.y + 1].piece != null) {
                    if (mainActivity.squares[piecePosition.x - 1][piecePosition.y + 1].piece.type != piecePosition.piece.type) {
                        squares.add(mainActivity.squares[piecePosition.x - 1][piecePosition.y + 1]);
                    } else {
                        defendingPieces.add(mainActivity.squares[piecePosition.x - 1][piecePosition.y + 1]);
                    }
                }
            }

            if ((piecePosition.y - 1) >= 0) {
                if (mainActivity.squares[piecePosition.x - 1][piecePosition.y - 1].piece != null) {
                    if (mainActivity.squares[piecePosition.x - 1][piecePosition.y - 1].piece.type != piecePosition.piece.type){
                        squares.add(mainActivity.squares[piecePosition.x - 1][piecePosition.y - 1]);
                    }else{
                        defendingPieces.add(mainActivity.squares[piecePosition.x - 1][piecePosition.y - 1]);
                    }
                }
            }
        } else if (piecePosition.piece.type == Type.BLACK) {
            if (mainActivity.squares[piecePosition.x + 1][piecePosition.y].piece == null) {
                squares.add(mainActivity.squares[piecePosition.x + 1][piecePosition.y]);
                if (first_move && mainActivity.squares[piecePosition.x + 2][piecePosition.y].piece == null) {
                    squares.add(mainActivity.squares[piecePosition.x + 2][piecePosition.y]);
                }
            }

            // if next column has no piece
            if ((piecePosition.y + 1) <= 7) {
                if (mainActivity.squares[piecePosition.x + 1][piecePosition.y + 1].piece != null) {
                    if (mainActivity.squares[piecePosition.x + 1][piecePosition.y + 1].piece.type != piecePosition.piece.type){
                        squares.add(mainActivity.squares[piecePosition.x + 1][piecePosition.y + 1]);
                    }else{
                        defendingPieces.add(mainActivity.squares[piecePosition.x + 1][piecePosition.y + 1]);
                    }
                }
            }

            if ((piecePosition.y - 1) >= 0) {
                if (mainActivity.squares[piecePosition.x + 1][piecePosition.y - 1].piece != null) {
                    if(mainActivity.squares[piecePosition.x + 1][piecePosition.y - 1].piece.type != piecePosition.piece.type){
                        squares.add(mainActivity.squares[piecePosition.x + 1][piecePosition.y - 1]);
                    }else {
                        defendingPieces.add(mainActivity.squares[piecePosition.x + 1][piecePosition.y - 1]);
                    }
                }
            }
        }
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
        return new ArrayList<Square>();
    }
}
