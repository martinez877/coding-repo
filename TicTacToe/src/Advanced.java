import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Advanced {
	static int boardPosition;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = "";
		String winner = "z";
		while (true) {
			System.err.println("Please select Game_Player ('x' or 'o', or 'q' to exit): ");
			s = sc.next();
			if (s.equalsIgnoreCase("q")) {
				break;
			}
			if (!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
				while (!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
					System.err.println("Please select Game_Player ('x' or 'o', or 'q' to exit): ");
					s = sc.next();
				}
			}
			//PLAY GAME
			winner = GamePlay(s);
			if (winner.equals("draw")) {
				System.err.println("That game ended in a draw!");
			} else {
				System.err.println("Congrat~! Player " + winner + " won that game!");
			}
		}
	}

	public static String GamePlay(String user) {
		Scanner sc = new Scanner(System.in);
		Game_Player starter = AdvancedState.getGame_Player(user);
		AdvancedState initial = new AdvancedState();
		initial.setCurrentGame_Player(starter);
		String nextSpot;
		boardPosition = 0;
		if (starter == Game_Player.X) {
			AdvancedTrans.setComputerGame_Player(Game_Player.O);
			System.err.println("Please enter a two digit integer to place your Game_Player:"
					+ "\n(For example: '91' indicates the top left postition in the right bottom board.)");
			int[] move = null;
			while (true) {
				nextSpot = sc.next();
				try {
					move = parseNumbers(nextSpot);
					break;
				} catch (Exception e) {
					System.err.println("Please enter a VALID location:");
				}
			}
			System.err.println("You put in board: " + move[0] + " and in position: " + move[1]);
			AdvancedAction a = new AdvancedAction(move[0], move[1], initial.getCurrentGame_Player());
			while (!initial.valid(a)) {
				System.err.println("Please enter a VALID location:");
				while (true) {
					nextSpot = sc.next();
					try {
						move = parseNumbers(nextSpot);
						break;
					} catch (Exception e) {
						System.err.println("Please enter a VALID location:");
					}
				}
				a = new AdvancedAction(move[0], move[1], initial.getCurrentGame_Player());
			}
			boardPosition = move[1];
			initial.applyAction(a);
			System.err.println(initial);
		} else {
			initial.setCurrentGame_Player(Game_Player.X);
			AdvancedTrans.setComputerGame_Player(Game_Player.X);
		}
		while (!initial.Terminal_check()) {
			initial = initial.makeMoveAlphaBeta(boardPosition, 7);
			if (initial.Terminal_check()) {
				System.err.println(initial);
				break;
			}
			System.err.println(initial);
			System.err.println("Please enter a location:");
			int[] move = null;
			while (true) {
				nextSpot = sc.next();
				try {
					move = parseNumbers(nextSpot);
					break;
				} catch (Exception e) {
					System.err.println("Please enter a VALID location:");
				}
			}
			AdvancedAction a = new AdvancedAction(move[0], move[1], initial.getCurrentGame_Player());
			while (!initial.valid(a) || move[0] != boardPosition) {
				System.err.println("Please enter a VALID location:");
				//nextSpot = sc.next();
				while (true) {
					nextSpot = sc.next();
					try {
						move = parseNumbers(nextSpot);
						break;
					} catch (Exception e) {
						System.err.println("Please enter a VALID location:");
					}
				}
				a = new AdvancedAction(move[0], move[1], initial.getCurrentGame_Player());
			}
			initial.applyAction(a);
			if (initial.Terminal_check()) {
				System.err.println(initial);
				break;
			}
			boardPosition = move[1];
			System.err.println(initial);
		}
		return initial.Game_PlayerOfWon();
	}

	public static void setBoardLocation(int n) {
		boardPosition = n;
	}

	public static int[] parseNumbers(String s) {
		char[] c = s.toCharArray();
		int[] temp = new int[c.length];
		for (int i = 0; i < c.length; i++) {
			try {
				temp[i] = Integer.parseInt(String.valueOf(c[i]));
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		return temp;
	}
}

class BasicBoard {
	Game_Player[][] board;

	public BasicBoard(int n) {
		board = new Game_Player[n][n];
	}

	public boolean Terminal_check() {
		Game_Player player = Game_Player.X;
		for (int z = 0; z < 2; z++) {
			//Horizontal Win Conditions
			for (int i = 0; i < 3; i++) {
				if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
					return true;
				}
			}
			//Vertical Win Conditions
			for (int j = 0; j < 3; j++) {
				if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
					return true;
				}
			}
			//Diagonal Win Conditions
			if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
				return true;
			}
			if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
				return true;
			}
			player = Game_Player.O;
		}
		boolean fullBoard = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == null) {
					fullBoard = false;
				}
			}
		}
		return fullBoard;
	}

	public Game_Player[][] getBoard() {
		return board;
	}

	public void setBoard(Game_Player[][] board) {
		this.board = board;
	}

	public String rowToString(int n) {
		String temp = "";
		for (int j = 0; j < 3; j++) {
			if (board[n][j] != null) {
				temp += "|" + board[n][j];
			} else {
				temp += "| ";
			}
		}
		temp += "|";
		return temp;
	}

	public String toString() {
		String toPrint = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == null) {
					toPrint += "| ";
				} else {
					toPrint += "|" + board[i][j];
				}
			}
			toPrint += "|" + "\n-------\n";
		}
		return toPrint;
	}
}

