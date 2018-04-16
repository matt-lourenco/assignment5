/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is an enumeration for the possible suits of a card
 *
 ****************************************************************************/

public enum Suits {
	SPADES ('A'),
	HEARTS ('B'),
	DIAMONDS ('C'),
	CLUBS ('D'),
	BLANK ('D');
	
	private char unicodeLetter;
	
	Suits(char letter) {
		//Defines the proper unicode letter for the card face
		unicodeLetter = letter;
	}
	
	public char getUniLetter() { return unicodeLetter; } //Getter
}