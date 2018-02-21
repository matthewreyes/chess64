package chess;
import pieces.*;

/**
 * Coordinate data structure for fileranks
 * @author justinchong
 *
 */

public class Coordinate {
	
	public int x;
	public int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}

	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setY(int y){
		this.y = y;;
	}

}
