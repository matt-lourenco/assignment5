/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Apr 2018
 * This program is a blueprint for a dealer for a game of blackjack. This
 *     class runs the entire game and is the class that should be run.
 *
 ****************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dealer extends Player {
	
	private Deck deck = new Deck();
	private Deck discardPile = new Deck(true);
	private ArrayList<User> users = new ArrayList<User>();
	private BufferedReader reader = new BufferedReader(new InputStreamReader
			(System.in));
	
	public void startGame() {
		//This method controls the game
		
		//Get the amout of players
		System.out.println("How many players will play?");
		String input = "";
		try {
			//Get user input
			input = reader.readLine();
		} catch (IOException noInput) {
			noInput.printStackTrace();
		}
		
		int numberOfPlayers = 1;
		
		try {
			//Convert string to Integer
			numberOfPlayers = Integer.parseInt(input);
			if(numberOfPlayers < 1) {
				numberOfPlayers = 1;
			}
		} catch (NumberFormatException stringInput) {}
		
		for(int player = 0; player < numberOfPlayers; player++) {
			users.add(new User());
		}
		
		boolean gameEnd = false;
		
		//Keep the game going until everyone runs out of money
		while(!gameEnd) {
			playOneRound();
			
			for(int user = users.size() - 1; user >= 0; user--) {
				if(users.get(user).getMoney() < 2) {
					System.out.println(users.get(user).playerName +
							" must leave due to insufficient funds");
					users.remove(user);
				}
			}
			
			if(users.size() == 0 || !choice("Do you want to keep playing?")) {
				System.out.println("GAME OVER");
				gameEnd = true;
			}
		}
	}
	
	private void playOneRound() {
		//Plays a round of blackjack
		
		//get bets
		String input = "";
		for(User user: users) {
			System.out.println(user.playerName + " enter your bet:"
							+ "\nMoney: $" + user.getMoney());
			double bet = 0.0;
			while(true) {
				try {
					//Get user input
					input = reader.readLine();
				} catch (IOException noInput) {
					noInput.printStackTrace();
				}
				
				try {
					//Convert string to Double
					bet = Double.parseDouble(input);
					if(bet > user.getMoney()) {
						System.out.println("You do not have enough money");
					} else if(user.setBet(bet)) {
						break;
					} else {
						System.out.println("Enter a bet greater than $2");
					}
				} catch (NumberFormatException stringInput) {
					System.out.println("Enter a valid number");
				}
			}
		}
		
		//Deal cards
		dealCards();
		
		//Initial display
		display();
		
		//Ask for insurance if necessary
		manageInsurance();
		
		//Force players with naturals to stand
		manageNaturals();
		
		//Manage players who double down or split
		for(User user: users) {
			
			//Manage split pairs
			if(user.getCard(0).getValue().equals(
				user.getCard(1).getValue()) &&
				user.getMoney() >= user.getBet()) {
				System.out.println("----------");
				displayPlayer(user);
				if(choice("Do you want to split your pair?")) {
					user.splitPair();
					for(SplitPair pair: user.getSplitPairs()) {
						pair.hit(deckPop());
					}
				}
			}
			
			//Manage doubledowns
			if(user.getScore() >= 9 && user.getScore() <= 11 &&
					user.getMoney() >= user.getBet()) {
				System.out.println("----------");
				displayPlayer(user);
				if(choice("Do you want to double down?")) {
					user.doubledown(deckPop());
				}
			}
		}
		
		finishDeal();
		
		//Display Dealer's cards
		System.out.println("\n----------\nDealer:");
		for(Card card: getCards()) {
			System.out.print(card.getFace() + " ");
		}
		System.out.println("\nScore: " + getScore());
		
		//Start handing out money
		
		//Insurance
		if(getCard(0).isAce() && getScore() == 21) {
			for(User user: users) {
				user.finishInsurance();
			}
		}
		
		for(User user: users) {
			
			//Split pairs
			if(user.isSplit()) {
				for(SplitPair pair: user.getSplitPairs()) {
					
					//Split pairs with an ace as their first card
					// do not get 1.5X their bet from a blacjack
					
					if(getScore() > 21) {
						if(pair.getCard(0).isAce() &&
								pair.getScore() == 21) {
							pair.recieveReward(pair.getBet() * 2);
						} else if(pair.hasNatural()) {
							pair.recieveReward(pair.getBet() * 2.5);
						} else if(pair.getScore() <= 21) {
							pair.recieveReward(pair.getBet() * 2);
						} else {
							pair.recieveReward(0);
						}
					} else if(pair.getScore() < getScore()
								|| pair.getScore() > 21) {
						pair.recieveReward(0);
					} else if (pair.getScore() == getScore()) {
						pair.recieveReward(pair.getBet());
					} else if(pair.getCard(0).isAce() &&
								pair.getScore() == 21) {
						pair.recieveReward(pair.getBet() * 2);
					} else if(pair.hasNatural()) {
						pair.recieveReward(pair.getBet() * 2.5);
					} else {
						pair.recieveReward(pair.getBet() * 2);
					}
					
					displaySplit(pair);
					
					discardPile.addCards(pair.hand.removeAll());
				}
				
				user.getSplitPairs().clear();
				displayPlayer(user);
				user.stand = false;
			} else {
				
				//Users who have not split
				if(getScore() > 21) {
					if(user.hasNatural()) {
						user.recieveReward(user.getBet() * 2.5);
					} else if(user.getScore() <= 21) {
						user.recieveReward(user.getBet() * 2);
					} else {
						user.recieveReward(0);
					}
				} else if(user.getScore() < getScore()
						|| user.getScore() > 21) {
					user.recieveReward(0);
				} else if (user.getScore() == getScore()) {
					user.recieveReward(user.getBet());
				} else if(user.hasNatural()) {
					user.recieveReward(user.getBet() * 2.5);
				} else {
					user.recieveReward(user.getBet() * 2);
				}
				
				displayPlayer(user);
				
				discardPile.addCards(user.hand.removeAll());
				user.stand = false;
			}
		}
		
		discardPile.addCards(hand.removeAll());
	}
	
	private boolean choice(String question) {
		//Ask the player a question. Return true if the answer is yes
		System.out.println(question);
		String input = "";
		try {
			//Get user input
			input = reader.readLine();
		} catch (IOException noInput) {
			noInput.printStackTrace();
		}
		
		return input.toUpperCase().equals("YES") ||
				input.toUpperCase().equals("Y");
	}
	
	private boolean hitOrStand() {
		//Ask the player if they want to hit or stand
		String input = "";
		while(true) {
			System.out.println("Will you hit or stand?");
			try {
				//Get user input
				input = reader.readLine();
			} catch (IOException noInput) {
				noInput.printStackTrace();
			}
			
			if(input.toUpperCase().equals("HIT")) {
				return true;
			} else if(input.toUpperCase().equals("STAND")) {
				return false;
			}
		}
	}
	
	private void displaySplit(SplitPair pair) {
		//Print the data from the inputted split pair
		System.out.println("\n" + pair.playerName + ":");
		
		for(Card card: pair.getCards()) {
			System.out.print(card.getFace() + " ");
		}
		
		System.out.println("\nScore: " + pair.getScore() +
							"\nBet: $" + pair.getBet());
	}
	
	private void displayPlayer(User user) {
		//Print the data from the inputted user
		if(user.isSplit()) {
			for(SplitPair pair: user.getSplitPairs()) {
				displaySplit(pair);
			}
		} else {
			System.out.println("\n" + user.playerName + ":");
			
			for(Card card: user.getCards()) {
				System.out.print(card.getFace() + " ");
			}
			
			System.out.println("\nScore: " + user.getScore() +
							"\nMoney: $" + user.getMoney() + "\nBet: $" +
							user.getBet() + "\nInsurance: $" +
							user.getInsurance());
		}
	}
	
	private void display() {
		//Display the data from all players
		System.out.println("\n----------\nDealer:\n" +
							getCard(0).getFace() + " " +
							getCard(1).getBack());
		for(User user: users) {
			displayPlayer(user);
		}
	}
	
	private void dealCards() {
		//Deal two cards to each player
		for(int deal = 0; deal < 2; deal++) {
			hand.add(deckPop());
			for(User user: users) {
				user.hit(deckPop());
			}
		}
	}
	
	private Card deckPop() {
		//Checks if the next card is a blank and pops a card
		if(deck.nextIsBlank()) {
			deck.pop();
			deck.refill(discardPile);
		}
		return deck.pop();
	}
	
	private void finishDeal() {
		//Ask each player if they would like to hit until they all stand
		for(User user: users) {
			
			//Deal to split pairs
			if(user.isSplit()) {
				for(SplitPair split: user.getSplitPairs()) {
					while(!split.stand) {
						System.out.println("----------");
						displaySplit(split);
						if(hitOrStand()) {
							split.hit(deckPop());
							if(split.stand && !split.getCard(0).isAce()) {
								displaySplit(split);
								System.out.println("You have gone bust");
							}
						} else {
							split.stand();
						}
					}
				}
			}
			
			while(!user.stand) {
				System.out.println("----------");
				displayPlayer(user);
				if(hitOrStand()) {
					user.hit(deckPop());
					if(user.stand) {
						displayPlayer(user);
						System.out.println("You have gone bust");
					}
				} else {
					user.stand();
				}
			}
		}
		
		//Force the dealer to hit until they have a score of 17+
		while(getScore() < 17) {
			hand.add(deckPop());
		}
	}
	
	private void manageNaturals() {
		//Force the player to stand if he has a natural
		for(User user: users) {
			if(user.hasNatural()) {
				System.out.println("----------\n" + user.playerName
						+ " has 21");
				user.stand();
			}
		}
	}
	
	private void manageInsurance() {
		//Manage any players who wich to put down insurance
		if(getCard(0).isAce()) {
			for(User user: users) {
				if(user.getMoney() >= user.getBet() / 2) {
					System.out.println("----------");
					displayPlayer(user);
					if(choice("Do you want to put down insurance?")) {
						user.addInsurance();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		//Instantiate a dealer and start the game
		Dealer dealer = new Dealer();
		dealer.startGame();
	}
}