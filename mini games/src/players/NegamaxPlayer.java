package players;

import games.*;

public class NegamaxPlayer implements Player{
	
	public int evaluate(Game current_situation) {
		Integer res = null;
		if (current_situation.isOver()) {
			if (current_situation.getWinner() == current_situation.getCurrentPlayer())
				return 1;
			else if (current_situation.getWinner() != current_situation.getCurrentPlayer() && current_situation.getWinner() != null)
				return -1;
			else
				return 0;
		}
		else {
			for (int c : current_situation.validMoves()) {
				Game copy = current_situation.copy();
				copy.execute(c);
				int v = - evaluate(copy);
				if (res == null || v > res ) {
					res = v;
				}
			}
		}
		return res;
	}
	
	@Override
	public int chooseMove(Game game) {
		Integer best_move = null;
		Integer best_value = null;
		for (int c : game.validMoves()) {
			Game copy = game.copy();
			copy.execute(c);
			int v = - evaluate(copy);
			if (best_value == null || v > best_value) {
				best_value = v;
				best_move = c;
			}	
		}
		return best_move;
	}
}
