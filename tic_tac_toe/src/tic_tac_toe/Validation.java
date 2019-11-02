package tic_tac_toe;

public class Validation {

	protected static boolean isValidThenEnter(char board[][], int pos, char player) {
		if (pos < 1 || pos > 9) {
			Util.displayMsg("Enter a valid move in between(1-9)");
			return false;
		}
		int count = 0;
		int i = 0, j = 0;
		for (i = 0; i < Util.SIZE; i++) {
			for (j = 0; j < Util.SIZE; j++) {
				count++;
				if (count == pos)
					break;
			}
			if (count == pos)
				break;
		}
		if (board[i][j] != ' ') {
			Util.displayMsg("Board position occupied! ");
			return false;
		}
		Util.displayMsg("player: " + player + " pos: " + pos);
		board[i][j] = player;
		return true;
	}

	protected static boolean rowCrossed(char board[][]) {
		for (int i = 0; i < Util.SIZE; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ')
				return (true);
		}
		return (false);
	}

	protected static boolean columnCrossed(char board[][]) {
		for (int i = 0; i < Util.SIZE; i++) {
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ')
				return (true);
		}
		return (false);
	}

	protected static boolean diagonalCrossed(char board[][]) {
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')
			return (true);

		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ')
			return (true);

		return (false);
	}

	protected static boolean gameOver(char player, char board[][]) {
		if (rowCrossed(board) || columnCrossed(board) || diagonalCrossed(board)) {
			Util.displayMsg("-------------------------------");
			//during this check player has been changes in the calling function so I reversed it here
			Util.displayMsg(player == Main.X ? "Player 2 with move O" : "Player 1 with move X" + " HAS WON");
			Util.displayMsg("-------------------------------");
			Util.displayMsg("-----------RESULT--------------");
			Util.displayMsg("-------------------------------");
			Board.displayBoard(board);
			return true;
		}
		return (false);
	}
}
