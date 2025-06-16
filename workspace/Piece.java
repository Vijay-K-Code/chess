import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Piece {
    private final boolean color; // true = white, false = black
    private BufferedImage img;

    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    // Returns a list of all squares "controlled" by this piece (same as legal moves for a rook)
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        return getLegalMoves(board, start);
    }

    // Returns a list of legal moves (Rook limited to 3 spaces in any straight direction)
    public ArrayList<Square> getLegalMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        // Directions: up, down, left, right
        int[][] directions = {
            {1, 0},  // Down
            {-1, 0}, // Up
            {0, 1},  // Right
            {0, -1}  // Left
        };

        // Check each direction, limited to 3 squares max
        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                // Ensure the move is within bounds
                if (!isValidMove(board, newRow, newCol)) break;

                Square target = board[newRow][newCol];

                // Stop adding moves if a piece is blocking the path
                if (target.isOccupied()) {
                    if (target.getPiece().getColor() != this.color) {
                        moves.add(target); // Capture enemy piece
                    }
                    break;
                }

                moves.add(target);
            }
        }

        return moves;
    }

    // Helper function to check board boundaries
    private boolean isValidMove(Square[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }
}
