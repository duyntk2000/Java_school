package games;

import players.*;

public abstract class AbstractGame implements Game{

  public Player player1;
  public Player player2;
  public Player currentPlayer;

  public AbstractGame(Player player1, Player player2){
    this.player1 = player1;
    this.player2 = player2;
    this.currentPlayer = player1;
  }

  public abstract void doExecute(int coup);

  public void echangeJoueur() {
	if(this.currentPlayer == this.player1) {
	  this.currentPlayer = player2;
	}
	else {
	  this.currentPlayer = player1;
	}
  }

  @Override
  public void execute(int coup){
    doExecute(coup);
    echangeJoueur();
  }

  @Override
  public Player getCurrentPlayer(){
    return this.currentPlayer;
  }
}
