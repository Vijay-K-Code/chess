import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


//NAME: Nailah George
//PIECE NAME: widow (pawn)
//DESCRIPTION:  a widow that moves and attacks every 2 squares infont and behind it if the space is available 
//(if it's not occupied its own color)

public class Pawn extends Piece {
     
    public Pawn(boolean isWhite, String img_file) {
        super(isWhite, img_file);
        
        color = isWhite;
  
 
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
      }
    
    
        //pre-condition: a starting square and a piece
    //post-condition: highlighted squares
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
     ArrayList<Square> controlledSquares = new ArrayList<>();

     int row = start.getRow();
     int col = start.getCol();

     int[][] moveableAreas = {
      {-2, -2}, {-2,2},
        {2,-2}, {2,2}
     };
     
     for (int[] offset: moveableAreas) {
      int newRow = row + offset[0];
      int newCol = col + offset[1];

      if(newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
        controlledSquares.add(board[newRow][newCol]);
      }

    }
    return controlledSquares;
  }
    

  // pre-condition:a row and a column
    //post-condition: highlighted squares of only where the piece can attack when available

    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<Square>();

      int row = start.getRow();
      int col = start.getCol();

      int[][] moveOffsets = {
        {-2, -2}, {-2,2},
        {2,-2}, {2,2}
      };

      for (int[] offset: moveOffsets) {
        int newRow = row + offset[0];
        int newCol = col + offset[1];

        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
          Square targetSquare = b.getSquareArray()[newRow][newCol];

          if(!targetSquare.isOccupied()) {
            moves.add(targetSquare);
          } else {
            Piece targetPiece = targetSquare.getOccupyingPiece();
            if (targetPiece != null && targetPiece.getColor() != this.color) {
              moves.add(targetSquare);
            }
          }
        }
      }
      
      return moves;
    }

    @Override
    public String toString() {
    if (color)
    return "white";
    else
    return "black";
}
}
