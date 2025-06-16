import java.awt.Graphics;
import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(boolean color, String img_file) {
        super(color, img_file);
    }

    // Returns a list of all squares "controlled" by this piece
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        return getLegalMoves(board, start);
    }

    // Returns a list of legal moves (limited to 3 squares in any straight direction)
    public ArrayList<Square> getLegalMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        int[][] directions = {
            {1, 0},   // Down
            {-1, 0},  // Up
            {0, 1},   // Right
            {0, -1}   // Left
        };

        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                if (!isValidMove(board, newRow, newCol)) break;

                Square target = board[newRow][newCol];

                if (target.isOccupied()) {
                    if (target.getOccupyingPiece().getColor() != this.getColor()) {
                        moves.add(target); // Enemy piece can be captured
                    }
                    break; // Stop, path is blocked
                }

                moves.add(target);
            }
        }

        return moves;
    }

    // Check bounds
    private boolean isValidMove(Square[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    // Draws the rook at the center of the square
    @Override
    public void draw(Graphics g, Square s) {
        if (getImage() != null) {
            g.drawImage(getImage(), s.getX(), s.getY(), s.getWidth(), s.getHeight(), null);
        }
    }
}
