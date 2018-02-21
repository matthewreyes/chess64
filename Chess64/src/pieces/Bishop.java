package pieces;
import chess.*;
/**
 * Data Structure for queen piece
 * @author matthewreyes
 *
 */

public class Bishop extends Piece{
	
	public Bishop(boolean player){
		
		if(player==true){
			this.setPlayer(true);
			this.setName("wB");
		}
		else{
			this.setPlayer(false);
			this.setName("bB");
		}
	}
	/**
	 *  Checks if the Bishop can move to the new location
	 */
	public boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture){
		
		int movex;
		int movey;
		
		movex = Math.abs(newCoordinate.getX() - currCoordinate.getX());
		movey = Math.abs(newCoordinate.getY() - currCoordinate.getY());
		
		if(movey == movex){
			return true;
		}
		return false;
	}
}
