package com.example.chessgame.chess;

import android.util.Log;

import java.lang.reflect.Constructor;

public class Move {
    /*
     * type of move normal move no_capture
     * capture move which require undo to init a piece*/
    public Square previousPosition;

    public Square nextPosition;
    public boolean isCapture = false;
    public boolean isCheck;
    private Piece capturedPiece;
    private Piece promotedPiece;

    public Move(Square previousPosition, Square nextPosition) {
        this.previousPosition = previousPosition;
        this.nextPosition = nextPosition;
    }

    public Move(String moveNotation) {
        // To be used when loading a game from memory

    }

    public void move() {
        // check if nextPosition has a piece
        if (nextPosition.piece != null) {
            isCapture = true;
            capturedPiece = nextPosition.piece;
        }
        Piece piece = previousPosition.piece;
        previousPosition.setImageBitmap(null);
        previousPosition.piece = null;

        nextPosition.setImageBitmap(piece.pieceImage);
        nextPosition.piece = piece;
        nextPosition.piece.piecePosition = nextPosition;
        (nextPosition.piece).first_move = false;


        previousPosition.parentContext.moves.add(this);
        nextPosition.parentContext.turn = nextPosition.parentContext.turn == Piece.Type.WHITE ? Piece.Type.BLACK : Piece.Type.WHITE;

        // TODO: 4/28/21 check if checkmate or check
        previousPosition.parentContext.canUndo = true;
    }

    public void move(int i) {
        move();
        if (previousPosition.piece instanceof Pawn) {
            ((Pawn) previousPosition.piece).first_move = true;
        }
    }

    public void undo() {
        if (!previousPosition.parentContext.canUndo) {
            // End if in second consecutive undo
            return;
        }

        Piece piece = nextPosition.piece;
        if (isCapture) {
            nextPosition.setImageBitmap(capturedPiece.pieceImage);
            nextPosition.piece = capturedPiece;
        } else {
            nextPosition.setImageBitmap(null);
            nextPosition.piece = null;
        }

        previousPosition.setImageBitmap(piece.pieceImage);
        previousPosition.piece = piece;
        previousPosition.piece.piecePosition = previousPosition;

        (previousPosition.piece).first_move = true;

        previousPosition.parentContext.canUndo = false;
        previousPosition.parentContext.moves.remove(this);
        nextPosition.parentContext.turn = nextPosition.parentContext.turn == Piece.Type.WHITE ? Piece.Type.BLACK : Piece.Type.WHITE;
    }

    @Override
    public String toString() {
        // return the chess notation of the game
        return "Move{}";
    }
}