class AdvancedAction {
	int board, loc;
	Game_Player currentGame_Player;

	public AdvancedAction(int board, int loc, Game_Player currentGame_Player) {
		this.board = board;
		this.loc = loc;
		this.currentGame_Player = currentGame_Player;
	}

	public int getBoard() {
		return board;
	}

	public void setBoard(int board) {
		this.board = board;
	}

	public int getLoc() {
		return loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}

	public Game_Player getCurrentGame_Player() {
		return currentGame_Player;
	}

	public void setCurrentGame_Player(Game_Player currentGame_Player) {
		this.currentGame_Player = currentGame_Player;
	}

	public String toString() {
		return currentGame_Player + " at location " + loc + " in board " + board;
	}
}

class AdvancedTrans {
	static Game_Player computerGame_Player;
	int value;
	AdvancedState state;
	ArrayList<AdvancedTrans> children;

	//constructor
	public AdvancedTrans() {
		children = new ArrayList<AdvancedTrans>();
	}

	public static AdvancedTrans result(AdvancedState s, AdvancedAction a) {
		AdvancedState state = new AdvancedState(s.copyBoard(), s.getCurrentGame_Player());
		state.applyAction(a);
		AdvancedTrans n = new AdvancedTrans();
		n.setState(state);
		return n;
	}

	public static void setComputerGame_Player(Game_Player p) {
		computerGame_Player = p;
	}

	//Terminal Test
	public boolean wonCheck() {
		return state.Terminal_check();
	}

	//Alpha Beta Search Minimax
	public AdvancedAction alphaBetaSearch(int board, int depth) {
		value = maxValue(Integer.MIN_VALUE, Integer.MAX_VALUE, board, depth);
		AdvancedState s = this.getState();
		for (AdvancedTrans n : children) {
			if (value == n.getValue()) {
				s = n.getState();
				break;
			}
		}
		return new AdvancedAction(board, state.findChange(s, board), state.getCurrentGame_Player());
	}

	public int maxValue(int alpha, int beta, int board, int depth) {
		if (wonCheck()) {
			int x = value(computerGame_Player);
			value = x;
			return x;
		}
		if (depth <= 0) {
			int x = this.heuristic(computerGame_Player, board);
			value = x;
			return x;
		}
		value = Integer.MIN_VALUE;
		ArrayList<AdvancedAction> actions = state.getApplicableActions(board);
		for (AdvancedAction a : actions) {
			AdvancedTrans n = result(this.state, a);
			children.add(n);
			value = Math.max(value, n.minValue(alpha, beta, a.getLoc(), depth - 1));
			if (beta <= alpha) {
				break;
			}
			alpha = Math.max(alpha, value);
		}
		return value;
	}

	public int minValue(int alpha, int beta, int board, int depth) {
		if (wonCheck()) {
			int x = value(computerGame_Player);
			value = x;
			return x;
		}
		if (depth <= 0) {
			int x = this.heuristic(computerGame_Player, board);
			value = x;
			return x;
		}
		value = Integer.MAX_VALUE;
		ArrayList<AdvancedAction> actions = state.getApplicableActions(board);
		for (AdvancedAction a : actions) {
			AdvancedTrans n = result(this.state, a);
			children.add(n);
			value = Math.min(value, n.maxValue(alpha, beta, a.getLoc(), depth - 1));
			if (beta <= alpha) {
				break;
			}
			beta = Math.min(beta, value);
		}
		return value;
	}

