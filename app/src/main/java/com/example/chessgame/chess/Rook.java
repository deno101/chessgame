package com.example.chessgame.chess;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(Type type, int imageResource) {
        super(type, imageResource);
    }

    @Override
    public List<Square> findAvailableMoves() {
        List<Square> squares = new ArrayList<>();
        // Find squares South
        int i = 1;
        while (true) {
            if (piecePosition.x + i >= 8) {
                i = 1;
                break;
            }
            if (MainActivity.squares[piecePosition.x + i][piecePosition.y].piece == null) {
                squares.add(MainActivity.squares[piecePosition.x + i][piecePosition.y]);
            } else if (MainActivity.squares[piecePosition.x + i][piecePosition.y].piece != null) {
                if (MainActivity.squares[piecePosition.x + i][piecePosition.y].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x + i][piecePosition.y]);
                }
                i = 1;
                break;
            }
            i++;
        }
        // Search south
        while (true) {
            if (piecePosition.x - i  <=-1) {
                i = 1;
                break;
            }
            if (MainActivity.squares[piecePosition.x - i][piecePosition.y].piece == null) {
                squares.add(MainActivity.squares[piecePosition.x - i][piecePosition.y]);
            } else if (MainActivity.squares[piecePosition.x - i][piecePosition.y].piece != null) {
                if (MainActivity.squares[piecePosition.x - i][piecePosition.y].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x - i][piecePosition.y]);
                }
                i = 1;
                break;
            }
            i++;
        }

        // Search east
        while (true) {
            if (piecePosition.y + i >= 8) {
                i = 1;
                break;
            }
            if (MainActivity.squares[piecePosition.x][piecePosition.y + i].piece == null) {
                squares.add(MainActivity.squares[piecePosition.x][piecePosition.y + i]);
            } else if (MainActivity.squares[piecePosition.x][piecePosition.y + i].piece != null) {
                if (MainActivity.squares[piecePosition.x][piecePosition.y + i].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x][piecePosition.y + i]);
                }
                i = 1;
                break;
            }
            i++;
        }

        // Search east
        while (true) {
            if (piecePosition.y - i <= -1) {
                i = 1;
                break;
            }
            if (MainActivity.squares[piecePosition.x][piecePosition.y - i].piece == null) {
                squares.add(MainActivity.squares[piecePosition.x][piecePosition.y - i]);
            } else if (MainActivity.squares[piecePosition.x][piecePosition.y - i].piece != null) {
                if (MainActivity.squares[piecePosition.x][piecePosition.y - i].piece.type != piecePosition.piece.type) {
                    squares.add(MainActivity.squares[piecePosition.x][piecePosition.y - i]);
                }
                i = 1;
                break;
            }
            i++;
        }
        this.possibleMoves = squares;
        return squares;
    }
}