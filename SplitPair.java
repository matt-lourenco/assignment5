/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a split pair
 *
 ****************************************************************************/

public class SplitPair extends Player {
	
	private double bet;
	public User originalUser;
	protected boolean stand = false;
	
	SplitPair() {
		//Override superclass's constructor
	}
	
	SplitPair(User originalUser, Card card) {
		//Constructor to copy the original user's data
		this.originalUser = originalUser;
		bet = originalUser.getBet();
		hand.add(card);
		playerName = originalUser.playerName + " split pair";
	}
	
	public double getBet() { return bet; } //Getter
	
	public void recieveReward(double reward) {
		//Passes the reward to the original user
		originalUser.recieveReward(reward);
	}
	
	public void hit(Card card) {
		//Add a card to the hand
		
		//Split pairs cannot draw more than once if the starting card is an ace
		if(getCard(0).getValue().equals(Values.ACE)) {
			hand.add(card);
			stand();
		}
		
		if(!stand) {
			hand.add(card);
			if(getScore() > 21) {
				stand();
			}
		}
	}
	
	public void stand() {
		//Allow the player to stand
		stand = true;
	}
}