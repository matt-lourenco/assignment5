/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a Blackjack player
 *
 ****************************************************************************/

import java.util.ArrayList;

public abstract class Player {
	
	public Hand hand = new Hand();
	public String playerName;
	private static int playerNumber = 0;
	
	Player() {
		//Default constructor
		playerName = "Player " + playerNumber++;
	}
	
	public Integer getScore() {
		//Finds and returns the total score of the player
		Integer score = 0;
		for(Card card: hand.getCards()) {
			score += card.getPoints();
		}
		
		//Check for aces
		for(Card aceCheck: hand.getCards()) {
			if(aceCheck.getValue().equals(Values.ACE) && score + 10 <= 21) {
				score += 10;
			}
		}
		
		return score;
	}
	
	public boolean hasNatural() {
		//Finds if this player has an ace and a card worth ten points
		return hand.getCards().size() == 2 && getScore() == 21;
	}
	
	public ArrayList<Card> getCards() { return hand.getCards(); } //Getter
	
	public Card getCard(int position) {
		//Get the card at a specified position
		return hand.getCard(position);
	}
}