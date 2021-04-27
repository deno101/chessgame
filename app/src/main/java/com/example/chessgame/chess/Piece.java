package com.example.chessgame.chess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.chessgame.MainActivity;
import com.example.chessgame.R;

import java.util.List;

public abstract class Piece {

    public enum Type{
        WHITE, BLACK
    }

    public static class PieceImages{
        public static int BLACK_PAWN = R.drawable.pawn_black;
        public static int WHITE_PAWN = R.drawable.pawn_white;
        public static int BLACK_KING= R.drawable.king_black;
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
    protected Square piecePosition;
    protected Square previousPosition;
    protected Bitmap pieceImage;
    protected int imageResource;
    protected List<Square> possibleMoves;

    private Piece() {
    }

    public Piece(Piece.Type type, int imageResource) {
        this.type = type;
        this.imageResource = imageResource;
    }

    public void initPiecePosition(Square square){
        square.setPiece(this);
        this.piecePosition = square;
        this.initPieceImage(this.imageResource);

        this.moveTo(this.piecePosition);
    }


    public abstract List<Square> findAvailableMoves();

    public void moveTo(Square nextPosition) {
        MainActivity.turn = MainActivity.turn == Type.WHITE ? Type.BLACK : Type.WHITE;

        this.previousPosition = this.piecePosition;
        this.previousPosition.piece = null;

        this.piecePosition = nextPosition;
        this.piecePosition.piece = this;

        previousPosition.setImageBitmap(null);
        piecePosition.setImageBitmap(pieceImage);
    }


    public void initPieceImage(int imageResource){
        this.pieceImage = BitmapFactory.decodeResource(piecePosition.getResources(), imageResource);
    }
}
