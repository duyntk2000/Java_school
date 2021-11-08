package players;
import java.util.Scanner;
import games.*;

public class Human implements Player{

  private String name;
  private Scanner scan;
  public Human(String name, Scanner scan){
    this.name = name;
    this.scan = scan;

  }

  public String toString(){
    return this.name;
  }

  @Override
  public int chooseMove(Game game) {
    int move = 0;
    System.out.println(game.validMoves());
    boolean a = true;
    while (a==true) {
      System.out.println("Entrez votre coup : ");
      move = this.scan.nextInt();
      if (game.validMoves().contains(move))
        a = false;
    }
    return move;
  }
  
}
