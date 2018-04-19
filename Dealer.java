/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a dealer for a game of blackjack. This
 *     class runs the entire game and is the class that should be run.
 *
 ****************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dealer extends Player {
	
	Deck deck = new Deck();
	Deck discardPile = new Deck(true);
	ArrayList<User> users = new ArrayList<User>();
	
	private boolean choice(String question) {
		//Ask the player a question. Return true if the answer is yes
		System.out.println(question);
		String input = "";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(System.in));
		try {
			//Get user input
			input = reader.readLine();
		} catch (IOException noInput) {
			noInput.printStackTrace();
		}
		
		return input.toUpperCase().equals("YES") ||
				input.toUpperCase().equals("Y");
	}
	
	private void displayPlayer(User user) {
		//Print the data from the inputted user
		if(user.isSplit()) {
			for(SplitPair pair: user.getSplitPairs()) {
				System.out.println("\n" + pair.playerName + ":\n");
				
				for(Card card: pair.getCards()) {
					System.out.print(card.getFace() + " ");
				}
				
				System.out.println("\nBet: " + pair.getBet() +
									"Insurance: " + "\n");
			}
		} else {
			System.out.println("\n" + user.playerName + ":\n");
			
			for(Card card: user.getCards()) {
				System.out.print(card.getFace() + " ");
			}
			
			System.out.println("\nMoney: " + user.getMoney() + "Bet: " +
							user.getBet() + "Insurance: " +
							user.getInsurance() + "\n");
		}
	}
	
	private void display() {
		//Display the data from all players
		System.out.println("Dealer:\n" + getCard(0).getFace() + " " +
							getCard(1).getBack() + "\n");
		for(User user: users) {
			displayPlayer(user);
		}
	}
	
	private void dealCards() {
		//Deal two cards to each player
		for(int deal = 0; deal < users.size() * 2; deal++) {
			int user = deal % 3;
			users.get(user).hit(deckPop());
		}
	}
	
	private Card deckPop() {
		//Checks if the next card is a blank and pops a card
		if(deck.nextIsBlank()) {
			deck.pop();
			deck.refill(discardPile);
		}
		return deck.pop();
	}
	
	private void manageNaturals() {
		//Force the player to stand if he has a natural
		for(User user: users) {
			if(user.hasNatural()) {
				user.stand();
			}
		}
	}
	
	private void managePairs() {
		//Allow the player to split their pair
		for(User user: users) {
			if(user.getCard(0).getValue().equals(
				user.getCard(1).getValue()) && user.getMoney() >=
				user.getBet()*2) {
				displayPlayer(user);
				choice()
			}
		}
	}
}