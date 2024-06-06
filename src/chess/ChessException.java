package chess;

import boardgame.BoardException;

//Classe para as exceções provenientes do pacote Chess
public class ChessException extends BoardException{
	private static final long serialVersionUID = 1L;
	
	public ChessException (String msg) {
		super(msg);
	}

}
