package players;

import java.util.Random;
import games.*;

public class RandomPlayer implements Player{

  private Random rand;
  public RandomPlayer(Random rand){
    this.rand = rand;
  }

  @Override
  public int chooseMove(Game game) {
    int a = this.rand.nextInt(game.validMoves().size());
    return game.validMoves().get(a);
  }

  @Override
  public String toString(){
    return "Joueur aléatoire n° " + this.hashCode();
  }
  
  @Override
  public boolean equals(Object o) {
	  if (this == o) {
          return true;
      } else if (  o == null || ! (o instanceof RandomPlayer) ) {
		    return false;
      } else {
    	  RandomPlayer otherAsRandomPlayer = (RandomPlayer)o;
    	  return this.toString() == otherAsRandomPlayer.toString();
      }
  }
}
