package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	
	public Board(int rows, int column) {
		this.rows = rows;
		this.columns = column;
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board:"
					+ " there must be at least 1 row and 1 column"); 
		}
		pieces = new Piece [rows][column];
//		Adicionando exceções para criação do tabuleiro
		
	
	}


	public int getRows() {
		return rows;
	}
	
	public int getColumn() {
		return columns;
	}

	public Piece piece(int row, int column) {
		
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];	
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
//	placePiece -> Coloca peça em uma posição
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is alredy a piece on position " + position);
		}		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
//	removePiece -> Remover uma peça da matrix
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if(piece(position) == null) {
			return null;
		}
		
		Piece aux = piece(position);
		aux.position = null;
		
//		Indica que na matrix de peças, nessa posição, agora tem um null
		pieces[position.getRow()][position.getColumn()] = null;
//		retorna a peça que foi retirada 
		return aux;
		
	}
	
	
	
//	Em determinado momento será mais facil consultar a posição através das linhas e colunas 
	private boolean positionExists(int row, int column) {
		//Verificando se a posição existe
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
//	positionExists -> pega uma posição e retorn um boolean, dizendo se exite ou não
	public boolean positionExists(Position position) {
			
		return positionExists(position.getRow(), position.getColumn());
	}
//	thereIsAPiece -> Verifica se na posição existe uma peça
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
}
