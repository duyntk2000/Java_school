package games;

import java.util.ArrayList;
import java.util.Objects;

import players.*;

public class Nim extends AbstractGame {
  private int pile_size;
  private int max_matches;
  private int current_matches;

  public Nim(int pile_size,int max_matches,Player player1,Player player2){
    super(player1, player2);
    this.pile_size = pile_size;
    this.max_matches = max_matches;
    this.current_matches = pile_size;
  }

  public int getInitialNbMatches(){
    return this.pile_size;
  }

  public int getCurrentNbMatches(){
    return this.current_matches;
  }

  public String situationToString(){
    String sit = "Il reste " +this.current_matches+ " allumettes.";
    return sit;
  }

  public String moveToString(int nb_matches) {
	  return this.currentPlayer + " a retiré " + nb_matches + " allumettes";
  }

  @Override
  public void doExecute(int nb_matches){
    this.current_matches -= nb_matches;
  }

  public boolean isValid(int nb_matches){
    if (nb_matches>0 && this.current_matches - nb_matches >=0 && nb_matches <= this.max_matches){
      return true;
    }
    else{return false;}
  }
  @Override
  public ArrayList<Integer> validMoves(){
    ArrayList<Integer> nbCoupValide = new ArrayList<Integer>();

    for(int i = 1; i <= this.current_matches; i++){
      if(isValid(i)){
        nbCoupValide.add(i);
      }
    }
    return nbCoupValide;
  }

  @Override
  public boolean isOver(){
    if (this.current_matches == 0){
      return true;
    }
    else {return false;}
  }

  @Override
  public Player getWinner(){
    if (isOver()){
      return this.currentPlayer;
    }
    else{return null;}
  }
  
  @Override
  public Game copy() {
	  Nim res = new Nim(this.pile_size, this.max_matches, this.player1, this.player2);
	  res.current_matches = this.current_matches;
	  res.currentPlayer = super.currentPlayer;
	  return res;
  }
  
  @Override 
  public boolean equals(Object o) {
	  if (  o == null || ! (o instanceof Nim) ) {
		    return false;
		} else {
		    Nim otherAsNim = (Nim)o;
		    return this.currentPlayer.equals(otherAsNim.currentPlayer) && this.pile_size == otherAsNim.pile_size && this.max_matches == otherAsNim.max_matches && this.current_matches == otherAsNim.current_matches ; 
		}
  }
  
  @Override 
  public int hashCode() {
	  return Objects.hash(this.player1, this.player2, this.currentPlayer, this.current_matches, this.pile_size, this.max_matches);
  }

}
