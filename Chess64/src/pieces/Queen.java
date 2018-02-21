package pieces;
import chess.*;
/**
 * Data Structure for queen piece
 * @author matthewreyes
 *
 */

public class Queen extends Piece{
	public Queen(boolean player){
		
		if(player==true){
			this.setPlayer(true);
			this.setName("wQ");
		}
		else{
			this.setPlayer(false);
			this.setName("bQ");
		}
	}
	/**
	 *  Checks if the Queen can move to the new location
	 */
	public boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture){
		
		int movex;
		int movey;
		
		movex = Math.abs(newCoordinate.getX() - currCoordinate.getX());
		movey = Math.abs(newCoordinate.getY() - currCoordinate.getY());
		
		if(movey == 0 && movex != 0 || movey != 0 && movex == 0){
			return true;
		}
		else if(movey == movex){
			return true;
		}
		return false;
	}

}
