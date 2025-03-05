import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// Class representing a chess piece
public class Piece {
    private final boolean color; // true = white, false = black
    private BufferedImage img; // Image of the piece
    private String type; // Type of piece (King, Queen, Bishop, Knight, Pawn, ModifiedRook)

    // Constructor to initialize the piece with its color, image, and type
    public Piece(boolean isWhite, String img_file, String type) {
        this.color = isWhite;
        this.type = type;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file)); // Load image for the piece
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    // Get the color of the piece
    public boolean getColor() {
        return color;
    }

    // Get the image of the piece for drawing on the board
    public Image getImage() {
        return img;
    }

    // Draw the piece on the square by placing it at the appropriate coordinates
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    // Returns a list of squares controlled by this piece, based on its movement rules
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        return getLegalMoves(board, start); // Controlled squares are essentially the legal moves
    }

    // Returns an ArrayList of legal moves for the piece, depending on its type and movement rules
    public ArrayList<Square> getLegalMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();

        // Based on the type of piece, the legal moves are determined
        switch (type) {
            case "King":
                moves.addAll(getKingMoves(board, start)); // King can move one square in any direction
                break;
            case "Queen":
                moves.addAll(getRookMoves(board, start)); // Queen combines Rook and Bishop moves
                moves.addAll(getBishopMoves(board, start));
                break;
            case "Bishop":
                moves.addAll(getBishopMoves(board, start)); // Bishop moves diagonally
                break;
            case "Knight":
                moves.addAll(getKnightMoves(board, start)); // Knight moves in "L" shapes
                break;
            case "Pawn":
                moves.addAll(getPawnMoves(board, start)); // Pawn moves forward but captures diagonally
                break;
            case "ModifiedRook":
                moves.addAll(getModifiedRookMoves(board, start)); // Custom Rook moves up to 3 spaces in any direction
                break;
            default:
                moves.addAll(getRookMoves(board, start)); // Default to Rook moves if type is unknown
        }

        return moves;
    }

    // Returns legal moves for a King (one square in any direction)
    private ArrayList<Square> getKingMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        // Directions array to check all 8 possible moves
        int[] directions = {-1, 0, 1};

        // Loop through all directions (vertical, horizontal, and diagonal)
        for (int dRow : directions) {
            for (int dCol : directions) {
                if (dRow == 0 && dCol == 0) continue; // Skip the current position

                // Calculate new row and column after move
                int newRow = row + dRow;
                int newCol = col + dCol;

                // Check if the new move is valid (within bounds)
                if (isValidMove(board, newRow, newCol)) {
                    moves.add(board[newRow][newCol]);
                }
            }
        }
        return moves;
    }

    // Returns legal moves for a Rook (can move horizontally and vertically)
    private ArrayList<Square> getRookMoves(Square[][] board, Square start) {
        return getSlidingMoves(board, start, new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}});
    }

    // Returns legal moves for a Bishop (can move diagonally)
    private ArrayList<Square> getBishopMoves(Square[][] board, Square start) {
        return getSlidingMoves(board, start, new int[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}});
    }

    // Returns legal moves for a Knight (moves in an "L" shape)
    private ArrayList<Square> getKnightMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        // Possible "L" shaped jumps for Knight
        int[][] jumps = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        // Check each possible jump for validity
        for (int[] jump : jumps) {
            int newRow = row + jump[0];
            int newCol = col + jump[1];
            if (isValidMove(board, newRow, newCol)) {
                moves.add(board[newRow][newCol]);
            }
        }
        return moves;
    }

    // Returns legal moves for a Pawn (moves forward, captures diagonally)
    private ArrayList<Square> getPawnMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int direction = color ? -1 : 1; // Direction depends on the color of the piece (up for white, down for black)

        // Check for forward movement (no capture)
        if (isValidMove(board, row + direction, col)) {
            moves.add(board[row + direction][col]);
        }

        return moves;
    }

    // **CUSTOM PIECE: Modified Rook**
    // This piece moves like a rook but only up to 3 spaces in any direction (no jumping over pieces)
    private ArrayList<Square> getModifiedRookMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        // Loop through all directions and limit movement to 3 squares
        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) { // Limit to 3 squares
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                // Stop if the move is out of bounds or blocked by a piece
                if (!isValidMove(board, newRow, newCol)) break;
                moves.add(board[newRow][newCol]);

                if (board[newRow][newCol].isOccupied()) break; // Stop if a piece occupies the square
            }
        }
        return moves;
    }

    // Helper function for sliding moves (bishop & rook)
    // This method checks moves for sliding pieces (rook and bishop)
    private ArrayList<Square> getSlidingMoves(Square[][] board, Square start, int[][] directions) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        // Loop through all sliding directions
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) { // Move up to 7 squares in any direction
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                // Stop if the move is out of bounds or blocked by a piece
                if (!isValidMove(board, newRow, newCol)) break;
                moves.add(board[newRow][newCol]);

                if (board[newRow][newCol].isOccupied()) break; // Stop if a piece occupies the square
            }
        }
        return moves;
    }

    // Checks if a move is within bounds of the board
    private boolean isValidMove(Square[][] board, int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8; // Ensure move is within the board limits
    }
}
