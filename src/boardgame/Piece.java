package boardgame;

public abstract class Piece {

	protected Position position; 
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}
	
//	possibleMoves -> movimentos possiveis
	public abstract boolean[][] possibleMoves();
	
//	possibleMove -> verificar se a peça pode se mover para posição
//	Usa o método de uma subclasse (hookmethods)
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
//	isThereAnyPossibleMove -> verifica se há algum movimento possivel
	public boolean isThereAnyPossibleMove() {
		boolean [][] mat = possibleMoves();
//		*percorrendo a matrix para saber se há algum movimento possível
//		se retornar true, existe movimentos / se returnar false, não há*
		for (int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat.length; j++ ) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
