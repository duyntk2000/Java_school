package players;

import java.util.HashMap;
import java.util.Map;
import games.*;

public class NegamaxPlayerWithCache extends NegamaxPlayer{
	
	private Map<Game,Integer> cache = new HashMap<Game,Integer>();
	public NegamaxPlayerWithCache() {
	}
	
	@Override 
	public int evaluate(Game current_situation) {
		if (cache.containsKey(current_situation))
			return cache.get(current_situation);
		
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
		cache.put(current_situation, res);
		return res;
	}
}
