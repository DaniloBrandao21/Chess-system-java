package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
//		Criando tabuleiro 8/8 
		board = new Board(8, 8);
		
//		Iniciando a partida
		initialSetup();
	}

	public ChessPiece[][] getPieces(){
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumn()];
		
		for(int i = 0; i < board.getRows(); i++) {
			
			for(int j = 0; j < board.getColumn(); j++ ) {
				mat[i][j] = (ChessPiece)board.piece(i, j);
				
			}
		}
		return mat;
	}
	
//	Método responsável por inicial a partida de Xadrez
//	Colocando as peças no tabuleiro
	private void initialSetup(){
		board.placePiece(new Rook(board,  Color.WHITE), new Position(0,0));
		board.placePiece(new King(board,  Color.BLACK), new Position(0,4));
		board.placePiece(new King(board,  Color.WHITE), new Position(7,4));
	}
}
