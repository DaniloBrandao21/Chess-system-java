package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

//	Cores dos textos 
		public static final String ANSI_RESET = "\u001B[0m";
		public static final String ANSI_BLACK = "\u001B[30m";
		public static final String ANSI_RED = "\u001B[31m";
		public static final String ANSI_GREEN = "\u001B[32m";
		public static final String ANSI_YELLOW = "\u001B[33m";
		public static final String ANSI_BLUE = "\u001B[34m";
		public static final String ANSI_PURPLE = "\u001B[35m";
		public static final String ANSI_CYAN = "\u001B[36m";
		public static final String ANSI_WHITE = "\u001B[37m";

//		Cores do background
		public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
		public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
		public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
		public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
		public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
		public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
		public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
		public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
		
		
		// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() { 
		 System.out.print("\033[H\033[2J"); 
		 System.out.flush(); 
	} 
		
//		readChessPosition -> ler uma posição do xadrez(vinda direto do programa principal)
	public static ChessPosition readChessPosition(Scanner sc) {
		
		try {
		String s = sc.nextLine();
//		recebendo a coluna
		char column = s.charAt(0);
//		recebendo a linha, recortando o string apartir da posição 1 e convertendo o resultado pra int
		int row = Integer.parseInt(s.substring(1));
		
		return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valie values"
					+ " are from a1 to h8.");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		System.out.println("Waiting player: " + chessMatch.currentPlayer());
		if(chessMatch.getCheck()){
			System.out.println("CHECK!");
		}
	}
		
//		Método para imprimir o tabuleiro
	public static void printBoard(ChessPiece[][] pieces){

		for(int i = 0; i < pieces.length; i++) {
				System.out.print(8 - i + " ");
			for(int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}
	
//	printBoard (sobrecarregado) -> imprime o tabuleiro com as possiveis jogadas da peça selecionada
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves ){

		for(int i = 0; i < pieces.length; i++) {
				System.out.print(8 - i + " ");
			for(int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}

//	Método para imprimir as peças
	private static void printPiece(ChessPiece piece, boolean backgroud) {
		if(backgroud) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
    	if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	
//	printCapturedPieces-> imprimir na tela as peças capturadas
	private static void printCapturedPieces(List<ChessPiece> captured) {
//		Filtrando a lista de pessas pela cor 		
		List<ChessPiece> white = 
				captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = 
				captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		
		System.out.println("Captured pieces");
		System.out.print("Whites: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print(ANSI_YELLOW);
		System.out.print("Blacks: ");
		System.out.print(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
	
}
