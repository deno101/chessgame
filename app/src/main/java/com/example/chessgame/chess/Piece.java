package com.example.chessgame.chess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.chessgame.MainActivity;
import com.example.chessgame.R;

import java.util.List;

public abstract class Piece {

    public enum Type {
        WHITE, BLACK
    }

    public static class PieceImages {
        public static int BLACK_PAWN = R.drawable.pawn_black;
        public static int WHITE_PAWN = R.drawable.pawn_white;
        public static int BLACK_KING = R.drawable.king_black;
        public static int WHITE_KING = R.drawable.king_white;
        public static int BLACK_QUEEN = R.drawable.queen_black;
        public static int WHITE_QUEEN = R.drawable.queen_white;
        public static int BLACK_ROOK = R.drawable.rook_black;
        public static int WHITE_ROOK = R.drawable.rook_white;
        public static int WHITE_BISHOP = R.drawable.bishop_white;
        public static int BLACK_BISHOP = R.drawable.bishop_black;
        public static int BLACK_KNIGHT = R.drawable.knight_black;
        public static int WHITE_KNIGHT = R.drawable.knight_white;
    }

    protected Piece.Type type;
    public Square piecePosition;
    protected Bitmap pieceImage;
    protected int imageResource;
    public List<Square> possibleMoves;
    public List<Square> piecesDefending;
    public boolean first_move = true;

    private Piece() {
    }

    public Piece(Piece.Type type, int imageResource) {
        this.type = type;
        this.imageResource = imageResource;
    }

    protected Piece(Piece piece) {
        this.type = piece.type;
        this.piecePosition = piece.piecePosition;
        this.pieceImage = piece.pieceImage;
        this.imageResource = piece.imageResource;
        this.possibleMoves = piece.possibleMoves;
        this.first_move = piece.first_move;
        this.piecesDefending = piece.piecesDefending;
    }

    public void initPiecePosition(Square square) {
        square.setPiece(this);
        this.piecePosition = square;
        this.initPieceImage(this.imageResource);

        this.piecePosition.setImageBitmap(this.pieceImage);
    }


    public abstract void findAvailableMoves();

    /**
     *Returns the square between an attacking piece and the king
     * essential to find if check can be blocked
     *
     * @param king
     * */
    public abstract List<Square> getAttackVector(Piece king);


    public void initPieceImage(int imageResource) {
        this.pieceImage = BitmapFactory.decodeResource(piecePosition.getResources(), imageResource);
    }
}
