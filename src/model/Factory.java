package model;

import java.util.Random;

import application.InvalidPartyDataException;
import application.PollListFullException;
import model.PollFullException;

/**
 * <h1>The Factory Class</h1>
 * The Factory class implements all other classes
 * in the program and generates random results for them
 * to be used for creating example elections.
 * <p>
 * The Factory class contains methods for generating
 * random data at the party, poll, and poll list levels,
 * and takes as arguments the names of the parties to be used
 * and the number of seats available in the election.
 * @author colec
 */
public class Factory {
	private int numOfSeats;
	private String[] partyNames;
	
	/**
	 * <h1>Getter Method</h1>
	 * This getter method retrieves the names of the
	 * parties being used to generate random data from
	 * the Factory's string array "partyNames"
	 * @author colec
	 * @return
	 */
	public String[] getPartyNames(){
		return partyNames;
	}
	
	/**
	 * <h1>Setter Method</h1>
	 * This setter method takes as argument the names to be
	 * placed into the Factory's string array "partyNames" for use in 
	 * generating random data. If a null value is entered, a default list
	 * is used instead.
	 * @author colec
	 * @param newParties
	 */
	public void setPartyNames(String[] newParties) {
	
		if (newParties == null) {
			System.out.println("Error. You did not enter any names.");
		} else {
		this.partyNames = newParties;
		}
	}
	
	/**
	 * <h1>Constructor</h1>
	 * This is the only constructor for Factory and takes
	 * as argument the number of available seats to use for
	 * generating random data. It also fills the "partyNames"
	 * array with a default list of political parties.
	 * @author colec
	 * @param seats
	 */
	public Factory(int seats) {
		
		if (seats >= 0) {
			numOfSeats = seats;
		} else {
			System.out.println("Error. Cannot have negative seats");
			numOfSeats = 0;
		}
		partyNames = new String[] {"NDP","Liberal","CPC","Green","PPC","Rhinoceros"};
	}

