import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Rook extends Piece {

    public Rook(boolean color, String img_file) {
        super(color, img_file);
    }

    // Controlled squares = legal moves for a Rook
    public ArrayList<Square> getControlledSquares(Board board, Square start) {
        return getLegalMoves(board, start);
    }

    @Override
    public ArrayList<Square> getLegalMoves(Board board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        Square[][] squares = board.getSquareArray();

        int row = start.getRow();
        int col = start.getCol();

        int[][] directions = {
            {1, 0},  // Down
            {-1, 0}, // Up
            {0, 1},  // Right
            {0, -1}  // Left
        };

        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                if (!isValidPosition(newRow, newCol)) break;

                Square target = squares[newRow][newCol];

                if (target.isOccupied()) {
                    if (target.getOccupyingPiece().getColor() != this.getColor()) {
                        moves.add(target); // Capture
                    }
                    break; // Blocked
                }

                moves.add(target);
            }
        }

        return moves;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}