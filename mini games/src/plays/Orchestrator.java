package plays;
import games.*;
import players.*;

public class Orchestrator{

  public Game game;

  public Orchestrator(Game game){
    this.game = game;
  }

  public void play(){
      while( game.isOver() == false ){
        Player now = game.getCurrentPlayer();
        System.out.println(game.situationToString());
        int move = now.chooseMove(game);
        
        System.out.println(game.moveToString(move));
        game.execute(move);

        if(game.getWinner() != null) {
        System.out.println(" ");
        System.out.println("Le gagnant est " + game.getWinner().toString());
        }

        if(game.getWinner() == null && game.validMoves().size() == 0)
        System.out.println("Match Nul");

      }



  }
}
