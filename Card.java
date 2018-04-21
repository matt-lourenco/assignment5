/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a card
 *
 ****************************************************************************/

public class Card {
	
	Suits suit;
	Values value;
	String face;
	String back = String.valueOf(Character.toChars(0x0001F0A0));
	private boolean blank = false;
	
	Card(int position) {
		//Uses position to generate the face value
		
		//Determine the suit and colour
		int suitNumber = position % 4;
		switch (suitNumber) {
		case 0: suit = Suits.SPADES; break;
		case 1: suit = Suits.HEARTS; break;
		case 2: suit = Suits.DIAMONDS; break;
		case 3: suit = Suits.CLUBS; break;
		default: suit = Suits.BLANK; break;
		}
		
		//Determine the value
		int valueNumber = position % 13;
		switch (valueNumber) {
		case 0: value = Values.ACE; break;
		case 1: value = Values.TWO; break;
		case 2: value = Values.THREE; break;
		case 3: value = Values.FOUR; break;
		case 4: value = Values.FIVE; break;
		case 5: value = Values.SIX; break;
		case 6: value = Values.SEVEN; break;
		case 7: value = Values.EIGHT; break;
		case 8: value = Values.NINE; break;
		case 9: value = Values.TEN; break;
		case 10: value = Values.JACK; break;
		case 11: value = Values.QUEEN; break;
		case 12: value = Values.KING; break;
		default: value = Values.BLANK; break;
		}
		
		String hexNumber = "0001F0" + suit.getUniLetter() + value.getUniLetter();
		face = String.valueOf(Character.toChars(Integer.parseInt(hexNumber, 16)));
	}
	
	Card(boolean blank) {
		//Constructor
		this(0);
		this.blank = blank;
	}
	
	public Suits getSuit() { return suit; } //Getter
	
	public Values getValue() { return value; } //Getter
	
	public String getBack() { return back; } //Getter
	
	public String getFace() { return face; } //Getter
	
	public boolean isBlank() { return blank; } //Getter
	
	public Integer getPoints() { return value.getPoints(); } //Getter
	
	public boolean isAce() {
		//returns true if the card is an ace
		return value.equals(Values.ACE);
	}
}