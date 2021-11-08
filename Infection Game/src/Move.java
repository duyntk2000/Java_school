
import java.util.Arrays;
public class Move {
	public int[] pos;					//La position du pion
	public int type;					//1 (cloner) ou 2 (sauter)
	public int[] destination;			//La nouvelle position pour faire cloner/sauter
	
	public Move(int[] pos, int type, int[] destination) {
		this.pos = pos;
		this.type = type;
		this.destination = destination;
	}
	
	@Override
	public boolean equals(Object o) {									//Changer equals() et hashCode() pour éliminer les coups en duplicata
		if (this == o) {												//C'est le cas que plusieur pions peuvent cloner sur la même destination
            return true;												//Alors , on ne garde qu'un seul
        }
		else if (  o == null || ! (o instanceof Move) ) {
		    return false;
		} else {
			Move otherAsMove = (Move)o;
			if (this.destination[0] == otherAsMove.destination[0] && this.destination[1] == otherAsMove.destination[1] && this.type == otherAsMove.type && this.type == 1)
				return true;
			else
				return this.pos == otherAsMove.pos && this.type == otherAsMove.type && this.destination == otherAsMove.destination;
		}
	}
	
	@Override 
	  public int hashCode() {
		if (this.type == 2)
			return (int) Arrays.hashCode(this.pos) * Arrays.hashCode(this.destination);
		else 
			return Arrays.hashCode(this.destination);
	  }
	
	public void getPresentation() {
		if (this.type == 1)
			System.out.println("Clone case [" + this.destination[0] + "," + this.destination[1] + "]");
		else
			System.out.println("Jump from case [" + this.pos[0] + "," + this.pos[1] + "] to case [" + this.destination[0] + "," + this.destination[1] + "]");
	}
}