	//HEURISTIC FUNCTION
	public int heuristic(Game_Player p, int board) {
		Game_Player opposite = AdvancedState.oppositeGame_Player(p);
		AdvancedState state = this.getState();
		int[] indices = AdvancedState.getIndices(board);
		BasicBoard b = state.getBoard()[indices[0]][indices[1]];
		int result = 8;
		//Horizontal Win Conditions
		for (int i = 0; i < 3; i++) {
			if (b.getBoard()[i][0] == opposite || b.getBoard()[i][1] == opposite || b.getBoard()[i][2] == opposite) {
				result--;
				//System.err.println("horizontal");
			}
		}
		//Vertical Win Conditions
		for (int j = 0; j < 3; j++) {
			if (b.getBoard()[0][j] == opposite || b.getBoard()[1][j] == opposite || b.getBoard()[2][j] == opposite) {
				result--;
				//System.err.println("vertical");
			}
		}
		//Diagonal Win Conditions
		if (b.getBoard()[0][0] == opposite || b.getBoard()[1][1] == opposite || b.getBoard()[2][2] == opposite) {
			result--;
			//System.err.println("diagonal 1");
		}
		if (b.getBoard()[0][2] == opposite || b.getBoard()[1][1] == opposite || b.getBoard()[2][0] == opposite) {
			result--;
			//System.err.println("diagonal 2");
		}
		return result;
	}

	//Value FUNCTION
	public int value(Game_Player p) {
		int result = 100;
		String winner = state.Game_PlayerOfWon();
		if (winner.equalsIgnoreCase(p.toString())) {
			result = 9;
		}
		if (winner.equalsIgnoreCase(AdvancedState.oppositeGame_Player(p).toString())) {
			result = -9;
		}
		if (winner.equals("draw")) {
			result = 0;
		}
		return result;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public AdvancedState getState() {
		return state;
	}

	public void setState(AdvancedState state) {
		this.state = state;
	}

	public ArrayList<AdvancedTrans> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<AdvancedTrans> children) {
		this.children = children;
	}
}

class AdvancedState {
	BasicBoard[][] board;
	Game_Player currentGame_Player;

