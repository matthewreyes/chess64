package chess;
import pieces.*;
import java.io.*;

/**
 * Chess.java
 * @author justinchong
 *
 */

// added comment
public class Chess {
	
	public static ChessBoard chessboard = new ChessBoard();
	public static boolean playerTurn = true;
	public static boolean goodInput = false;
	public static boolean draw = false;
	
	public static void main(String args[]){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = "";
	
		
		while(true){
			
			printPrompt(playerTurn);
			goodInput=false;
			
			while(goodInput == false){
				try{
					str = reader.readLine();
					System.out.println();
				}
				catch(IOException e){
					e.printStackTrace();
				}
				
			acceptInput(str);
			
			}
			
			
			if(check(!playerTurn)){
				System.out.println("Checkmate! ");
				if (playerTurn==true){
					System.out.println("Black Wins");
					System.exit(0);
				}
				if (playerTurn==false){
					System.out.println("White Wins");
					System.exit(0);
				}
			}
			
			changeTurn();
			
		}
		
		
	}
	
	/**
	 * Prints the prompt of whose turn it is and also calls drawBoard method
	 * @param player
	 */
	public static void printPrompt(boolean player){
		drawBoard();
		
		if(check(!playerTurn)){
			System.out.println("Check!");
		}
		
		if(player == true){
			System.out.print("White's move: ");
		}
		else{
			System.out.print("Black's move: ");
		}
		
	}
	
	
	/**
	 * Converts board pieces into ascii representation and prints board
	 */
	public static void drawBoard(){
		String[][] tiles = new String[8][8];
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(i%2==0){
					if(j%2==0){
						tiles[i][j]="   ";
					}	
					else{
						tiles[i][j]= "## ";
					}
				}
				else{
					if(j%2==0){
						tiles[i][j]="## ";
					}	
					else{
						tiles[i][j]= "   ";
					}
				}	
			}
		}
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chessboard.board[j][i] != null){
					tiles[i][j] = chessboard.board[j][i].getName();
				}
			}

		}
		
		System.out.println();
		for(int i=0;i<8;i++){
			for(int j = 0; j<8;j++){
				System.out.print(tiles[i][j]);
			}
			System.out.print(8-i);
			System.out.println();
		}
		
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	
	/**
	 * Accepts input from user, then tokenizes and translates input
	 * @param stream
	 */
	public static void acceptInput(String stream){
		stream.trim();
		
		//resign
		if (stream.equals("resign")) {
			if(playerTurn==true){
				System.out.println("Black wins");
			}
			else{
				System.out.println("White wins");
			}
			System.exit(0);
		}
		
		//confirm draw
		if (stream.equals("draw") && draw == true) {
			System.out.println("Draw");
			System.exit(0);
		}
		else{
			draw=false;
		}
		
		
		
		
		String[] tokens = stream.split(" ");
		
		//ask for draw
		if(tokens.length==3 && tokens[2].equals("draw?")){
			askDraw();
			makeMove(tokens[0], tokens[1]);
			return;
		}
		
		//promotion case here
		if(tokens.length==3){
			promotion(tokens[0], tokens[1], tokens[2]);
			return;
		}
		
		//the case where there is less than two inputs
		if(tokens.length<2){
			System.out.println("Illegal command, try again");
			return;
		}
		
		if(tokens.length==2){
			makeMove(tokens[0], tokens[1]);
		}
		
	}
	
	/**
	 * Moves chess piece according to given fileranks
	 * @param currFileRank
	 * @param newFileRank
	 */
	public static void makeMove(String currFileRank, String newFileRank){
		Coordinate currCoordinate = new Coordinate(filetoX(currFileRank), filetoY(currFileRank));
		Coordinate newCoordinate = new Coordinate(filetoX(newFileRank), filetoY(newFileRank));
		//System.out.println("X coordinate:" + currCoordinate.getX() + "Y coordinate: " + currCoordinate.getY());
		//System.out.println("X coordinate:" + newCoordinate.getX() + "Y coordinate: " + newCoordinate.getY());
		
		
		if(newFileRank.length()!=2 || currFileRank.length()!=2 || currCoordinate.getX() < 0 || currCoordinate.getX() > 7 || currCoordinate.getY() < 0 || currCoordinate.getY() > 7 || newCoordinate.getX() < 0 || newCoordinate.getX() > 7 || newCoordinate.getY() < 0 || newCoordinate.getY() > 7){
			System.out.println("Illegal move, try again");
			return;
		}
		
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()] == null){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getPlayer() != playerTurn){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[newCoordinate.getX()][newCoordinate.getY()] != null && chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getPlayer() == chessboard.board[newCoordinate.getX()][newCoordinate.getY()].getPlayer()){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].legitMove(currCoordinate, newCoordinate, canCapture(currCoordinate, newCoordinate))){
			if(noObstacles(chessboard.board[currCoordinate.getX()][currCoordinate.getY()], currCoordinate, newCoordinate)){
				chessboard.board[newCoordinate.getX()][newCoordinate.getY()]= chessboard.board[currCoordinate.getX()][currCoordinate.getY()];
				chessboard.board[currCoordinate.getX()][currCoordinate.getY()] = null;
				goodInput=true;
				if(chessboard.board[newCoordinate.getX()][newCoordinate.getY()].getName().equals("wp ") || chessboard.board[newCoordinate.getX()][newCoordinate.getY()].getName().equals("bp ")){
					if(chessboard.board[newCoordinate.getX()][newCoordinate.getY()].getPlayer()==false && newCoordinate.getY()==7){
						chessboard.board[newCoordinate.getX()][newCoordinate.getY()]= new Queen(playerTurn);
					}
				
					if(chessboard.board[newCoordinate.getX()][newCoordinate.getY()].getPlayer()==true && newCoordinate.getY()==0){
					
					}
				}
				return;
			}
		}
		
		if(castling(chessboard.board[currCoordinate.getX()][currCoordinate.getY()], currCoordinate, newCoordinate)){
			goodInput=true;
			return;
		}
		
		
		System.out.println("Illegal move, try again");
		return;
	
		
		
	}
	
	
	/**
	 * Checks for castling 
	 * @param piece
	 * @param currC
	 * @param newC
	 * @return
	 */
	public static boolean castling(Piece piece, Coordinate currC, Coordinate newC){
		
		int dirX = newC.getX()>currC.getX() ? 1 : -1;
		int distance = Math.abs(newC.getX()-currC.getX());
		
		if(distance!=2){
			return false;
		}
		
		if(newC.getY()-currC.getY()!=0){
			return false;
		}
		
		if(piece.getName().equals("wK ") || piece.getName().equals("bK ")){
			;
		}
		else{
			return false;
		}
		
		
		if(piece.gethasMoved() == true){
			return false;
		}
		
		if(check(!playerTurn)){
			return false;
		}
		
		
		
		if(playerTurn == true){
			if(distance*dirX == 2){
				if( chessboard.board[7][7]!=null && chessboard.board[7][7].getName().equals("wR ") && chessboard.board[7][7].gethasMoved()==false){
					if(noObstacles(chessboard.board[7][7], newC, currC)){
						chessboard.board[newC.getX()][newC.getY()]= chessboard.board[currC.getX()][currC.getY()];
						chessboard.board[currC.getX()][currC.getY()] = null;
						chessboard.board[5][7] = chessboard.board[7][7];
						chessboard.board[7][7] = null;
						return true;
					}
				}
			}
			
			if(distance*dirX==-2){
				if( chessboard.board[0][7]!=null && chessboard.board[0][7].getName().equals("wR ") && chessboard.board[0][7].gethasMoved()==false){
					if(noObstacles(chessboard.board[0][7], newC, currC)){
						chessboard.board[newC.getX()][newC.getY()]= chessboard.board[currC.getX()][currC.getY()];
						chessboard.board[currC.getX()][currC.getY()] = null;
						chessboard.board[3][7] = chessboard.board[0][7];
						chessboard.board[0][7] = null;
						return true;
					}
				}
			}

		}
		
		if(playerTurn == false){
			
			if(distance*dirX == 2){
				
				if( chessboard.board[7][0]!=null && chessboard.board[7][0].getName().equals("bR ") && chessboard.board[7][0].gethasMoved()==false){
					
					if(noObstacles(chessboard.board[7][0], newC, currC)){
						
						chessboard.board[newC.getX()][newC.getY()]= chessboard.board[currC.getX()][currC.getY()];
						chessboard.board[currC.getX()][currC.getY()] = null;
						chessboard.board[5][0] = chessboard.board[7][0];
						chessboard.board[7][0] = null;
						return true;
					}
				}
			}
			
			if(distance*dirX==-2){
				
				if( chessboard.board[0][0]!=null && chessboard.board[0][0].getName().equals("bR ") && chessboard.board[0][0].gethasMoved()==false){
					
					if(noObstacles(chessboard.board[0][0], newC, currC)){
						
						chessboard.board[newC.getX()][newC.getY()]= chessboard.board[currC.getX()][currC.getY()];
						chessboard.board[currC.getX()][currC.getY()] = null;
						chessboard.board[3][0] = chessboard.board[0][0];
						chessboard.board[0][0] = null;
						return true;
					}
				}
			}

		}
		
		return false;
	}
		
	
	/**
	 * Converts file to x coordinate
	 * @param fileX
	 * @return
	 */
	public static int filetoX(String fileX){
		int xcoordinate = (int) fileX.toLowerCase().charAt(0) - (int)('a');
		return xcoordinate;
	}
	
	/**
	 * Converts filerank to y coordinate
	 * @param fileY
	 * @return
	 */
	public static int filetoY(String fileY){
		int ycoordinate = 7 - ((int) fileY.toLowerCase().charAt(1) - (int)('1'));
		return ycoordinate;
	}
	
	/**
	 * Changes turn
	 */
	public static void changeTurn(){
		if(playerTurn==true){
			playerTurn=false;
			return;
		}
		if(playerTurn==false){
			playerTurn=true;
			return;
		}
	}
	
	/**
	 * changes draw variable when first draw is asked for
	 */
	public static void askDraw(){
		if(draw==true){
			draw=false;
			return;
		}
		if(draw==false){
			draw=true;
			return;
		}
	}
	
	/**
	 * Checks if new filerank is occupied by opposite color piece
	 * @param currC
	 * @param newC
	 * @return
	 */
	public static boolean canCapture(Coordinate currC, Coordinate newC){
		if(chessboard.board[newC.getX()][newC.getY()]==null){
			return false;
		}
		if(chessboard.board[currC.getX()][currC.getY()].getPlayer() == chessboard.board[newC.getX()][newC.getY()].getPlayer()){
			return false;
		}
		return true;	
	}
	
	/**
	 * Checks if there are no obstacles on the way to the new space
	 * @param piece
	 * @param currC
	 * @param newC
	 * @return
	 */
	public static boolean noObstacles(Piece piece,Coordinate currC, Coordinate newC){
		//Pawn
		if(piece.getName().equals("wp ") || piece.getName().equals("bp ")){
			int dirX = newC.getX()>currC.getX() ? 1 : -1;
			int dirY = newC.getY()>currC.getY() ? 1 : -1; 
			if(Math.abs(newC.getX()-currC.getX()) != 0){
				return true;
			}
			for (int i=1;i<=Math.abs(newC.getY()-currC.getY());i++) {
			    if (chessboard.board[currC.getX()][currC.getY()+i*dirY] != null) {
			      return false;
			      
			    }
			}
			
			  return true;
		}
		
		
		//Rook
		if(piece.getName().equals("wR ") || piece.getName().equals("bR ")){
			int dirX = newC.getX()>currC.getX() ? 1 : -1;
			int dirY = newC.getY()>currC.getY() ? 1 : -1; 
			if(Math.abs(newC.getY()-currC.getY()) != 0){
				for (int i=1;i <= Math.abs(newC.getY()-currC.getY())-1;i++) {
					if (chessboard.board[currC.getX()][currC.getY()+i*dirY] != null) {
						return false;
						
					}
				}
			}
			if(Math.abs(newC.getX()-currC.getX()) != 0){
				for (int i=1;i<=Math.abs(newC.getX()-currC.getX())-1;i++) {
					if (chessboard.board[currC.getX()+i*dirX][currC.getY()] != null) {
						return false;
							
					}
				}
			  return true;
			}	
		
		return true;
		
		}
		
		
		//Bishop
		if(piece.getName().equals("wB ") || piece.getName().equals("bB ")){
			int dirX = newC.getX()>currC.getX() ? 1 : -1;
			int dirY = newC.getY()>currC.getY() ? 1 : -1; 
			for (int i=1;i <= Math.abs(newC.getY()-currC.getY())-1;i++) {
				if (chessboard.board[currC.getX()+i*dirX][currC.getY()+i*dirY] != null) {
					return false;			
				}
			}
			return true;	
		}
		
		//Queen
		if(piece.getName().equals("wQ ") || piece.getName().equals("bQ ")){
			int dirX = newC.getX()>currC.getX() ? 1 : -1;
			int dirY = newC.getY()>currC.getY() ? 1 : -1;
			//vertical movement
			if(Math.abs(newC.getX()-currC.getX()) == 0){
				for (int i=1;i <= Math.abs(newC.getY()-currC.getY())-1;i++) {
					if (chessboard.board[currC.getX()][currC.getY()+i*dirY] != null) {
						return false;
						
					}
				}
				return true;
			}
			
			//horizontal movement
			if(Math.abs(newC.getY()-currC.getY()) == 0){
				for (int i=1;i <= Math.abs(newC.getX()-currC.getX())-1;i++) {
					if (chessboard.board[currC.getX()+i*dirX][currC.getY()] != null) {
						return false;
						
					}
				}
				return true;
			}
			
			//diagonal movement
			for (int i=1;i <= Math.abs(newC.getY()-currC.getY())-1;i++) {
				if (chessboard.board[currC.getX()+i*dirX][currC.getY()+i*dirY] != null) {
					return false;			
				}
				
			}
					
			return true;	
		}
		
		
		
		//this is the end
		return true;
	}
	
	/**
	 * returns coordinate of same colored king
	 * @param player
	 * @return
	 */
	public static Coordinate getKingLocation(boolean player){
		
		Coordinate location = new Coordinate(0,0);
		
		if(player==true){
			for (int i = 0; i<8;i++){
				for (int j = 0; j<8;j++){
					if(chessboard.board[i][j]!= null && chessboard.board[i][j].getName().equals("wK ")){
						location = new Coordinate(i,j);
						return location;
						
					}
				}
			}
		}
		else{
			for (int i = 0; i<8;i++){
				for (int j = 0; j<8;j++){
					if( chessboard.board[i][j]!= null && chessboard.board[i][j].getName().equals("bK ")){
						location = new Coordinate(i,j);
						return location;
					}
				}
			}
		}	
			
		return location;	
	
	}
	
	/**
	 * Sees if your last move put your opponent in check
	 * @param player
	 * @return
	 */
	public static boolean check(boolean player){
		
		
		if(player==true){
			Coordinate blkKing = getKingLocation(false);
			
			
			for (int i = 0; i<8;i++){
				for (int j = 0; j<8;j++){
					Coordinate piece = new Coordinate(i,j);
					if(chessboard.board[i][j] != null && chessboard.board[i][j].getPlayer()==true){
						if(chessboard.board[i][j].legitMove(piece, blkKing, canCapture(piece, blkKing))){
							if(noObstacles(chessboard.board[i][j], piece, blkKing)){
						
								return true;
							}
						}
					}
				}
			}
			
			return false;
		}
		
		if(player==false){
			Coordinate whtKing = getKingLocation(true);
			
			
			for (int i = 0; i<8;i++){
				for (int j = 0; j<8;j++){
					Coordinate piece = new Coordinate(i,j);
					if(chessboard.board[i][j] != null && chessboard.board[i][j].getPlayer()==false){
						if(chessboard.board[i][j].legitMove(piece, whtKing, canCapture(piece, whtKing))){
							if(noObstacles(chessboard.board[i][j], piece, whtKing)){
								
								return true;
							}
						}
					}
				}
			}
			
			return false;
		}
			
		return false;
		
	}
	
	
	/**
	 * Similar to makemove but also promotes pawn
	 * @param currFileRank
	 * @param newFileRank
	 * @param type
	 */
	public static void promotion(String currFileRank, String newFileRank, String type){
		Coordinate currCoordinate = new Coordinate(filetoX(currFileRank), filetoY(currFileRank));
		Coordinate newCoordinate = new Coordinate(filetoX(newFileRank), filetoY(newFileRank));
		//System.out.println("X coordinate:" + currCoordinate.getX() + "Y coordinate: " + currCoordinate.getY());
		//System.out.println("X coordinate:" + newCoordinate.getX() + "Y coordinate: " + newCoordinate.getY());
		
		
		
		if(newFileRank.length()!=2 || currFileRank.length()!=2 || currCoordinate.getX() < 0 || currCoordinate.getX() > 7 || currCoordinate.getY() < 0 || currCoordinate.getY() > 7 || newCoordinate.getX() < 0 || newCoordinate.getX() > 7 || newCoordinate.getY() < 0 || newCoordinate.getY() > 7){
			System.out.println("Illegal move, try again");
			return;
		}
		
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()] == null){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getName().equals("wp ") || chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getName().equals("bp ")){
			;
		}
		else{
			System.out.println("Illegal move, try again");
			System.out.println(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getName());
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getPlayer() != playerTurn){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[newCoordinate.getX()][newCoordinate.getY()] != null && chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getPlayer() == chessboard.board[newCoordinate.getX()][newCoordinate.getY()].getPlayer()){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getPlayer()==false && newCoordinate.getY()!=7){
			System.out.println("Illegal move not at end of board, try again");
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].getPlayer()==true && newCoordinate.getY()!=0){
			System.out.println("Illegal move, try again");
			return;
		}
		
		if(chessboard.board[currCoordinate.getX()][currCoordinate.getY()].legitMove(currCoordinate, newCoordinate, canCapture(currCoordinate, newCoordinate))){
			if(noObstacles(chessboard.board[currCoordinate.getX()][currCoordinate.getY()], currCoordinate, newCoordinate)){
				chessboard.board[newCoordinate.getX()][newCoordinate.getY()]= chessboard.board[currCoordinate.getX()][currCoordinate.getY()];
				chessboard.board[currCoordinate.getX()][currCoordinate.getY()] = null;
				goodInput=true;
				if(type.equals("N")){
					chessboard.board[newCoordinate.getX()][newCoordinate.getY()] = new Knight(playerTurn);
					return;
				}
				if(type.equals("B")){
					chessboard.board[newCoordinate.getX()][newCoordinate.getY()] = new Bishop(playerTurn);
					return;
				}
				if(type.equals("R")){
					chessboard.board[newCoordinate.getX()][newCoordinate.getY()] = new Rook(playerTurn);
					return;
				}
				else{
					chessboard.board[newCoordinate.getX()][newCoordinate.getY()] = new Queen(playerTurn);	
					return;
				}
			}
			
		}
		
		System.out.println("Illegal move, try again");
		return;
		
	}
	
	/**
	 * checkmate method
	 */
	public static void checkMate(){
		
		Coordinate blkKing = getKingLocation(false);
		
		
		for (int i = 0; i<8;i++){
			for (int j = 0; j<8;j++){
				Coordinate piece = new Coordinate(i,j);
				if(chessboard.board[i][j] != null && chessboard.board[i][j].getPlayer()==true){
					if(chessboard.board[i][j].legitMove(piece, blkKing, canCapture(piece, blkKing))){
						if(noObstacles(chessboard.board[i][j], piece, blkKing)){
					
							return;
						}
					}
				}
			}
		}
		
		
		Coordinate whtKing = getKingLocation(true);
		
		
		for (int i = 0; i<8;i++){
			for (int j = 0; j<8;j++){
				Coordinate piece = new Coordinate(i,j);
				if(chessboard.board[i][j] != null && chessboard.board[i][j].getPlayer()==false){
					if(chessboard.board[i][j].legitMove(piece, whtKing, canCapture(piece, whtKing))){
						if(noObstacles(chessboard.board[i][j], piece, whtKing)){
							
							return;
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 
	 * 
	 * if(piece.getName()=="wp"){
				int path = currC.getY()-newC.getY();
				for(int i = 1; i<path;i++){
					if(chessboard.board[currC.getX()][currC.getY()-1] != null){
						return false;
					}
					else{
						return true;
					}	
				}
			}
			else if(piece.getName()=="bp"){
				int path = newC.getY()-currC.getY();
				for(int i = 1; i<path;i++){
					if(chessboard.board[currC.getX()][currC.getY()+i] != null){
						return false;
					}
					
				}
			}	
	 */
	

}
