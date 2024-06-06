package chess;

//Classe para as exceções provenientes do pacote Chess
public class ChessException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ChessException (String msg) {
		super(msg);
	}

}
