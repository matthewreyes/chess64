package pieces;
import chess.*;

/**
 * Abstract class for a chess piece
 * @author justinchong
 *
 */

public abstract class Piece {
	

	/** true if player of piece is white, false if player is black
	 * 
	 */
	private boolean player = true;
	private boolean hasMoved = false;
	private	String name;
	
	
	/** Methods to set and get the player of the piece
	 * 
	 * @param color
	 */
	public void setPlayer(boolean color){
		player = color;
	}
	
	public boolean getPlayer(){
		return this.player;
	}
	
	/** Method to check if move is a legitimate basic move of selected piece
	 * 
	 * @return
	 */
	public abstract boolean legitMove(Coordinate currCoordinate, Coordinate newCoordinate, boolean capture);
	
	/**
	 * getters and setters
	 * 
	 *
	 */
	public String getName(){
		return this.name+" ";
	};
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean gethasMoved(){
		return this.hasMoved;
	}
	
	public void sethasMoved(boolean t){
		this.hasMoved=t;
	}
	
	
}
