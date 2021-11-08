
public class Algorithme {
	
	public int player;
	public int pdr; //Profondeur de raisonnement
	public long counter = 0;
	
	public Algorithme(int player, int pdr) {
		this.player = player;
		this.pdr = pdr;
	}
	
	public double minimax(State game, int pdr) {
		double value;
		this.counter ++;
		if (pdr == 0) {
			return game.getScore(this.player);
		} else if (game.isOver()) {
			if (game.getScore(this.player) > 0.5)
				return 1;
			else
				return 0;
		} else {
			if (game.currentPlayer == this.player) {
				value = 0;
				for (Move move : game.getMove(game.currentPlayer)) {
					State newgame = game.play(move);
					double m = minimax(newgame, pdr-1);
					value = Math.max(value, m);
				}
			}
			else {
				value = 1;
				for (Move move : game.getMove(game.currentPlayer)) {
					State newgame = game.play(move);
					double m = minimax(newgame, pdr-1);
					value = Math.min(value, m);
				}
				
			}
		return value;
		} 
	}
	
	public double alphabeta(State game, double alpha, double beta, int pdr) {
		this.counter ++;
		if (pdr == 0) {
			return game.getScore(this.player);
		} else if (game.isOver()) {
			if (game.getScore(this.player) > 0.5)
				return 1;
			else
				return 0;
		} else {
			if (game.currentPlayer == this.player) {
				for (Move move : game.getMove(game.currentPlayer)) {
					State newgame = game.play(move);
					alpha = Math.max(alpha, alphabeta(newgame,alpha,beta,pdr-1));
					if (alpha >= beta)
						return alpha;
				}
			return alpha;
			}
			else {
				for (Move move : game.getMove(game.currentPlayer)) {
					State newgame = game.play(move);
					beta = Math.min(beta, alphabeta(newgame,alpha,beta,pdr-1));
					if (alpha >= beta)
						return beta;
				}
			return beta;
			}
		} 
	}
}
