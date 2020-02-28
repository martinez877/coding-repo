import java.util.ArrayList;
import java.util.Scanner;

enum Game_Player {
	X, O
}

public class Basic {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = "";
		String winner = "";

		while (true) {
			System.err.println("Please select your role ('x' or 'o', or 'q' to exit): ");
			s = sc.next();
			if (s.equalsIgnoreCase("q")) {
				break;
			}
			if (!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
				while (!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
					System.err.println("Please select your role ('x' or 'o', or 'q' to exit): ");
					s = sc.next();
				}
			}

			winner = gamePlay(s);
			if (winner.equals("draw")) {
				System.err.println("That game ended with a draw!");
			} else {
				System.err.println("Congrats!!!!!!! Player " + winner + " won that game!");
			}
		}
	}

	public static String gamePlay(String user) {
		Scanner sc = new Scanner(System.in);
		Game_Player start_person = State.getGame_Player(user);
		State initial = new State(new Game_Player[3][3], start_person);
		int nextLoca;
		if (start_person == Game_Player.X) {
			Trans.setComputerGame_Player(Game_Player.O);
			System.err.println("Please enter a location: ");
			nextLoca = sc.nextInt();
			Action a = new Action(nextLoca, initial.getCurrentGame_Player());
			while (!initial.valid(a)) {
				System.err.println("Please enter a VALID location: ");
				nextLoca = sc.nextInt();
				a = new Action(nextLoca, initial.getCurrentGame_Player());
			}
			initial.applyAction(a);
			System.err.println(initial);
		} else {
			initial.setCurrentGame_Player(Game_Player.X);
			Trans.setComputerGame_Player(Game_Player.X);
		}
		while (!initial.TerminalCheck()) {
			initial = initial.Move_Initiate();
			if (initial.TerminalCheck()) {
				System.err.println(initial);
				break;
			}
			System.err.println(initial);
			System.err.println("Please enter a location: ");
			nextLoca = sc.nextInt();
			Action a = new Action(nextLoca, initial.getCurrentGame_Player());
			while (!initial.valid(a)) {
				System.err.println("Please enter a VALID location: ");
				nextLoca = sc.nextInt();
				a = new Action(nextLoca, initial.getCurrentGame_Player());
			}
			initial.applyAction(a);
			System.err.println(initial);
		}
		return initial.CheckWhoWon();
	}
}

class Action {
	Game_Player currentGame_Player;
	int location;

	//constructor
	public Action(int loc, Game_Player player) {
		currentGame_Player = player;
		location = loc;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public Game_Player getCurrentGame_Player() {
		return currentGame_Player;
	}

	public void setCurrentGame_Player(Game_Player currentGame_Player) {
		this.currentGame_Player = currentGame_Player;
	}

	public String toString() {
		return currentGame_Player + " at location " + location;
	}
}

class Trans {
	static Game_Player computerGame_Player;
	int utility;
	State state;
	ArrayList<Trans> children;

	public Trans() {
		children = new ArrayList<Trans>();
	}

	public static void setComputerGame_Player(Game_Player p) {
		computerGame_Player = p;
	}

	//result function
	public static Trans result(State s, Action a) {
		State state = new State(s.copyBoard(), s.getCurrentGame_Player());
		state.applyAction(a);
		Trans n = new Trans();
		n.setState(state);
		return n;
	}

