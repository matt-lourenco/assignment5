/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a hand of cards
 *
 ****************************************************************************/

public class Hand extends Deck {
	
	Hand() {
		//Default constructor to override BlackjackDeck's constructor
	}
	
	public Card popCard(Card card) {
		//Removes the specific card from the hand
		cards.remove(card);
		return card;
	}
}