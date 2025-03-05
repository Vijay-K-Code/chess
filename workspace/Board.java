import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private GameWindow g;
    private Square[][] board;
    private boolean whiteTurn;
    private Piece currPiece;
    private Square fromMoveSquare;
    private int currX, currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // Populate the board with alternating black and white squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color color = (row + col) % 2 == 0 ? Color.WHITE : Color.BLACK;
                board[row][col] = new Square(row, col, color);
                this.add(board[row][col]);
            }
        }

        initializePieces();
        this.setPreferredSize(new Dimension(400, 400));
        whiteTurn = true;
    }

    // Initialize the board with pieces
    private void initializePieces() {
        // Example: Place kings on the board
        board[0][4].put(new Piece(false, "resources/bking.png")); // Black King
        board[7][4].put(new Piece(true, "resources/wking.png"));  // White King
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Square startSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        if (startSquare.hasPiece()) {
            currPiece = startSquare.getPiece();
            fromMoveSquare = startSquare;
            startSquare.setDisplay(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        // Remove any highlighting of legal moves
        for (Square[] row : board) {
            for (Square s : row) {
                s.setBorder(null);
            }
        }

        if (currPiece != null && fromMoveSquare != null) {
            // Check if the move is legal
            if (currPiece.isLegalMove(fromMoveSquare, endSquare, board)) {
                // Move the piece
                endSquare.put(currPiece);
                fromMoveSquare.removePiece();
                whiteTurn = !whiteTurn; // Change turn
            } else {
                // Illegal move, snap the piece back to its original square
                fromMoveSquare.put(currPiece);
            }
        }

        fromMoveSquare.setDisplay(true);
        currPiece = null;
        repaint();
    }

    // mouse dragged is called when the user is holding the piece and moving the mouse
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        // Highlight all the squares that are legal to move to!
        if (currPiece != null) {
            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Empty method (not needed for this functionality)
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Empty method (not needed for this functionality)
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Empty method (not needed for this functionality)
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Empty method (not needed for this functionality)
    }
}
