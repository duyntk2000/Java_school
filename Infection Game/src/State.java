
import java.util.Arrays;
import java.util.HashSet;

public class State {
	public int[][] board;
	public int player1;
	public int player2;
	public int currentPlayer;
	public HashSet<State> history;			// Un set pour stocker les états qui ont déjà joués , pour faire comparer plus tard dans isOver()
	public boolean playerPass = false;		//Un booléen indiquant si ce état est créé après un coup ou un passe
	
	public State(int [][] board, int player1, int player2, int currentPlayer, HashSet<State> history) {
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = currentPlayer;
		this.history = history;
	}
	
	public boolean isOver() {
		if (getScore(this.player1) == 0 || getScore(this.player2) == 0) {	//Un des joueurs n’a plus de pion de sa couleur sur le plateau
			return true;
		}
		if (!hasMove()) {		//Les deux joueurs doivent passer leur tour
			return true;
		}
		if (!this.playerPass) {
			if (!this.history.add(this)) { //Si on ne peut pas stocker un état , c-à-d cet état est déjà joué 
				return true;			   // => Le plateau de jeu revient dans un état qui a déjà été joué.
			}
		}
		return false;
	}
	
	public boolean hasMove() {
		return getMove(this.player1).size() != 0 || getMove(this.player2).size() != 0;
	}
	
	public HashSet<Move> getMove(int player) {
		HashSet<Move> list = new HashSet<Move>();
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				if (this.board[i][j] == player) {				//Chercher pour un pion
					for (int c = 1 ; c < 3 ; c++) {				//Pour chaque type : 1 (cloner) , 2 (sauter)
						for (int dx = -c ; dx <= c ; dx += c) {		
							for (int dy = -c ; dy <= c ; dy += c) {		//Chercher pour les cases vides autours
								int x = i + dx;
								int y = j + dy;
								if (x >= 0 && x <= 6 && y >= 0 && y <= 6 && (x != i || y != j)) { //Eleminer les cas à l'exterieur du plateau
									if (this.board[x][y] == 0) {								  //Pratique pour les pions dans les coins ou les côtés
										int[] pos = {i,j};
										int[] direction =  {x,y};
										list.add(new Move(pos,Math.max(Math.abs(dx),Math.abs(dy)),direction));
									}
								}
							}
						}
					}
				}
			}
		}
		
		return list;
	}
	
	public double getScore(int player) { 
		double points = 0;
		double totalPoints = 0;
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				if (this.board[i][j] != 0) {
					totalPoints += 1;
					if (this.board[i][j] == player) {
						points += 1;
					}
				}
			}
		}
		return points/totalPoints;
	}
	public State play(Move move) {
		int[][] board = new int[7][7];			//Faire un copie du plateau
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				board[i][j] = this.board[i][j];
			}
		}
		if (move != null) {		//move == null : joueur passe son tour
			board[move.destination[0]][move.destination[1]] = board[move.pos[0]][move.pos[1]];
			if (move.type == 2) 
				board[move.pos[0]][move.pos[1]] = 0;
			for (int i = -1; i<=1; i++) {
				for (int j = -1; j<=1; j++) {
					int x = i + move.destination[0];
					int y = j + move.destination[1];
					if (x >= 0 && x <= 6 && y >= 0 && y <= 6) {
						if (board[x][y] != 0 && board[x][y] != board[move.destination[0]][move.destination[1]])
							board[x][y] = board[move.destination[0]][move.destination[1]];
					}
				}
			}
		}
		HashSet<State> history = new HashSet<State>();
		for (State state : this.history) {
			history.add(state);
		}
		if (this.currentPlayer == this.player1)
			return new State(board,this.player1,this.player2,this.player2,history);
		else
			return new State(board,this.player1,this.player2,this.player1,history);
	}
	
	public void getPresentation() {
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				System.out.print(" " + this.board[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
		else if (  o == null || ! (o instanceof State) ) {
		    return false;
		} else {
			State otherAsState = (State)o;
			for (int i=0 ; i<7 ; i++) {
				for (int j = 0; j<7; j++) {
					if (this.board[i][j] != otherAsState.board[i][j]) {
						return false;
					}
				}
			}
			return this.currentPlayer == otherAsState.currentPlayer;
		}
	}
	
	@Override 
	  public int hashCode() {
		  return Arrays.deepHashCode(this.board)*this.currentPlayer;
	  }
	
	public Move getBestMove(State game, int pdr, boolean alphabeta) {
		Move bestMove = null;
		double bestValue = 0;
		long counter = 0;
		HashSet<Move> list = game.getMove(game.currentPlayer);
		System.out.println(list.size());
		if (list.isEmpty())
			return null;
		else {
			System.out.println(list.size());
			for (Move move : list) {		
				Algorithme a = new Algorithme(this.currentPlayer, pdr);
				State newState = game.play(move);
				double value;
				if (alphabeta)
					value = a.alphabeta(newState,0,1,pdr);
				else
					value = a.minimax(newState,pdr);
				if (value >= bestValue) {
					bestValue = value;
					bestMove = move;
				}
				counter = counter + a.counter;
			}
		}
		System.out.println("Noeud recherché :" + counter);
		return bestMove;
		
	}
	
	public static void main(String[] args) {
		int[][] board = {{2,0,0,0,0,0,1},
		         		 {0,0,0,0,0,0,0},
		         		 {0,0,0,0,0,0,0},
		         		 {0,0,0,0,0,0,0},
		         		 {0,0,0,0,0,0,0},
		         		 {0,0,0,0,0,0,0},
		         		 {1,0,0,0,0,0,2}};
		HashSet<State> history = new HashSet<State>();
		State game = new State(board,1,2,1,history);
		System.out.println(" Begin ");
		int pdrb = Integer.parseInt(args[0]);
		int pdrr = Integer.parseInt(args[1]);
		boolean alphabeta = Boolean.parseBoolean(args[2]); 
		game.getPresentation();
		int i=0;
		int pdr;
		while (!game.isOver()) {
			i = (i+1) % 2;
			if (i == 1 ) {
				System.out.println("Tour de Bleu: ");
				pdr = pdrb;
			}
			else {
				System.out.println("Tour de Rouge: ");
				pdr = pdrr;
			}
			Move move = game.getBestMove(game,pdr,alphabeta);
			if (move != null) {
				move.getPresentation();
				game = game.play(move);
				game.getPresentation();
			}
			else {
				System.out.println("Pass!");
				game = game.play(move);
				game.playerPass = true;
			}
		}
		System.out.println(" End ");
	}
}

