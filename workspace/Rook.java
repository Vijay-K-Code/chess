//Vijay Kannan
//Custom Rook!
//Special rook can move straight (up/down/left/right) like a normal rook, but it’s limited to just 3 spaces max in any direction.

import java.util.ArrayList;

public class Rook extends Piece {


    public Rook(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    // Just a toString so I can see which rook this is when I need to
    public String toString() {
        return this.getColor() ? "A White Rook" : "A Black Rook";
    }

    // This method returns the squares the rook *controls* (like attacking range)
    // Still straight lines, but only up to 3 squares in each direction
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int boardSize = board.length;

        int[][] directions = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) {
                int newRow = row + dir[0] * i;
                int newCol = col + dir[1] * i;

                // Make sure we don’t go off the board
                if (newRow < 0 || newRow >= boardSize || newCol < 0 || newCol >= boardSize)
                    break;

                Square sq = board[newRow][newCol];
                controlledSquares.add(sq);

                // Stop if we hit another piece
                if (sq.isOccupied())
                    break;
            }
        }

        return controlledSquares;
    }

    // This is the method that returns actual legal moves (like where I can really move)
    // Same logic as controlled squares, but I can’t move onto my own team
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        Square[][] board = b.getSquareArray();
        boolean isWhite = this.getColor();
        int boardSize = board.length;

        int[][] directions = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) {
                int newRow = row + dir[0] * i;
                int newCol = col + dir[1] * i;

                if (newRow < 0 || newRow >= boardSize || newCol < 0 || newCol >= boardSize)
                    break;

                Square sq = board[newRow][newCol];

                if (!sq.isOccupied()) {
                    legalMoves.add(sq); // Empty square, I can move here
                } else {
                    // Enemy piece , I can capture it
                    if (sq.getOccupyingPiece().getColor() != isWhite) {
                        legalMoves.add(sq);
                    }
                    // Stop here no matter what if it's occupied
                    break;
                }
            }
        }

        return legalMoves;
    }
}
