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
			System.out.println("Enter your name (Max of 12 characters):");
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
		return Math.round(number * 100d) / 100;
	}
	
	public boolean setBet(double newBet) {
		//Sets the bet if it is valid. If it is not the method returns false
		if(newBet >= 2 && newBet <= 500 && newBet <= money) {
			bet = newBet;
			money -= bet;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addInsurance(double newInsurance) {
		//Adds insurance if it is valid. If it is not the method returns false
		if(newInsurance >= 0 && newInsurance <= bet / 2 &&
				newInsurance <= money) {
			insurance = newInsurance;
			money -= insurance;
			return true;
		} else {
			return false;
		}
	}
	
	public void recieveReward(double reward) {
		//Recieves the reward from the match and sets bet and insurance back
		// to zero
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
	}
	
	public boolean doubledown() {
		//Doubles down if the player has enough money
		if(bet * 2 <= money) {
			money -= bet;
			bet += bet;
			return true;
		} else {
			return false;
		}
	}
}