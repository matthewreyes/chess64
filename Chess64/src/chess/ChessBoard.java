package chess;
import pieces.*;


/** Chessboard of pieces
 * 
 * @author matthewreyes
 *
 */
public class ChessBoard {

	public Piece[][] board;
	
	public ChessBoard(){
		board = new Piece[8][8];
		makeBoard();		
	}
	
	
	/**
	 * Makes the initial board with chess pieces in original spots
	 */
	public void makeBoard(){
		
		/**
		 * Creates pieces for black player
		 */
		for(int i = 0;i<8;i++){
			board[i][1] = new Pawn(false);
		}

		board[0][0] = new Rook(false);
		board[1][0] = new Knight(false);
		board[2][0] = new Bishop(false);
		board[3][0] = new Queen(false);
		board[4][0] = new King(false);
		board[5][0] = new Bishop(false);
		board[6][0] = new Knight(false);
		board[7][0] = new Rook(false);
		
		/**
		 * Creates pieces for white player
		 */
		for(int i = 0;i<8;i++){
			board[i][6] = new Pawn(true);
		}
		
		board[0][7] = new Rook(true);
		board[1][7] = new Knight(true);
		board[2][7] = new Bishop(true);
		board[3][7] = new Queen(true);
		board[4][7] = new King(true);
		board[5][7] = new Bishop(true);
		board[6][7] = new Knight(true);
		board[7][7] = new Rook(true);
	}
	
	

}
