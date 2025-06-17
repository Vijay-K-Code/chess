import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

// Implements the chess board and handles piece movement
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private static final String RESOURCES_WROOK_PNG = "wrook.png";
    private static final String RESOURCES_BROOK_PNG = "brook.png";
    private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
    private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
    private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
    private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
    private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
    private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
    private static final String RESOURCES_BKING_PNG = "bking.png";
    private static final String RESOURCES_WKING_PNG = "wking.png";

    private final Square[][] board;
    private final GameWindow g;

    private boolean whiteTurn;
    private Piece currPiece;
    private Square fromMoveSquare;

    private int currX;
    private int currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // Initialize squares (Alternating black and white)
        for (int row = 0; row < 8; row++) {
    for (int col = 0; col < 8; col++) {
        boolean isWhite = (row + col) % 2 == 0;
        board[row][col] = new Square(this, isWhite, row, col); // 'this' refers to the Board instance
        this.add(board[row][col]);
    }
}


        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    // Sets up rooks for each player
    private void initializePieces() {
        board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG)); // Black Rook
        board[0][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));//Black Knight
        board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG)); // Black Bishop
        board[0][3].put(new Queen(false, RESOURCES_BQUEEN_PNG)); // Black Queen
        board[0][4].put(new King(false, RESOURCES_BKING_PNG)); // Black King
        board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG)); // Black Bishop
        board[0][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG)); // Black Knight
        board[0][7].put(new Rook(false, RESOURCES_BROOK_PNG)); // Black Rook
        board[1][0].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][1].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][2].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][3].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][4].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][5].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][6].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[1][7].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // Black Pawn
        board[7][0].put(new Rook(true, RESOURCES_WROOK_PNG));  // White Rook
        board[7][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG)); // White Knight
        board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG)); // White Bishop
        board[7][3].put(new Queen(true, RESOURCES_WQUEEN_PNG)); // White Queen
        board[7][4].put(new King(true, RESOURCES_WKING_PNG)); // White King
        board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG)); // White Bishop
        board[7][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG)); // White Knight
        board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG));  // White Rook
        board[6][0].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][1].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][2].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][3].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][4].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][5].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][6].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
        board[6][7].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // White Pawn
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
            }
        }
        if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn) || (!currPiece.getColor() && !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn) return;
            if (currPiece.getColor() && !whiteTurn) return;
            sq.setDisplay(false);
        }
        repaint();
    }

    // Moves the piece only if the move is legal
    @Override
public void mouseReleased(MouseEvent e) {
    if ((currPiece.color==true)&&(!whiteTurn)||(currPiece.color==false)&&(whiteTurn)){
return;
    } else{
        
        
    }
    Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

    if (currPiece != null && fromMoveSquare != null && endSquare != null) {
        ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);

        if (legalMoves.contains(endSquare)) {
            // Simulate the move
            Piece originalEndPiece = endSquare.getOccupyingPiece(); // might be null
            Square originalFromSquare = fromMoveSquare;

            // Move the piece
            endSquare.put(currPiece);
            fromMoveSquare.removePiece();

            // Check if own king is in check after the move
            boolean inCheckAfterMove = isInCheck(currPiece.getColor());

            if (inCheckAfterMove) {
                // Undo move if it leaves king in check
                fromMoveSquare.put(currPiece);
                endSquare.put(originalEndPiece); // restore captured piece (if any)
            } else {
                // Move is valid
                whiteTurn = !whiteTurn;
            }
        }
    }

    if (fromMoveSquare != null)
        fromMoveSquare.setDisplay(true);

    currPiece = null;
    repaint();
}



    public boolean isInCheck(boolean kingColor) {
    Square[][] squares = this.getSquareArray();
    Square kingSquare = null;

    // Step 1: Locate the king of the given color
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Square sq = squares[row][col];
            if (sq.isOccupied()) {
                Piece p = sq.getOccupyingPiece();
                if (p instanceof King && p.getColor() == kingColor) {
                    kingSquare = sq;
                    break;
                }
            }
        }
        if (kingSquare != null) break;
    }

    if (kingSquare == null) {
        // Safety check — should never happen
        throw new IllegalStateException("King of specified color not found on the board.");
    }

    // Step 2: Check if any opposing piece controls the king's square
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Square sq = squares[row][col];
            if (sq.isOccupied()) {
                Piece p = sq.getOccupyingPiece();
                if (p.getColor() != kingColor) {
                    ArrayList<Square> controlledSquares = p.getControlledSquares(this.board, sq);
                    if (controlledSquares.contains(kingSquare)) {
                        return true;
                    }
                }
            }
        }
    }

    return false;
}


    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
