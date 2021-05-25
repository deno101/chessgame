package com.example.chessgame.chess;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.example.chessgame.MainActivity;
import com.example.chessgame.R;

public class Move {
    private static final String TAG = "Move";
    private final char[] FILES = {'8', '7', '6', '5', '4', '3', '2', '1'};
    private final char[] RANKS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

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

    /**
     * This constructor is used to create a move from a notation
     *
     * @param moveNotation example e2 e4 (piece moved from e2 to e4)
     */
    public Move(String moveNotation, MainActivity mainActivity) {
        String[] notation = moveNotation.split(" ");
        int y_from = getIndex(RANKS, notation[0].charAt(0));
        int x_from = getIndex(FILES, notation[0].charAt(1));

        int y_to = getIndex(RANKS, notation[1].charAt(0));
        int x_to = getIndex(FILES, notation[1].charAt(1));

        Log.d(TAG, "Move: ");
        this.previousPosition = mainActivity.squares[x_from][y_from];
        this.nextPosition = mainActivity.squares[x_to][y_to];
    }

    private int getIndex(char[] array, char element) {
        int i = 0;
        for (i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return i;
    }

    public void move() {
        // check if nextPosition has a piece
        if (nextPosition.piece != null) {
            isCapture = true;
            capturedPiece = nextPosition.piece;
            if (capturedPiece.type == Piece.Type.WHITE) {
                nextPosition.parentContext.whitePieces.remove(capturedPiece);
            } else {
                nextPosition.parentContext.blackPieces.remove(capturedPiece);
            }
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

        previousPosition.parentContext.evaluateMoves();
        evaluateMoveIsValid();
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
            if (capturedPiece.type == Piece.Type.WHITE) {
                nextPosition.parentContext.whitePieces.add(capturedPiece);
            } else {
                nextPosition.parentContext.blackPieces.add(capturedPiece);
            }
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
        nextPosition.parentContext.evaluateMoves();
    }

    void evaluateMoveIsValid() {
        // Check if the king still in check after move

        King activeKing = this.nextPosition.piece.type == Piece.Type.WHITE ? previousPosition.parentContext.whiteKing : previousPosition.parentContext.blackKing;
        King inactiveKing = this.nextPosition.piece.type == Piece.Type.WHITE ? previousPosition.parentContext.blackKing : previousPosition.parentContext.whiteKing;
        if (activeKing == null || inactiveKing == null) {
            return;
        }
        activeKing.checkIfCheckOrCheckmate();
        inactiveKing.checkIfCheckOrCheckmate();
        if (activeKing.isInCheck) {
            this.undo();
            Toast.makeText(previousPosition.parentContext, "Invalid Move!!", Toast.LENGTH_LONG).show();
            previousPosition.parentContext.canUndo = false;
        } else {
            // change the color of background back to the original color
            if (activeKing.bgChanged) {
                activeKing.undoCheck.undo();
            }
        }

        if (inactiveKing.isInCheckMate) {
            previousPosition.parentContext.showGameEnd("Checkmate " + activeKing.type.toString().toLowerCase() + " wins !!");
        }

        if (inactiveKing.isInCheck) {
            // make the king square red so that the user know it is in check
            inactiveKing.bgChanged = true;
            final int initialBgColor = ((ColorDrawable) inactiveKing.piecePosition.getBackground()).getColor();
            final Square initialSquare = inactiveKing.piecePosition;

            inactiveKing.setUndoCheck(new King.UndoCheck() {
                @Override
                public void undo() {
                    initialSquare.setBackgroundColor(initialBgColor);
                }
            });

            inactiveKing.piecePosition.setBackgroundColor(inactiveKing.piecePosition.parentContext.getResources().getColor(R.color.red));

        }
    }

    @Override
    public String toString() {
        // return the chess notation of the game
        StringBuilder builder = new StringBuilder();

        builder.append(RANKS[previousPosition.y]);
        builder.append(FILES[previousPosition.x]);
        builder.append(" ");
        builder.append(RANKS[nextPosition.y]);
        builder.append(FILES[nextPosition.x]);
        return builder.toString();
    }
}
