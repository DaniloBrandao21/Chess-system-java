package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOntheBoard = new ArrayList<Piece>();
	private List<Piece> capturedList = new ArrayList<>();
	private boolean check;
	private boolean checkMate;
	

	
	public ChessMatch() {
//		Criando tabuleiro 8/8 
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		
//		Iniciando a partida
		initialSetup();
	}
	
	public int getTurn(){
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
//	performChessMove -> retira a peça da posição de origem e coloca na posição de destino
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturePiece = makeMove(source, target);
		
		
		//Testando se o jogador se colocou em check
		if(testCheck(currentPlayer)) { 
			undoMove(source, target, capturePiece);
			throw new ChessException("You can't put yourself in check");
		}
		
//		** Se a jogada colocar o rei adversário em check **
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();
		}
		return (ChessPiece)capturePiece;
	}
	
//	makeMove -> Faz o movimento da peça e retorna uma possivel peça capturada
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);
		
//		Sempre que voltar um peça capturada, remove do tabuleiro e adiciona na lista de capturadas
		if(capturePiece != null) {
			piecesOntheBoard.remove(capturePiece);
			capturedList.add(capturePiece);
		}
		
//		#Movimento especial roque posicionando a posse (lado do rei)
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
//		#Movimento especial roque posicionando a posse (lado da rainha)
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		
		return capturePiece;
	}
	
//	undoMove -> Desfazer o movimento
	private void undoMove(Position source, Position target, Piece capturedPice){
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPice != null) {
			board.placePiece(capturedPice, target);
			capturedList.remove(capturedPice);
			piecesOntheBoard.add(capturedPice);
		}
		
//		#Desfazendo roque (lado do rei)
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
//		#Desfazendo roque (lado da rainha)
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		
	}
	
//	validadeSourcePosition -> valida posição original da peça
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()){
			
			boolean verificar = board.piece(position).isThereAnyPossibleMove();
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
//	validadeSourcePosition -> valida posição destino está disponivel
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
//	nextTurn -> Passa o turno e muda o jogador atual
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	
	private Color opponent(Color color){
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
//	king -> Faz uma varredura no campo procurando o rei de uma determinada cor
	private ChessPiece king(Color color){
		List<Piece> list = 
		piecesOntheBoard.stream()
		.filter(x -> ((ChessPiece)x)
		.getColor() == color)
		.collect(Collectors.toList());
		for(Piece p:list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
//	testCheck -> Verificar se o rei esta em check
	private boolean testCheck(Color color) {
		 Position kingPosition = king(color).getChessPosition().toPosition();
		 List<Piece> opponentPieces = 
			piecesOntheBoard.stream()
			.filter(x -> ((ChessPiece)x)
			.getColor() == opponent(color))
			.collect(Collectors.toList());
		 
//		 *Verifica todos os movimentos possiveis para as peças do oponente
//		 Se alguma peça puder chegar ao rei, retorna true. Caso contrário, false*
		 for(Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		 }
		 return false;
	}
	
	
//	testCheckMate -> testa se o rei esta em xequemate
	public boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list =
				piecesOntheBoard.stream()
				.filter(x -> ((ChessPiece)x)
				.getColor() == color)
				.collect(Collectors.toList());
		
		for (Piece p: list) {
			boolean[][] mat = 	p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for(int j = 0; j < board.getColumn(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position taget = new Position(i	,j);
						Piece capturedPiece = makeMove(source, taget);
						boolean testCheck = testCheck(color);
						undoMove(source, taget, capturedPiece);
						if(!testCheck) {
							return false;
						}
						
					}
				}
			}
		}
		
		return true;
		
		
	}
	
	
	
//	placeNewPiece -> Função para colocar peça passando as coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece chessPiece){
//	 	Convertento uma ChessPosition para uma position
		board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
		
//		Sempre que instanciar uma nova peça deve adicionar ela na lista de peças no tabuleiro
		piecesOntheBoard.add(chessPiece);
	}
	
	
//	Método responsável por inicial a partida de Xadrez
//	Colocando as peças no tabuleiro
	private void initialSetup(){
		
	
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
    	placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));
		
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	

	}
}
