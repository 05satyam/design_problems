
/**
 * THIS IS A TIC-TAC-TOE GAME FOR TWO PLAYERS 
 * KEY POINTS TO REMEBER: BUILD BY NOT KEEPING IN MIND MULTITHREADING :p
 * */
package tic_tac_toe;

import java.util.Scanner;

public class Main {

	public static final char X = 'X';
	public static final char O = 'O';
	public static char P1 = ' ';
	public static char P2 = ' ';
	private boolean isTie = false;
	static char board[][] = new char[3][3];

	private void assignPlayersValue() {
		P1 = X;
		P2 = O;
		Util.displayMsg("Player1 = X");
		Util.displayMsg("Player2 = O");
		Util.displayMsg("----------------------------------\n");
	}

	public void startGame() {
		char move = X;
		Scanner sc = new Scanner(System.in);
		int pos = 0;
		do {
			switch (move) {
			case 'X':
				if (Board.isBoardFull(board)) {
					isTie = true;
					break;
				}
				Util.displayMsg(" PLAYER 1 CHANCE ");
				Board.displayBoard(board);
				Util.displayMsg("Enter a number of an empty position: ");
				pos = sc.nextInt();
				move = Validation.isValidThenEnter(board, pos, X) ? O : X;
				break;
			case 'O':
				if (Board.isBoardFull(board)) {
					isTie = true;
					break;
				}
				Util.displayMsg(" PLAYER 2 CHANCE");
				Board.displayBoard(board);
				Util.displayMsg("Enter a number of an empty position: ");
				pos = sc.nextInt();
				move = Validation.isValidThenEnter(board, pos, O) ? X : O;
				break;
			default:
				Util.displayMsg("default");
				break;
			}
			if (isTie) {
				Util.displayMsg("It was a Tie... Play Again");
				break;
			}

		} while ((Validation.gameOver(move, board) == false));
	}

	public static void main(String[] args) {
		Main m = new Main();
		try {
			Board.initializeBoard(board);
			Board.dispalyInstructions(board);
			m.assignPlayersValue();
			m.startGame();

		} catch (Exception e) {
			System.out.println("exception occur : " + e.getMessage());
		}

	}
}