	public int getUtility() {
		return utility;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public ArrayList<Trans> getChildren() {
		return children;
	}

	public boolean terminalTest() {
		return state.TerminalCheck();
	}

	//UTILITY FUNCTION
	public int utility(Game_Player p) {
		int result = 10;
		String winner = state.CheckWhoWon();
		if (winner.equalsIgnoreCase(p.toString())) {
			result = 1;
		}
		if (winner.equalsIgnoreCase(State.oppositeGame_Player(p).toString())) {
			result = -1;
		}
		if (winner.equals("draw")) {
			result = 0;
		}
		return result;
	}

	public void miniMax() {
		if (terminalTest()) {
			utility = this.utility(computerGame_Player);
		} else {
			if (state.getCurrentGame_Player() == computerGame_Player) {
				ArrayList<Action> actions = state.get_Potential_Actions();
				int max = Integer.MIN_VALUE;
				ArrayList<Trans> successors = new ArrayList<Trans>();
				for (Action a : actions) {
					successors.add(result(state, a));
				}
				children = successors;
				for (Trans n : children) {
					n.miniMax();
					if (max < n.getUtility()) {
						max = n.getUtility();
					}
				}
				utility = max;
			}
			if (state.getCurrentGame_Player() != computerGame_Player) {
				ArrayList<Action> actions = state.get_Potential_Actions();
				int min = Integer.MAX_VALUE;
				ArrayList<Trans> successors = new ArrayList<Trans>();
				for (Action a : actions) {
					successors.add(result(state, a));
				}
				children = successors;
				for (Trans n : children) {
					n.miniMax();
					if (min > n.getUtility()) {
						min = n.getUtility();
					}
				}
				utility = min;
			}
		}
	}

	public String toString() {
		return state.toString();
	}
}

class State {
	Game_Player[][] board;
	Game_Player currentGame_Player;

	public State(Game_Player[][] nBoard, Game_Player nGame_Player) {
		board = nBoard;
		currentGame_Player = nGame_Player;
	}

	public static Game_Player getGame_Player(String s) {
		if (s.equalsIgnoreCase("x")) {
			return Game_Player.X;
		} else {
			return Game_Player.O;
		}
	}

	public static int getNumber(int i, int j) {
		int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		return a[i][j];
	}

	public static int[] getLocation(int x) {
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

	public Game_Player[][] getBoard() {
		return board;
	}

	public ArrayList<Action> get_Potential_Actions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == null) {
					actions.add(new Action(getNumber(i, j), getCurrentGame_Player()));
				}
			}
		}
		return actions;
	}

	public State Move_Initiate() {
		Trans n = new Trans();
		n.setState(this);
		n.miniMax();
		int utilityAtRoot = n.getUtility();
		System.err.println("Computer put at : " + utilityAtRoot);
		ArrayList<Trans> children = n.getChildren();
		State s = n.getState();
		for (Trans Trans : children) {
			if (Trans.getUtility() == utilityAtRoot) {
				s = Trans.getState();
			}
		}
		System.out.println(findChange(s));
		return s;
	}


	public int findChange(State s) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != s.getBoard()[i][j]) {
					return getNumber(i, j);
				}
			}
		}
		return -1;
	}

	public Game_Player[][] copyBoard() {
		Game_Player[][] newBoard = new Game_Player[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}

	public void applyAction(Action a) {
		int x = a.getLocation();
		int[] array = getLocation(x);
		board[array[0]][array[1]] = a.getCurrentGame_Player();
		if (!TerminalCheck()) {
			if (currentGame_Player == Game_Player.X) {
				currentGame_Player = Game_Player.O;
			} else {
				currentGame_Player = Game_Player.X;
			}
		}
	}

	public boolean valid(Action a) {
		boolean check = false;
		int[] dimens = getLocation(a.getLocation());
		if (board[dimens[0]][dimens[1]] != null) {
			check = false;
		} else {
			check = true;
		}
		return check;
	}

	public boolean TerminalCheck() {
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

	public String CheckWhoWon() {
		Game_Player player = Game_Player.X;
		for (int z = 0; z < 2; z++) {
			//Horizontal Win Conditions
			for (int i = 0; i < 3; i++) {
				if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
					return playerToString(player);
				}
			}
			//Vertical Win Conditions
			for (int j = 0; j < 3; j++) {
				if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
					return playerToString(player);
				}
			}
			//Diagonal Win Conditions
			if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
				return playerToString(player);
			}
			if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
				return playerToString(player);
			}
			player = Game_Player.O;
		}
		boolean full = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == null) {
					full = false;
				}
			}
		}
		if (full) {
			return "draw";
		}
		return "fail";
	}

	public Game_Player getCurrentGame_Player() {
		return currentGame_Player;
	}

	public void setCurrentGame_Player(Game_Player currentGame_Player) {
		this.currentGame_Player = currentGame_Player;
	}

	public String toString() {
		StringBuilder toPrint = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == null) {
					toPrint.append("| ");
				} else {
					toPrint.append("|").append(board[i][j]);
				}
			}
			toPrint.append("|" + "\n-------\n");
		}
		return toPrint.toString();
	}
}
