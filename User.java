/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a user
 *
 ****************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class User extends Player {
	
	private double bet = 0;
	private double money = 500;
	private double insurance = 0;
	protected boolean stand = false;
	public ArrayList<SplitPair> splitPairs = new ArrayList<SplitPair>();
	
	User() {
		//Allow the user to customize their name
		String input = "";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(System.in));
		while(true) {
			System.out.println(playerName + " enter your name"
					+ " (Max of 12 characters):");
			try {
				//Get user input
				input = reader.readLine();
			} catch (IOException noInput) {
				noInput.printStackTrace();
			}
			
			if(input.length() <= 12) {
				break;
			}
		}
		playerName = input;
	}
	
	public double getBet() { return bet; } //Getter
	
	public double getMoney() { return money; } //Getter
	
	public double getInsurance() { return insurance; } //Getter
	
	public ArrayList<SplitPair> getSplitPairs() {
		//Getter
		return splitPairs;
	}
	
	private double round2(double number) {
		//Round a number to two decimals for money
		return Math.round(number * 100d) / 100d;
	}
	
	public boolean setBet(double newBet) {
		//Sets the bet if it is valid. If it is not the method returns false
		newBet = round2(newBet);
		if(newBet >= 2 && newBet <= money) {
			bet = newBet;
			money -= bet;
			return true;
		} else {
			return false;
		}
	}
	
	public void addInsurance() {
		//Adds insurance that is equal to half of the bet
		insurance = bet / 2;
		money -= insurance;
	}
	
	public void finishInsurance() {
		//Adds twice the insurance amount plus
		// takes back the original insurance
		money += insurance * 3;
		insurance = 0;
	}
	
	public void recieveReward(double reward) {
		//Recieves the reward from the match and sets bets back to zero
		money += round2(reward);
		bet = 0;
		insurance = 0;
	}
	
	public void hit(Card card) {
		//Add a card to the hand
		if(!stand) {
			hand.add(card);
			if(getScore() > 21) {
				stand = true;
			}
		}
	}
	
	public void stand() {
		//Allow the player to stand
		stand = true;
	}
	
	public boolean isSplit() {
		//Return true if the player's hand has been split
		if(splitPairs.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void splitPair() {
		//Splits the player's cards
		money -= bet;
		splitPairs.add(new SplitPair(this, hand.pop()));
		splitPairs.add(new SplitPair(this, hand.pop()));
		bet = 0;
		stand();
	}
	
	public void doubledown(Card card) {
		//Doubles down
		money -= bet;
		bet += bet;
		hit(card);
		stand();
	}
}