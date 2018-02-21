package pieces;
import chess.*;
/**
 * Data Structure for rook piece
 * @author matthewreyes
 *
 */

public class Rook extends Piece{
	
	public Rook(boolean player){
		
		if(player==true){
			this.setPlayer(true);
			this.setName("wR");
		}
		else{
			this.setPlayer(false);
			this.setName("bR");
		}	
	}
	/**
	 *  Checks if the Rook can move to the new location
	 */
	public boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture){
		
		int movex;
		int movey;
		
		movex = Math.abs(newCoordinate.getX() - currCoordinate.getX());
		movey = Math.abs(newCoordinate.getY() - currCoordinate.getY());
		
		if(movey == 0 && movex != 0 || movey != 0 && movex == 0){
			sethasMoved(true);
			return true;
			
		}
		return false;
	}

}

