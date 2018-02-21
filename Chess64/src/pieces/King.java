package pieces;
import chess.*;

/**
 * Data Structure for king piece
 * @author justinchong
 *
 */
public class King extends Piece{
	
	public King(boolean player){
			
		if(player==true){
			this.setPlayer(true);
			this.setName("wK");
		}
		else{
			this.setPlayer(false);
			this.setName("bK");
		}
	}
		
	public boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture){
		
		int movex;
		int movey;
		
		movex = Math.abs(newCoordinate.getX() - currCoordinate.getX());
		movey = Math.abs(newCoordinate.getY() - currCoordinate.getY());
		
		if(movey == 0 && movex == 1 || movey == 1 && movex == 0){
		 sethasMoved(true);
		return true;
		}
		else if(movey == movex){
			if(movey == 1 && movex == 1){
				sethasMoved(true);
				return true;
			}
		}
		return false;
	}
}
