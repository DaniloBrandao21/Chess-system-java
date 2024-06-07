package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override 
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumn()];
		
		Position p = new Position(0, 0);
		
//		Verificando espaços acima 
//		*position acessando a posição da própria peça, declarada na Classe Piece*
		
		p.setValues(position.getRow()-1, position.getColumn());
		
//		*Verificando as casas acima subindo linha a linha*
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		
//		*Verificando se existe mais uma casas acima e se há uma peça adversária na posição*
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			
		}
		
//		Verificando espaços à esquerda
		
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
//		Verificando espaços à direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
//		Verificando espaços abaixo
		p.setValues(position.getRow()+1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			
		}
		
		p.setValues(position.getRow()-1, position.getColumn() - 1);
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			
		}

		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1 , position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() +1 , position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereAOponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			
		}
		
		return mat;
	}
	
}
