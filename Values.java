/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is an enumeration for the possible values of a card
 *
 ****************************************************************************/

public enum Values {
	ACE ('1', 1),
	TWO ('2', 2),
	THREE ('3', 3),
	FOUR ('4', 4),
	FIVE ('5', 5),
	SIX ('6', 6),
	SEVEN ('7', 7),
	EIGHT ('8', 8),
	NINE ('9', 9),
	TEN ('A', 10),
	JACK ('B', 10),
	QUEEN ('D', 10),
	KING ('E', 10),
	BLANK ('F', 0);
	
	private char unicodeLetter;
	private Integer points;
	
	Values(char letter, Integer pointValue) {
		//Defines the proper unicode letter and point value for the card
		unicodeLetter = letter;
		points = pointValue;
	}
	
	public char getUniLetter() { return unicodeLetter; } //Getter
	
	public Integer getPoints() { return points; } //Getter
}