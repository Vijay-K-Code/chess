import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// Class representing a chess piece
public class Piece {
    private final boolean color; // true = white, false = black
    private BufferedImage img;
    private String type; // Type of piece (King, Queen, Bishop, Knight, Pawn, ModifiedRook)

    // Constructor for a piece with its color and image file
    public Piece(boolean isWhite, String img_file, String type) {
        this.color = isWhite;
        this.type = type;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    // Get the color of the piece
    public boolean getColor() {
        return color;
    }

    // Get the image of the piece
    public Image getImage() {
        return img;
    }

    // Draw the piece on a square
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    // Returns a list of squares controlled by this piece
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        return getLegalMoves(board, start);
    }

    // Returns an ArrayList of legal moves for the piece
    public ArrayList<Square> getLegalMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();

        switch (type) {
            case "King":
                moves.addAll(getKingMoves(board, start));
                break;
            case "Queen":
                moves.addAll(getRookMoves(board, start));
                moves.addAll(getBishopMoves(board, start));
                break;
            case "Bishop":
                moves.addAll(getBishopMoves(board, start));
                break;
            case "Knight":
                moves.addAll(getKnightMoves(board, start));
                break;
            case "Pawn":
                moves.addAll(getPawnMoves(board, start));
                break;
            case "ModifiedRook":
                moves.addAll(getModifiedRookMoves(board, start));
                break;
            default:
                moves.addAll(getRookMoves(board, start));
        }

        return moves;
    }

    // Returns legal moves for a king (one square in any direction)
    private ArrayList<Square> getKingMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        int[] directions = {-1, 0, 1};

        for (int dRow : directions) {
            for (int dCol : directions) {
                if (dRow == 0 && dCol == 0) continue;
                int newRow = row + dRow;
                int newCol = col + dCol;
                if (isValidMove(board, newRow, newCol)) {
                    moves.add(board[newRow][newCol]);
                }
            }
        }
        return moves;
    }

    // Returns legal moves for a rook (horizontal & vertical)
    private ArrayList<Square> getRookMoves(Square[][] board, Square start) {
        return getSlidingMoves(board, start, new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}});
    }

    // Returns legal moves for a bishop (diagonal movement)
    private ArrayList<Square> getBishopMoves(Square[][] board, Square start) {
        return getSlidingMoves(board, start, new int[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}});
    }

    // Returns legal moves for a knight (L-shape movement)
    private ArrayList<Square> getKnightMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        int[][] jumps = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] jump : jumps) {
            int newRow = row + jump[0];
            int newCol = col + jump[1];
            if (isValidMove(board, newRow, newCol)) {
                moves.add(board[newRow][newCol]);
            }
        }
        return moves;
    }

    // Returns legal moves for a pawn (forward movement, captures diagonally)
    private ArrayList<Square> getPawnMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int direction = color ? -1 : 1;

        if (isValidMove(board, row + direction, col)) {
            moves.add(board[row + direction][col]);
        }
        return moves;
    }

    // **CUSTOM PIECE: Modified Rook**
    // Moves like a rook but only up to 3 spaces in any direction (no jumping over pieces)
    private ArrayList<Square> getModifiedRookMoves(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) { // Limited to 3 squares
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                if (!isValidMove(board, newRow, newCol)) break; // Stop if out of bounds or blocked
                moves.add(board[newRow][newCol]);

                if (board[newRow][newCol].isOccupied()) break; // Stop if there's a piece
            }
        }
        return moves;
    }

    // Helper function for sliding moves (bishop & rook)
    private ArrayList<Square> getSlidingMoves(Square[][] board, Square start, int[][] directions) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                if (!isValidMove(board, newRow, newCol)) break;
                moves.add(board[newRow][newCol]);

                if (board[newRow][newCol].isOccupied()) break;
            }
        }
        return moves;
    }

    // Checks if a move is within bounds and valid
    private boolean isValidMove(Square[][] board, int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
