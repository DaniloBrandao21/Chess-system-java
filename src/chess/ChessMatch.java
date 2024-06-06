package chess;

import boardgame.Board;
import boardgame.Piece;
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
	
	
//	performChessMove -> retira a peça da posição de origem e coloca na posição de destino
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPossition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPossition.toPosition();
		validadeSourcePosition(source);
		Piece capturePiece = makeMove(source, target);
		return (ChessPiece)capturePiece;
	}
	
//	makeMove -> Faz o movimento da peça e retorna uma possivel peça capturada
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturePiece;
	}
	
//	validadeSourcePosition -> valida posição original da peça
	private void validadeSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
	}
	
//	placeNewPiece -> Função para colocar peça passando as coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece chessPiece){
		
//	 	Convertento uma ChessPosition para uma position
		board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
	}
	
	
//	Método responsável por inicial a partida de Xadrez
//	Colocando as peças no tabuleiro
	private void initialSetup(){
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
