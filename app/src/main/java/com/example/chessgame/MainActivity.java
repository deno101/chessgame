package com.example.chessgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.example.chessgame.chess.Bishop;
import com.example.chessgame.chess.King;
import com.example.chessgame.chess.Knight;
import com.example.chessgame.chess.Move;
import com.example.chessgame.chess.Pawn;
import com.example.chessgame.chess.Piece;
import com.example.chessgame.chess.Queen;
import com.example.chessgame.chess.Rook;
import com.example.chessgame.chess.Square;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    public enum GameMode {
        PLAY, LOAD
    }

    public Square[][] squares = new Square[8][8];
    public Piece active_piece = null;
    public List<Piece> whitePieces = new ArrayList<Piece>();
    public List<Piece> blackPieces = new ArrayList<Piece>();
    public List<Move> moves = new ArrayList<>();
    public GameMode gameMode = GameMode.PLAY;
    private int minDim;

    // Handle undo only once
    public boolean canUndo = true;
    public Piece.Type turn = Piece.Type.WHITE;

    private GridLayout board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        setContentView(R.layout.activity_main);

        // Set the board width and height to min(width, height)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        minDim = Math.min(width, height);
        board = findViewById(R.id.gridLayout);
        ConstraintLayout.LayoutParams boardparams = (ConstraintLayout.LayoutParams) board.getLayoutParams();
        boardparams.height = minDim;
        boardparams.width = minDim - 4;
        board.setLayoutParams(boardparams);

        ImageButton undo = findViewById(R.id.undo);
        ImageButton nextMove = findViewById(R.id.nextMove);
        ImageButton previousMove = findViewById(R.id.prevMove);
        ImageButton ai = findViewById(R.id.ai);
        ImageButton resign = findViewById(R.id.resign);
        ImageButton draw = findViewById(R.id.draw);
        undo.setOnClickListener(this);
        nextMove.setOnClickListener(this);
        previousMove.setOnClickListener(this);
        ai.setOnClickListener(this);
        resign.setOnClickListener(this);
        draw.setOnClickListener(this);

        init_board();
        addPiecesToBoard();


    }

    public void init_board() {
        int siver = ContextCompat.getColor(this, R.color.siver);
        int pale_green = ContextCompat.getColor(this, R.color.pale_green);

        boolean on = true;
        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
                int c = on ? pale_green : siver;
                Square v = new Square(i, x, null, this);
                v.setBackgroundColor(c);
                v.setLayoutParams(new ConstraintLayout.LayoutParams(minDim / 8, minDim / 8));
                board.addView(v);

                // Add view to the board Array
                squares[i][x] = v;
                on = !on;
            }

            on = !on;
        }
    }

    public void addPiecesToBoard() {
        // Add black pawns
        for (int i = 0; i < 8; i++) {
            Pawn p = new Pawn(Piece.Type.BLACK, Piece.PieceImages.BLACK_PAWN);
            p.initPiecePosition(squares[1][i]);
            blackPieces.add(p);
        }

        // Add white pawns
        for (int i = 0; i < 8; i++) {
            Pawn p = new Pawn(Piece.Type.WHITE, Piece.PieceImages.WHITE_PAWN);
            p.initPiecePosition(squares[6][i]);
            whitePieces.add(p);
        }
        //add rooks to board
        Rook rook = new Rook(Piece.Type.WHITE, Piece.PieceImages.WHITE_ROOK);
        rook.initPiecePosition(squares[7][0]);
        whitePieces.add(rook);
        rook = new Rook(Piece.Type.WHITE, Piece.PieceImages.WHITE_ROOK);
        rook.initPiecePosition(squares[7][7]);
        whitePieces.add(rook);
        rook = new Rook(Piece.Type.BLACK, Piece.PieceImages.BLACK_ROOK);
        rook.initPiecePosition(squares[0][0]);
        blackPieces.add(rook);
        rook = new Rook(Piece.Type.BLACK, Piece.PieceImages.BLACK_ROOK);
        rook.initPiecePosition(squares[0][7]);
        blackPieces.add(rook);

        Bishop bishop = new Bishop(Piece.Type.WHITE, Piece.PieceImages.WHITE_BISHOP);
        bishop.initPiecePosition(squares[7][2]);
        whitePieces.add(bishop);
        bishop = new Bishop(Piece.Type.WHITE, Piece.PieceImages.WHITE_BISHOP);
        bishop.initPiecePosition(squares[7][5]);
        whitePieces.add(bishop);
        bishop = new Bishop(Piece.Type.BLACK, Piece.PieceImages.BLACK_BISHOP);
        bishop.initPiecePosition(squares[0][5]);
        blackPieces.add(bishop);
        bishop = new Bishop(Piece.Type.BLACK, Piece.PieceImages.BLACK_BISHOP);
        bishop.initPiecePosition(squares[0][2]);
        blackPieces.add(bishop);

        Knight knight = new Knight(Piece.Type.WHITE, Piece.PieceImages.WHITE_KNIGHT);
        knight.initPiecePosition(squares[7][1]);
        whitePieces.add(knight);
        knight = new Knight(Piece.Type.WHITE, Piece.PieceImages.WHITE_KNIGHT);
        knight.initPiecePosition(squares[7][6]);
        whitePieces.add(knight);
        knight = new Knight(Piece.Type.BLACK, Piece.PieceImages.BLACK_KNIGHT);
        knight.initPiecePosition(squares[0][1]);
        blackPieces.add(knight);
        knight = new Knight(Piece.Type.BLACK, Piece.PieceImages.BLACK_KNIGHT);
        knight.initPiecePosition(squares[0][6]);
        blackPieces.add(knight);

        King king = new King(Piece.Type.WHITE, Piece.PieceImages.WHITE_KING);
        king.initPiecePosition(squares[7][4]);
        whitePieces.add(king);
        king = new King(Piece.Type.BLACK, Piece.PieceImages.BLACK_KING);
        king.initPiecePosition(squares[0][4]);
        blackPieces.add(king);

        Queen queen = new Queen(Piece.Type.WHITE, Piece.PieceImages.WHITE_QUEEN);
        queen.initPiecePosition(squares[7][3]);
        whitePieces.add(queen);
        queen = new Queen(Piece.Type.BLACK, Piece.PieceImages.BLACK_QUEEN);
        queen.initPiecePosition(squares[0][3]);
        blackPieces.add(queen);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.undo:
                if (moves.size() > 0 && gameMode == GameMode.PLAY) {
                    moves.get(moves.size() - 1).undo();
                }
                break;
            case R.id.resign:
                if (gameMode == GameMode.PLAY) {
                    resign();
                }
                break;
            case R.id.draw:
                if (gameMode == GameMode.PLAY) {
                    offerDraw();
                }
                break;
            case R.id.ai:
                if (gameMode == GameMode.PLAY) {
                    choseRandomMove();
                }
                break;
        }
    }

    private void choseRandomMove() {
        List<Piece> pieces = turn == Piece.Type.WHITE ? whitePieces : blackPieces;
        while (true) {
            int x = (int) (Math.random() * pieces.size());
            Piece s = pieces.get(x);
            s.findAvailableMoves();
            if (s.possibleMoves.size() > 0) {
                x = (int) (Math.random() * s.possibleMoves.size());
                Move move = new Move(s.piecePosition, s.possibleMoves.get(x));
                move.move();
                break;
            }
        }
    }

    private void showGameEnd(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage(message);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < 8; i++) {
                    for (int x = 0; x < 8; x++) {
                        ((ViewGroup) squares[i][x].getParent()).removeView(squares[i][x]);
                    }
                }
                active_piece = null;
                whitePieces = new ArrayList<Piece>();
                blackPieces = new ArrayList<Piece>();
                moves = new ArrayList<>();
                gameMode = GameMode.PLAY;
                canUndo = true;
                turn = Piece.Type.WHITE;
                dialog.dismiss();
                init_board();
                addPiecesToBoard();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void offerDraw() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Draw");
        String sender = turn == Piece.Type.BLACK ? "Black" : "White";
        alertDialog.setTitle(sender + " offers a Draw! ");
        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showGameEnd("Game ended in a Draw");
            }
        });

        alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void resign() {
        showGameEnd(turn.toString() + " Resigned the Game");
    }
}