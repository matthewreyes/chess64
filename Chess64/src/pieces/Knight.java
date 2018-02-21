package pieces;
import chess.*;


/**
 * Data Structure for Knight piece
 * @author justinchong
 *
 */

public class Knight extends Piece{
	
	public Knight(boolean player){
		
		if(player==true){
			this.setPlayer(true);
			this.setName("wN");
		}
		else{
			this.setPlayer(false);
			this.setName("bN");
		}
	}
	
	public boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture){
		
		int movex;
		int movey;
		
		movex = Math.abs(newCoordinate.getX() - currCoordinate.getX());
		movey = Math.abs(newCoordinate.getY() - currCoordinate.getY());
		
		if(movey == 1 && movex == 2 || movey == 2 && movex ==1){
			return true;
		}
		return false;
	}

}