	//CONSTRUCTOR
	public AdvancedState() {
		BasicBoard[][] newBoard = new BasicBoard[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				BasicBoard s = new BasicBoard(3);
				newBoard[i][j] = s;
			}
		}
		board = newBoard;
	}

	public static Game_Player getGame_Player(String s) {
		if (s.equalsIgnoreCase("x")) {
			return Game_Player.X;
		} else {
			return Game_Player.O;
		}
	}

	public static String playerToString(Game_Player p) {
		String result = "";
		if (p == Game_Player.O) {
			result = "o";
		}
		if (p == Game_Player.X) {
			result = "x";
		}
		return result;
	}

	public static Game_Player oppositeGame_Player(Game_Player p) {
		if (p == Game_Player.X) {
			return Game_Player.O;
		} else {
			return Game_Player.X;
		}
	}

	public AdvancedState(BasicBoard[][] board, Game_Player currentGame_Player) {
		this.board = board;
		this.currentGame_Player = currentGame_Player;
	}

	public static int getNumber(int i, int j) {
		int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		return a[i][j];
	}

	public static int[] getIndices(int x) {
		int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		int c = -1;
		int d = -1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (a[i][j] == x) {
					c = i;
					d = j;
					break;
				}
			}
		}
		int[] temp = {c, d};
		return temp;
	}

	//USED TO INITIATE ALPHA BETA MINIMAX
	public AdvancedState makeMoveAlphaBeta(int board, int depth) {
		if (board == 0) {
			Random random = new Random();
			int newBoard = random.nextInt(9);
			newBoard++;
			int loc = random.nextInt(9);
			loc++;
			AdvancedAction a = new AdvancedAction(newBoard, loc, getCurrentGame_Player());
			System.out.println(String.valueOf(newBoard) + String.valueOf(loc));
			Advanced.setBoardLocation(a.getLoc());
			return AdvancedTrans.result(this, a).getState();
		}
		AdvancedTrans n = new AdvancedTrans();
		n.setState(this);
		AdvancedAction a = n.alphaBetaSearch(board, depth);
		Advanced.setBoardLocation(a.getLoc());
		AdvancedState s = n.getState();
		System.out.println(String.valueOf(a.getBoard()) + String.valueOf(a.getLoc()));
		s.applyAction(a);
		return s;
	}

	//CHECK IF A MOVE IS VALID
	public boolean valid(AdvancedAction a) {
		boolean check = false;
		int[] indices = getIndices(a.getBoard());
		BasicBoard s = board[indices[0]][indices[1]];
		int[] dimens = getIndices(a.getLoc());
		if (s.getBoard()[dimens[0]][dimens[1]] != null) {
			check = false;
		} else {
			check = true;
		}
		return check;
	}

	//APPLY A MOVE TO THIS STATE
	public void applyAction(AdvancedAction a) {
		int[] indices = getIndices(a.getBoard());
		BasicBoard s = board[indices[0]][indices[1]];
		int[] dimens = getIndices(a.getLoc());
		s.getBoard()[dimens[0]][dimens[1]] = a.getCurrentGame_Player();
		if (!Terminal_check()) {
			if (currentGame_Player == Game_Player.X) {
				currentGame_Player = Game_Player.O;
			} else {
				currentGame_Player = Game_Player.X;
			}
		}
	}

	//GET LIST OF POSSIBLE ACTIONS FOR A GIVEN BOARD OF SIMPLETTT INSIDE Advanced BOARD
	public ArrayList<AdvancedAction> getApplicableActions(int boardNum) {
		ArrayList<AdvancedAction> actions = new ArrayList<AdvancedAction>();
		if (boardNum == 0) {
			//ignore this... sorry!
			//taken care of elsewhere
		} else {
			int[] indices = getIndices(boardNum);
			BasicBoard currentBoard = board[indices[0]][indices[1]];
			Game_Player[][] boardP = currentBoard.getBoard();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (boardP[i][j] == null) {
						actions.add(new AdvancedAction(boardNum, getNumber(i, j), getCurrentGame_Player()));
					}
				}
			}
		}
		return actions;
	}

	public String Game_PlayerOfWon() {
		Game_Player player = Game_Player.X;
		BasicBoard b;
		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 3; l++) {
				b = this.getBoard()[k][l];
				for (int z = 0; z < 2; z++) {
					//Horizontal Win Conditions
					for (int i = 0; i < 3; i++) {
						if (b.getBoard()[i][0] == player && b.getBoard()[i][1] == player && b.getBoard()[i][2] == player) {
							return AdvancedState.playerToString(player);
						}
					}
					//Vertical Win Conditions
					for (int j = 0; j < 3; j++) {
						if (b.getBoard()[0][j] == player && b.getBoard()[1][j] == player && b.getBoard()[2][j] == player) {
							return AdvancedState.playerToString(player);
						}
					}
					//Diagonal Win Conditions
					if (b.getBoard()[0][0] == player && b.getBoard()[1][1] == player && b.getBoard()[2][2] == player) {
						return AdvancedState.playerToString(player);
					}
					if (b.getBoard()[0][2] == player && b.getBoard()[1][1] == player && b.getBoard()[2][0] == player) {
						return AdvancedState.playerToString(player);
					}
					if (player.equals(Game_Player.X))
						player = Game_Player.O;
					else
						player = Game_Player.X;
				}
				boolean full = true;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (b.getBoard()[i][j] == null) {
							full = false;
						}
					}
				}
				if (full) {
					return "draw";
				}
			}
		}
		return "fail";
	}

	public Game_Player[][] getGame_PlayerBoard(int boardNum) {
		int[] indices = getIndices(boardNum);
		BasicBoard currentBoard = board[indices[0]][indices[1]];
		return currentBoard.getBoard();
	}

	//TERMINAL TEST!
	public boolean Terminal_check() {
		boolean gameOver = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gameOver = gameOver || board[i][j].Terminal_check();
			}
		}
		return gameOver;
	}

	public BasicBoard[][] copyBoard() {
		BasicBoard[][] newBoard = new BasicBoard[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				BasicBoard s = new BasicBoard(3);
				BasicBoard orig = getBoard()[i][j];
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						s.getBoard()[k][l] = orig.getBoard()[k][l];
					}
				}
				newBoard[i][j] = s;
			}
		}
		return newBoard;
	}

	public int findChange(AdvancedState s, int board) {
		int[] indices = getIndices(board);
		System.err.println("Indices: " + indices[0] + ", " + indices[1]);
		BasicBoard simpleBoard = this.board[indices[0]][indices[1]];
		BasicBoard orig = s.getBoard()[indices[0]][indices[1]];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (simpleBoard.getBoard()[i][j] != orig.getBoard()[i][j]) {
					return getNumber(i, j);
				}
			}
		}
		return -1;
	}

	public BasicBoard[][] getBoard() {
		return board;
	}

	public void setBoard(BasicBoard[][] board) {
		this.board = board;
	}

	public Game_Player getCurrentGame_Player() {
		return currentGame_Player;
	}

	public void setCurrentGame_Player(Game_Player currentGame_Player) {
		this.currentGame_Player = currentGame_Player;
	}

	public String toString() {
		String toPrint = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					toPrint += board[i][k].rowToString(j) + "\t\t";
				}
				toPrint += "\n";
			}
			toPrint += "\n";
		}
		return toPrint;
	}
}
