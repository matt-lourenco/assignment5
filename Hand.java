/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a hand of cards
 *
 ****************************************************************************/

public class Hand extends Deck {
	
	Hand() {
		//Default constructor
		super(true);
	}
	
	public Card popCard(Card card) {
		//Removes the specific card from the hand
		cards.remove(card);
		return card;
	}
	
	public Card getCard(int position) {
		//Get the card at a specified position
		return cards.get(position);
	}
}