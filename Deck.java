/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a deck of cards
 *
 ****************************************************************************/

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	ArrayList<Card> cards = new ArrayList<Card>();
	
	Deck() {
		//Default constructor
		
		for(int position = 0; position < 52*6; position++) {
			add(new Card(position));
		}
		
		shuffle();
		addWhiteCard();
	}
	
	public ArrayList<Card> getCards() { return cards; } //Getter
	
	public Card pop() { return cards.remove(0); } //Pop the top card
	
	public void add(Card card) {
		//Add a card to the bottom of the deck
		cards.add(card);
	}
	
	public void addCards(ArrayList<Card> deck) {
		//Adds a deck to the bottom of this deck
		for(Card card: deck) {
			add(card);
		}
	}
	
	public ArrayList<Card> removeAll() {
		//Removes all of the cards from the deck and returns them in an list
		@SuppressWarnings("unchecked")
		ArrayList<Card> returnList = (ArrayList<Card>) cards.clone();
		cards = null;
		return returnList;
	}
	
	public void shuffle() {
		//Shuffles the deck of cards
		
		@SuppressWarnings("unchecked")
		ArrayList<Card> tempList = (ArrayList<Card>) cards.clone();
		cards = null;
		
		Random rand = new Random();
		while (tempList.size() > 0) {
			add(tempList.remove(rand.nextInt(tempList.size())));
		}
	}
	
	public void addWhiteCard() {
		//Adds a blank card to the deck
		
		Random rand = new Random();
		int randomPosition = rand.nextInt(76 - 60) + 60;
		cards.add(cards.size() - randomPosition, new Card(true));
	}
	
	public void refill(Deck discard) {
		//Refills the deck and adds a blank card
		
		discard.shuffle();
		addCards(discard.removeAll());
		addWhiteCard();
	}
}