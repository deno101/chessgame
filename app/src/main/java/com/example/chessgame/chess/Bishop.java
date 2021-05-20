package com.example.chessgame.chess;

import com.example.chessgame.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Type type, int imageResource) {
        super(type, imageResource);
    }

    public Bishop(Piece piece) {
        super(piece);
    }

    @Override
    public void findAvailableMoves() {
        List<Square> squares = new ArrayList<>();
        List<Square> defendingPieces = new ArrayList<>();

        MainActivity mainActivity = piecePosition.parentContext;
        // Find squares South
        int i = 1, x = 1;
        while (true) {
            if (piecePosition.y + i >= 8 || piecePosition.x + i >= 8) {
                i = 1;
                break;
            }
            if (mainActivity.squares[piecePosition.x + i][piecePosition.y + i].piece == null) {
                squares.add(mainActivity.squares[piecePosition.x + i][piecePosition.y + i]);
            } else if (mainActivity.squares[piecePosition.x + i][piecePosition.y + i].piece != null) {
                if (mainActivity.squares[piecePosition.x + i][piecePosition.y + i].piece.type != piecePosition.piece.type) {
                    squares.add(mainActivity.squares[piecePosition.x + i][piecePosition.y + i]);
                } else {
                    defendingPieces.add(mainActivity.squares[piecePosition.x + i][piecePosition.y + i]);
                }
                i = 1;
                break;
            }
            i++;
        }
        // Search North
        while (true) {
            if (piecePosition.x - i <= -1 || piecePosition.y - i <= -1) {
                i = 1;
                break;
            }
            if (mainActivity.squares[piecePosition.x - i][piecePosition.y - i].piece == null) {
                squares.add(mainActivity.squares[piecePosition.x - i][piecePosition.y - i]);
            } else if (mainActivity.squares[piecePosition.x - i][piecePosition.y - i].piece != null) {
                if (mainActivity.squares[piecePosition.x - i][piecePosition.y - i].piece.type != piecePosition.piece.type) {
                    squares.add(mainActivity.squares[piecePosition.x - i][piecePosition.y - i]);
                } else {
                    defendingPieces.add(mainActivity.squares[piecePosition.x - i][piecePosition.y - i]);
                }
                i = 1;
                break;
            }
            i++;
        }

        // Search east
        while (true) {
            if (piecePosition.y + i >= 8 || piecePosition.x - i <= -1) {
                i = 1;
                break;
            }
            if (mainActivity.squares[piecePosition.x - i][piecePosition.y + i].piece == null) {
                squares.add(mainActivity.squares[piecePosition.x - i][piecePosition.y + i]);
            } else if (mainActivity.squares[piecePosition.x - i][piecePosition.y + i].piece != null) {
                if (mainActivity.squares[piecePosition.x - i][piecePosition.y + i].piece.type != piecePosition.piece.type) {
                    squares.add(mainActivity.squares[piecePosition.x - i][piecePosition.y + i]);
                } else {
                    defendingPieces.add(mainActivity.squares[piecePosition.x - i][piecePosition.y + i]);
                }
                i = 1;
                break;
            }
            i++;
        }

        // Search east
        while (true) {
            if (piecePosition.y - i <= -1 || piecePosition.x + i >= 8) {
                i = 1;
                break;
            }
            if (mainActivity.squares[piecePosition.x + i][piecePosition.y - i].piece == null) {
                squares.add(mainActivity.squares[piecePosition.x + i][piecePosition.y - i]);
            } else if (mainActivity.squares[piecePosition.x + i][piecePosition.y - i].piece != null) {
                if (mainActivity.squares[piecePosition.x + i][piecePosition.y - i].piece.type != piecePosition.piece.type) {
                    squares.add(mainActivity.squares[piecePosition.x + i][piecePosition.y - i]);
                } else {
                    defendingPieces.add(mainActivity.squares[piecePosition.x + i][piecePosition.y - i]);
                }
                i = 1;
                break;
            }
            i++;
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
        List<Square> attackVector = new ArrayList<>();
        int fx = 0, fy = 0;
        // it is a boolean to prevent infinate loops in the queen
        boolean isAttacker = true;

        if (king.piecePosition.x < this.piecePosition.x && king.piecePosition.y < this.piecePosition.y) {
            fx = -1;
            fy = -1;
        } else if (king.piecePosition.x < this.piecePosition.x && king.piecePosition.y > this.piecePosition.y) {
            fx = -1;
            fy = 1;
        } else if (king.piecePosition.x > this.piecePosition.x && king.piecePosition.y < this.piecePosition.y) {
            fx = 1;
            fy = -1;
        } else if (king.piecePosition.x > this.piecePosition.x && king.piecePosition.y > this.piecePosition.y) {
            fx = 1;
            fy = 1;
        }else {
            isAttacker = false;
        }

        int x = this.piecePosition.x;
        int y = this.piecePosition.y;

        while (x != king.piecePosition.x && y != king.piecePosition.y && isAttacker) {
            attackVector.add(this.piecePosition.parentContext.squares[x][y]);
            x += fx;
            y += fy;
        }

        return attackVector;
    }
}
