package tic_tac_toe;

public class Board {
	protected static void initializeBoard(char board[][]) {
		for (int i = 0; i < Util.SIZE; i++) {
			for (int j = 0; j < Util.SIZE; j++)
				board[i][j] = ' ';
		}
	}

	protected static void displayEmptyBoard(char board[][]) {
		Util.displayMsg("\t\t\t  1 | 2  | 3  ");
		Util.displayMsg("\t\t\t--------------");
		Util.displayMsg("\t\t\t  4 | 5  | 6  ");
		Util.displayMsg("\t\t\t--------------");
		Util.displayMsg("\t\t\t  7 | 8  | 9  \n");
	}

	protected static void displayBoard(char board[][]) {
		for (int i = 0; i < Util.SIZE; i++) {
			Util.displayMsg("\t\t\t" + board[i][0] + " | " + board[i][1] + " | " + board[i][2] + "");
			if (i < 2)
				Util.displayMsg("\t\t\t-----------");
		}
	}

	protected static boolean isBoardFull(char board[][]) {
		int i = 0, j = 0;
		for (; i < Util.SIZE; i++) {
			for (j=0; j < Util.SIZE; j++) {
				if (board[i][j] == ' ')
					return false;
			}
		}
		return true;
	}

	protected static void dispalyInstructions(char board[][]) {
		Board.displayEmptyBoard(board);
		Util.displayMsg("Player 1 uses = X  and Player 2 uses = O ");
		Util.displayMsg("\n Player1 starts");
		Util.displayMsg("\nChoose a number as shown above to make a move");
	}


}
