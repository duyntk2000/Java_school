package main;

import java.util.Random;
import java.util.Scanner;
import games.*;
import players.*;
import plays.*;


public class Demo{

  public static void main(String[] args){
	  boolean replay = false;
	  do {
	    Scanner scanner=new Scanner(System.in);
	    Random rand = new Random(123);
	    String j1;
	    String j2;
	    String jeu;
	    Player player1 = null;
	    Player player2 = null;
	    Game game = null;
	    do {
	    	System.out.println("Choisissez joueur 1 : Human (0) Random (1) Negamax (2) NegamaxWithCache (3)");
	    	j1 = scanner.next();
	    } while (!j1.equals("0") && !j1.equals("1") && !j1.equals("2") && !j1.equals("3"));
	    
	    switch (j1) {
	    	case "0":
	    		System.out.println("Entrez nom : ");
	    		player1 = new Human(scanner.next(), scanner);
	    		break;
	    	case "1":
	    		player1 = new RandomPlayer(rand);
	    		break;
	    	case "2":
	    		player1 = new NegamaxPlayer();
	    		break;
	    	case "3":
	    		player1 = new NegamaxPlayerWithCache();
	    		break;
	    }
	    
	    do {
	    	System.out.println("Choisissez joueur 2 : Human (0) Random (1) Negamax (2) NegamaxWithCache (3)");
	    	j2 = scanner.next();
	    } while (!j2.equals("0") && !j2.equals("1") && !j2.equals("2") && !j2.equals("3"));
	    
	    switch (j2) {
		case "0":
			System.out.println("Entrez nom : ");
			player2 = new Human(scanner.next(), scanner);
			break;
		case "1":
			player2 = new RandomPlayer(rand);
			break;
		case "2":
			player2 = new NegamaxPlayer();
			break;
		case "3":
			player2 = new NegamaxPlayerWithCache();
			break;
	    }
	    
	    do {
	    	System.out.println("Choisissez le jeu: Nim (n) TicTacToe(avec hints) (t/h) ");
	    	jeu = scanner.next();
	    }
	    while (!jeu.equals("n") && !jeu.equals("t") && !jeu.equals("h"));
	    	
	    switch (jeu) {
	    	case "n":
	    		String size;
	    		String max;
	    		boolean check;
	    		// Entrer et vérifier pile size et nombre matches maximum retiré sont entier
	    		do {
	    			check = true;
	    			System.out.println("Entrez nombre de matches : ");
	    			size = scanner.next();
	    			System.out.println("Entrez nombre de matches maximum retiré : ");
	    			max = scanner.next();
	    			
	    			try { 
	    	            Integer.parseInt(size);
	    	        }  
	    	        catch (NumberFormatException e) { 
	    	        	check = false; 
	    	        }
	    			
	    			try { 
	    	            Integer.parseInt(max);
	    	        }  
	    	        catch (NumberFormatException e) { 
	    	        	check = false; 
	    	        }
	    			
	    			if (check == true) {
	    				if (Integer.parseInt(size) <= Integer.parseInt(max)) {
	    					check = false;
	    				}
	    			}
	    			
	    		} 
	    		while (!check);
	    		game = new Nim(Integer.parseInt(size), Integer.parseInt(max), player1, player2);
	    		break;
	    	case "t":
	    		game = new TicTacToe(player1, player2);
	    		break;
	    	case "h":
	    		game = new TicTacToeWithHints(player1, player2);
	    		break;
	    }
	    
	    Orchestrator orchestrator = new Orchestrator(game);
	    orchestrator.play();
	    
	    System.out.println("Replay ? (y) ");
	    if (scanner.next().equals("y"))
	    	replay = true;
	    else 
	    	scanner.close();
	  }
	  while (replay);
  }
}
