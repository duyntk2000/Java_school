package games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import players.*;

public class TicTacToe extends AbstractGame {

	public Player mCoups[][];

	public TicTacToe(Player player1, Player player2) {
		super(player1, player2);
		mCoups = new Player[3][];
		for(int i = 0; i < 3; i++) {
			mCoups[i] = new Player[3];
		}

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				mCoups[i][j] = null;
	}

	@Override
	public void doExecute(int idCoup) {
		int x = idCoup / 3;
		int y = idCoup % 3;

		mCoups[x][y] = super.getCurrentPlayer();

	}

	public boolean isValid(int idCoup) {
		ArrayList<Integer> coupsValide = validMoves();

		for(Integer coup : coupsValide)
		{
			if(coup == idCoup)
				return true;
		}

		return false;
	}
  @Override
	public ArrayList<Integer> validMoves() {
		ArrayList<Integer> nbCoupValide = new ArrayList<Integer>();


		for(int j = 0; j < 3; j++)
			for(int i = 0; i < 3; i++) {
				if(mCoups[j][i] == null) {
					Integer idCoup = j * 3 + i;
					nbCoupValide.add(idCoup);
				}
			}

		return nbCoupValide;
	}

	public boolean wins(Player joueur, int row, int column, int deltaRow, int deltaColumn) {
		if(mCoups[row][column] == joueur && mCoups[row+deltaRow][column+deltaColumn] == joueur && mCoups[row+2*deltaRow][column+2*deltaColumn] == joueur ) {
			return true;
		}
		else {
			return false;
		}
	}
	@Override
	public Player getWinner() {
		for(int i = 0; i < 3; i++) {
			if(mCoups[i][0] != null)
				if(wins(mCoups[i][0], i, 0, 0, 1) == true)
					return mCoups[i][0];
		}
		for(int j = 0; j < 3; j++) {
			if(mCoups[0][j] != null)
				if(wins(mCoups[0][j], 0, j, 1, 0) == true)
					return mCoups[0][j];
		}
		if(mCoups[0][0] != null)
			if(wins(mCoups[0][0], 0, 0, 1, 1) == true)
				return mCoups[0][0];
		if(mCoups[0][2] != null)
			if(wins(mCoups[0][2], 0, 2, 1, -1) == true)
				return mCoups[0][2];
		return null;
	}
	@Override
	public boolean isOver() {
		if(getWinner() != null || (getWinner() == null && validMoves().size() == 0) ) {
			return true;
		}
		return false;
	}
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
		return chaine;
	}
	public String moveToString(int coup) {
		return "(" + coup / 3 + "," + (coup%3) + ")";
	}
	
	@Override
	public Game copy() {
		TicTacToe res = new TicTacToe(this.player1, this.player2);
		res.currentPlayer = super.currentPlayer;
		for (int i = 0 ; i<9 ; i++) {
			res.mCoups[i/3][i%3] = this.mCoups[i/3][i%3];
		}
		return res;
	}
	

	@Override 
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
		else if (  o == null || ! (o instanceof TicTacToe) ) {
		    return false;
		} else {
			TicTacToe otherAsTicTacToe = (TicTacToe)o;
			boolean board = true;
			for (int i=0 ; i<9 ; i++) {
				if (this.mCoups[i/3][i%3] != null && otherAsTicTacToe.mCoups[i/3][i%3] != null) {
					if (this.mCoups[i/3][i%3].equals(otherAsTicTacToe.mCoups[i/3][i%3]))
						board = true;
				}
				else if  (this.mCoups[i/3][i%3] == otherAsTicTacToe.mCoups[i/3][i%3]) {
					board = true;
				}
				else {
					board = false;
					break;
				}
			}
			return board && this.currentPlayer.equals(otherAsTicTacToe.currentPlayer);
		}
	}
	
	@Override 
	  public int hashCode() {
		  return Objects.hash(this.player1, this.player2, this.currentPlayer, Arrays.deepHashCode(this.mCoups));
	  }
}
