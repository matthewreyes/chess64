package pieces;
import chess.*;


/**
 * Data Structure for pawn piece
 * @author justinchong
 *
 */
public class Pawn extends Piece{
	
	public Pawn(boolean player){
		
		
		if(player==true){
			this.setPlayer(true);
			this.setName("wp");
		}
		else{
			this.setPlayer(false);
			this.setName("bp");
		}	
	}
	
	public boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture){
		
		int dirX;
		int dirY;
	
		if(getPlayer()==true){
			dirY = currCoordinate.getY() - newCoordinate.getY();
			dirX = currCoordinate.getX() - newCoordinate.getX();
			
			if(dirY == 1 && dirX == 0){
				
				this.sethasMoved(true);
				return true;
			} 
			if(dirY == 2 && dirX==0 && gethasMoved()==false){
				this.sethasMoved(true);
				return true;
			}
			if(dirY == 1 && Math.abs(dirX) == 1 && capture==true){
				this.sethasMoved(true);
				return true;
			}
			
			
			
		}
		
		if(getPlayer()==false){
			dirY = newCoordinate.getY() - currCoordinate.getY();
			dirX = newCoordinate.getX() - currCoordinate.getX();
			
			if(dirY == 1 && dirX == 0){
				this.sethasMoved(true);
				return true;
			} 
			else if(dirY == 2 && dirX==0 && gethasMoved()==false){
				this.sethasMoved(true);
				return true;
			}
			if(dirY == 1 && Math.abs(dirX) == 1 && capture==true){
				this.sethasMoved(true);
				return true;
			}
			
		}
		
		return false;
		
		
	}

	
	/*if(getPlayer()==true){
			if(hasMoved==false && currCoordinate.getX()==newCoordinate.getX() && currCoordinate.getY()==newCoordinate.getY()+2){
				sethasMoved(true);
				return true;
			}
			
			if(hasMoved==false && currCoordinate.getX()==newCoordinate.getX() && currCoordinate.getY()==newCoordinate.getY()+1){
				sethasMoved(true);
				return true;
			}
			
			if(hasMoved==true && currCoordinate.getX()==newCoordinate.getX() && currCoordinate.getY()==newCoordinate.getY()+1){
				sethasMoved(true);
				return true;
			}
			
			
		}
		
		if(getPlayer()==false){
			if(hasMoved==false && currCoordinate.getX()==newCoordinate.getX() && currCoordinate.getY()==newCoordinate.getY()-2){
				
				sethasMoved(true);
				return true;
			}
			
			if(hasMoved==false && currCoordinate.getX()==newCoordinate.getX() && currCoordinate.getY()==newCoordinate.getY()-1){
				sethasMoved(true);
				return true;
			}
			
			if(hasMoved==true && currCoordinate.getX()==newCoordinate.getX() && currCoordinate.getY()==newCoordinate.getY()-1){
				sethasMoved(true);
				return true;
			}
			
			
		}
		*/
}
