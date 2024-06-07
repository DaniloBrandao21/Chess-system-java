package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating chessPosition. Valid"
					+ " values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
//	Método toPosition -> transforma a ChessPosition para uma Position
//	ex: Posição A1 -> 0,0
//	coluna/linha -> linha/coluna
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
//	Método fromPosition -> Transforma uma position para uma chessPosition
//	ex: posição (7,8) -> H2
//	linha/coluna -> coluna/linha
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}

}
