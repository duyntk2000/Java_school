package games;

import java.util.ArrayList;
import players.*;

public class TicTacToeWithHints extends TicTacToe {
  public TicTacToeWithHints( Player player1 , Player player2) {
    super(player1, player2);

  }

  public ArrayList<Integer> hints() {
    ArrayList<Integer> hint = new ArrayList<Integer>();
    ArrayList<Integer> validM = super.validMoves();
    super.echangeJoueur();
    for (int c : validM ) {
      super.doExecute(c);

      if (super.getWinner() != null) {
        hint.add(c);
      }
      int x = c / 3;
  	  int y = c % 3;

      super.mCoups[x][y] = null;
    }
    super.echangeJoueur();
    return hint;

  }
  
  @Override
  public String situationToString() {
		String chaine = "###############";
		chaine += System.lineSeparator();
		for (int i = 0;i<3;i++) {
			for (int j = 0;j<3;j++) {
				if (mCoups[i][j] != null) {
					chaine += mCoups[i][j].toString();
					chaine += "   ";
				}
				else {
					chaine += "null";
					chaine += "   ";
				}
			}
			chaine += System.lineSeparator();
		}
		chaine += "###############";
		chaine += System.lineSeparator();
		chaine += "Hints : ";
		chaine += hints();
		return chaine;
	}
}
