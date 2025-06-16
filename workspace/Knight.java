
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Knight extends Piece{
    private final boolean color;
    private BufferedImage img;
    
    public Knight(boolean isWhite, String img_file) {
      
      super(isWhite, img_file);
      this.color = isWhite;
        
      //   try {
      //       if (this.img == null) {
      //         this.img = ImageIO.read(getClass().getResource(img_file));
      //       }
      //     } catch (IOException e) {
      //       System.out.println("File not found: " + e.getMessage());
      //     }
    }
    
  
    public String toString(){
      String x;
      if (color){
        x="white";
      } else{
        x="black";
      }
      return "A " + x + " Knight";
    }
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    	ArrayList<Square> moves = new ArrayList<Square>();
      if (start.getRow()+1<8 && start.getCol()+2<8){
        moves.add(board[start.getRow()+1][ start.getCol()+2]);
      }
      if (start.getRow()-1>0 && start.getCol()+2<8){
        moves.add(board[start.getRow()-1][ start.getCol()+2]);
      }
      if (start.getRow()+1<8 && start.getCol()-2>0){
        moves.add(board[start.getRow()+1][ start.getCol()-2]);
      }
      if (start.getRow()-1>0 && start.getCol()-2>0){
        moves.add(board[start.getRow()-1][ start.getCol()-2]);
      }
      if (start.getRow()+2<8 && start.getCol()+1<8){
        moves.add(board[start.getRow()+2][ start.getCol()+1]);
      }
      if (start.getRow()-2>0 && start.getCol()+1<8){
        moves.add(board[start.getRow()-2][ start.getCol()+1]);
      }
      if (start.getRow()+2<8 && start.getCol()-1>0){
        moves.add(board[start.getRow()+2][ start.getCol()-1]);
      }
      if (start.getRow()-2>0 && start.getCol()-1>0){
        moves.add(board[start.getRow()-2][ start.getCol()-1]);
      }
      return moves;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.

    //the code below is for the knight piece
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<Square>();
      if ((start.getRow()+1<8 && start.getCol()+2<8) &&
      ((b.getSquareArray()[start.getRow()+1][ start.getCol()+2].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()+1][ start.getCol()+2].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()+1][ start.getCol()+2]);
      }
      if ((start.getRow()-1>=0 && start.getCol()+2<8) &&
      ((b.getSquareArray()[start.getRow()-1][ start.getCol()+2].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()-1][ start.getCol()+2].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()-1][ start.getCol()+2]);
      }
      if ((start.getRow()+1<8 && start.getCol()-2>=0) &&
      ((b.getSquareArray()[start.getRow()+1][ start.getCol()-2].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()+1][ start.getCol()-2].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()+1][ start.getCol()-2]);
      }
      if ((start.getRow()-1>=0 && start.getCol()-2>=0) &&
      ((b.getSquareArray()[start.getRow()-1][ start.getCol()-2].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()-1][ start.getCol()-2].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()-1][ start.getCol()-2]);
      }
      if ((start.getRow()+2<8 && start.getCol()+1<8) &&
      ((b.getSquareArray()[start.getRow()+2][ start.getCol()+1].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()+2][ start.getCol()+1].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()+2][ start.getCol()+1]);
      }
      if ((start.getRow()-2>=0 && start.getCol()+1<8) &&
      ((b.getSquareArray()[start.getRow()-2][ start.getCol()+1].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()-2][ start.getCol()+1].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()-2][ start.getCol()+1]);
      }
      if ((start.getRow()+2<8 && start.getCol()-1>=0) &&
      ((b.getSquareArray()[start.getRow()+2][ start.getCol()-1].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()+2][ start.getCol()-1].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()+2][ start.getCol()-1]);
      }
      if ((start.getRow()-2>=0 && start.getCol()-1>=0) &&
      ((b.getSquareArray()[start.getRow()-2][ start.getCol()-1].getOccupyingPiece() == null) ||
      (this.getColor() != b.getSquareArray()[start.getRow()-2][ start.getCol()-1].getOccupyingPiece().getColor()))){
        moves.add(b.getSquareArray()[start.getRow()-2][ start.getCol()-1]);
      }
      return moves;
    }
}