	/**
	 * <h1>Creating a Random Party</h1>
	 * The first method for generating data takes as arguments
	 * the party name, maximum seats, and maximum vote percentage
	 * available to a party. Within these constraints, a random
	 * number of seats and a random percent of votes (within 5% of the seats)
	 * are generated for a new party. If values entered are null, the party
	 * is given a default name and 0 seats and votes. The method returns an
	 * object of the party class with this stored information.
	 * @author colec
	 * @param name
	 * @param maxSeats
	 * @param maxPercent
	 * @return
	 */
	public Party createRandomParty(String name, int maxSeats, int maxPercent) {
		Random rand = new Random();
		if (name != null && maxSeats >= 0 && maxPercent >= 0) {
			float randSeats = rand.nextInt(maxSeats + 1);
			float randPercent = (rand.nextInt(11) + (((randSeats / numOfSeats) * 100) - 5));
			if (randPercent > maxPercent) {
				randPercent = maxPercent;
			}
			
			if (randPercent < 0) {
				randPercent = 0;
			}
			
			Party party = new Party(name);
			
			try {
				
				party.setProjectedNumberOfSeats(randSeats);
				
				party.setProjectedPercentageOfVotes(randPercent / 100);
				
			} catch (InvalidPartyDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return party;
			
		} else {
			System.out.println("Error. Cannot have null name or negative seats/votes");
			Party party = new Party("party");
			
			
			try {
				party.setProjectedNumberOfSeats(0);
				
				party.setProjectedPercentageOfVotes(0);
				
			} catch (InvalidPartyDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return party;
		}
	}
	
	/**
	 * <h1>Creating a Random Poll</h1>
	 * This method takes as argument the name of the poll to create
	 * and uses the previous createRandomParty method to generate a poll of
	 * parties whose random vote-and-seat shares all sum to the maximum available
	 * number of seats and vote percentage.
	 * <p>
	 * The seats and votes of the generated parties, however, do not form a sufficiently
	 * random distribution such that each party has an equal chance of winning.
	 * The rate of wins will decrease for each additional party generated.
	 * To randomize the results further, this class uses the private helper class
	 * "randomizeWinner"
	 * <p>
	 * randomizeWinner simply scrambles the order of the party array
	 * given as an argument and then resets their names back to the
	 * order specified by "partyNames", effectively randomizing which party
	 * in the list will receive each set of generated poll data. This ensures that
	 * each party generated by the createRandomPoll method has an equal chance of
	 * winning a random poll.
	 * <p>
	 * The createRandomPoll method calls randomizeWinner to scramble the generated
	 * party array, then each party in this array is added to the created Poll object
	 * and the finished Poll is then returned.
	 * then for each 
	 * @author colec
	 * @param name
	 * @return
	 */
	public Poll createRandomPoll(String name) {
		if (name == null) {
			name = "poll";
			System.out.println("Error. Poll name cannot be null.");
		}
		
		Poll poll = new Poll(name, (partyNames.length));
		int remainingSeats = numOfSeats;
		int remainingPercent = 100;
		Party[] winRandomizerList = new Party[partyNames.length];
		
		for(int i = 0; i < partyNames.length - 1; i++) {
			Party newParty = createRandomParty(partyNames[i], remainingSeats, remainingPercent);
			remainingSeats -= newParty.getProjectedNumberOfSeats();
			remainingPercent -= (newParty.getProjectedPercentageOfVotes() * 100);
			winRandomizerList[i] = newParty;
		}
		
		Party lastParty = null;
		
		try {
			lastParty = new Party((partyNames[partyNames.length - 1]), remainingSeats, (remainingPercent / 100f));
		} catch (InvalidPartyDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		winRandomizerList[winRandomizerList.length - 1] = lastParty;
		Party[] finalResults = randomizeWinner(winRandomizerList);
		for(int i = 0; i < partyNames.length; i++) {
			try {
				poll.addParty(finalResults[i]);
			} catch (PollFullException e) {
				e.printStackTrace();
			}
		}
		
		return poll;
	}
	
	/*
	 This private method, randomizeWinner, is a helper method for 
	 createRandomPoll. To ensure the distribution of votes and seats
	 is sufficiently random, this method takes the list of parties generated
	 by createRandomPoll and scrambles them to ensure that each party has an
	 equal chance of winning a poll.
	 */
	private Party[] randomizeWinner(Party[] partyList) {
		Random rand = new Random();
		
		// This loop scrambles the order of the parties in the array
		for (int i = 0; i < partyList.length; i++) {
			int randIndex = rand.nextInt(partyList.length);
			Party partyToSwitch = partyList[i];
			partyList[i] = partyList[randIndex];
			partyList[randIndex] = partyToSwitch;
		}
		
		// Now each set of party data is re-assigned the name that matches its index in partyNames
		Party[] listWithCorrectNames = new Party[partyList.length];
		for (int i = 0; i < partyList.length; i++) {
			
			try {
				listWithCorrectNames[i] = new Party(partyNames[i], partyList[i].getProjectedNumberOfSeats(), partyList[i].getProjectedPercentageOfVotes());
			} catch (InvalidPartyDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return listWithCorrectNames;
	}
	
	/**
	 * <h1>Creating a Random Poll List</h1>
	 * This final method creates a list of randomly generated polls
	 * using the previous createRandomPoll method. It takes as argument
	 * the number of polls to create, and assigns a default incrementing
	 * name to each poll in the list. The Poll List is then returned.
	 * @author colec
	 * @param numOfPolls
	 * @return
	 * @throws PollListFullException 
	 */
	public PollList createRandomPollList(int numOfPolls) throws InvalidSetupDataException {
		if (numOfPolls < 0) {
			System.out.println("Error. Number of Polls cannot be negative");
			numOfPolls = 0;
		}
		
		PollList pollList = new PollList(numOfPolls, numOfSeats);
		int pollNamer = 1;
		for(int i = 0; i < numOfPolls; i++) {
			Poll newPoll = createRandomPoll("poll" + pollNamer);
			try {
				pollList.addPoll(newPoll);
			} catch (PollListFullException e) {
				System.out.println("PollList is full.");
				e.printStackTrace();
			}
			pollNamer ++;
		}
		
		return pollList;
	}

}
