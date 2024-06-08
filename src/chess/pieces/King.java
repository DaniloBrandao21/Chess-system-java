package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch; 
	}
	
	@Override
	public String toString() {
		return "K";
	}

	
//	canMove -> verifica se o rei pode se mover para uma determinada posiçao
	private boolean canMove(Position position){
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		
//		verificando se a posição não é null e se não é uma peça adversária
		return p == null || p.getColor() != getColor();
		
	}
	
//	testRookCastling -> Verificar se é possivel realizar Roque
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumn()];
		
		Position p = new Position(0, 0);
		
		//Acima
		p.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//À esquerda
		p.setValues(position.getRow(), position.getColumn() - 1 );
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//À direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Diagonal superior esquerda
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
						
		//Diagonal superior direita
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}		
		
		//Diagonal inferior esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Diagonal inferior direita
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
//		#Jogada especial Roque
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
//			#Jogada especial Roque torre do lado do rei 
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
				if(testRookCastling(posT1)){
					Position p1 = new Position(position.getRow(), position.getColumn() + 1);
					Position p2 = new Position(position.getRow(), position.getColumn() + 2);
					if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
						mat[position.getRow()][position.getColumn()+ 2] = true; 
				}
			}
		}
				if(getMoveCount() == 0 && !chessMatch.getCheck()) {
//					#Jogada especial Roque torre do lado da rainha
					Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
						if(testRookCastling(posT2)){
							Position p1 = new Position(position.getRow(), position.getColumn() - 1);
							Position p2 = new Position(position.getRow(), position.getColumn() - 2);
							Position p3 = new Position(position.getRow(), position.getColumn() - 3);
							if(getBoard().piece(p1) == null && getBoard().piece(p2) == null &&
									getBoard().piece(p3) == null) {
								mat[position.getRow()][position.getColumn() - 2] = true; 
						}
				}
		}
		return mat;
	}
}
