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
        ArrayList<Square> controlledSquares = new ArrayList<>();

        // Add squares controlled by the piece based on its type
        switch (type) {
            case "King":
                controlledSquares.addAll(getKingControlledSquares(board, start));
                break;
            case "Queen":
                controlledSquares.addAll(getRookControlledSquares(board, start));
                controlledSquares.addAll(getBishopControlledSquares(board, start));
                break;
            case "Bishop":
                controlledSquares.addAll(getBishopControlledSquares(board, start));
                break;
            case "Knight":
                controlledSquares.addAll(getKnightControlledSquares(board, start));
                break;
            case "Pawn":
                controlledSquares.addAll(getPawnControlledSquares(board, start));
                break;
            case "ModifiedRook":
                controlledSquares.addAll(getModifiedRookControlledSquares(board, start));
                break;
            default:
                controlledSquares.addAll(getRookControlledSquares(board, start));
        }

        return controlledSquares;
    }

    // Helper methods for each piece's control logic

    // King controls one square in any direction
    private ArrayList<Square> getKingControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        int[] directions = {-1, 0, 1}; // Possible moves in each direction
        for (int dRow : directions) {
            for (int dCol : directions) {
                if (dRow == 0 && dCol == 0) continue; // Skip the square it's currently on
                int newRow = row + dRow;
                int newCol = col + dCol;
                if (isValidMove(board, newRow, newCol)) {
                    controlled.add(board[newRow][newCol]);
                }
            }
        }
        return controlled;
    }

    // Queen controls squares like a rook and a bishop combined
    private ArrayList<Square> getRookControlledSquares(Square[][] board, Square start) {
        return getSlidingControlledSquares(board, start, new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}});
    }

    private ArrayList<Square> getBishopControlledSquares(Square[][] board, Square start) {
        return getSlidingControlledSquares(board, start, new int[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}});
    }

    // Knight controls squares in an "L" shape
    private ArrayList<Square> getKnightControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();
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
                controlled.add(board[newRow][newCol]);
            }
        }
        return controlled;
    }

    // Pawn controls squares diagonally for captures
    private ArrayList<Square> getPawnControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int direction = color ? -1 : 1; // Direction of movement (up for white, down for black)

        // Pawns capture diagonally
        if (isValidMove(board, row + direction, col + 1)) controlled.add(board[row + direction][col + 1]);
        if (isValidMove(board, row + direction, col - 1)) controlled.add(board[row + direction][col - 1]);

        return controlled;
    }

    // Modified Rook controls squares up to 3 spaces in each direction
    private ArrayList<Square> getModifiedRookControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            for (int i = 1; i <= 3; i++) { // Limited to 3 squares
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                if (!isValidMove(board, newRow, newCol)) break;
                controlled.add(board[newRow][newCol]);
            }
        }
        return controlled;
    }

    // Helper method for sliding pieces (rook, bishop, and queen)
    private ArrayList<Square> getSlidingControlledSquares(Square[][] board, Square start, int[][] directions) {
        ArrayList<Square> controlled = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];

                if (!isValidMove(board, newRow, newCol)) break;
                controlled.add(board[newRow][newCol]);
            }
        }
        return controlled;
    }

    // Checks if a move is within bounds and valid
    private boolean isValidMove(Square[][] board, int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
