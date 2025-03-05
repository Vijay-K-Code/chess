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
    board[0][4].put(new Piece(false, RESOURCES_BKING_PNG)); // Black King
    board[7][4].put(new Piece(true, RESOURCES_WKING_PNG));  // White King
}

@Override
public void mouseReleased(MouseEvent e) {
    Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

    if (currPiece != null && fromMoveSquare != null) {
        // Check if the move is legal
        if (currPiece.isLegalMove(fromMoveSquare, endSquare, board)) {
            // Move the piece
            endSquare.put(currPiece);
            fromMoveSquare.removePiece();
            whiteTurn = !whiteTurn; // Change turn
        }
    }

    fromMoveSquare.setDisplay(true);
    currPiece = null;
    repaint();
}
