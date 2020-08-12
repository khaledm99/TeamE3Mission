package application;

/**
 * Classname: TextApplication
 *
 * Version: 1
 * 
 * Author: Khaled Mograbee
 * 
 * Description:	This class displays election data based on user input
 * 				using the classes PollList, Factory, Poll, and Party. 
 * 				The application prompts the user for total seats, names of parties,
 * 				whether data should be manually input or randomly generated, and the method of 
 * 				displaying the data.
 */
import java.util.Scanner;

import model.Factory;
import model.Party;
import model.Poll;
import model.PollList;
import model.PollListFullException;

public class TextApplication {
	
	public static final int MAX_NUMBER_OF_STARS = 25;
	private PollList polls;
	
	// Default constructor
	public TextApplication() {
	}
	
	/**
	 * Constructor taking a pollList as a parameter
	 * @param polls
	 */
	public TextApplication(PollList polls) {
		this.polls = polls;
	}
	
	/**
	 * displayPollsBySeat method. Takes a list of party names and displays 
	 * each poll sorted by seat. Also displays an aggregate of the poll data.
	 * Doesn't return anything.
	 * @param partyNames
	 */
	public void displayPollsBySeat(String[] partyNames) {
		Poll[] pollList = polls.getPolls();
		for (int pollIndex = 0; pollIndex<pollList.length; pollIndex++) {
				displayPollDataBySeat(pollList[pollIndex]);
		}
		Poll aggregate = polls.getAggregatePoll(partyNames);
		displayPollDataBySeat(aggregate); 
			
	}
	
	// Returns a PollList object
	public PollList getPolls() {
		return polls;
	}
	
	/**
	 * displayPollDataBySeat method. Takes a poll and displays it visually sorted by seats
	 * using the textVisualizationBySeats method from the Party class.
	 * Doesn't return anything
	 * @param aPoll
	 */
	public void displayPollDataBySeat(Poll aPoll) {
		Party[] parties = aPoll.getPartiesSortedBySeats();
		int maxSeats = polls.getNumOfSeats();
		float seatsPerStar = maxSeats / MAX_NUMBER_OF_STARS;
		int starsForMajority = (int)((maxSeats/2)/seatsPerStar); // Calculates how many stars to fill 50% of the total seats
		System.out.println(aPoll.getPollName());
		for (int index = 0; index<parties.length; index++) {
			System.out.println(parties[index].textVisualizationBySeats(MAX_NUMBER_OF_STARS, starsForMajority, seatsPerStar));
			
		}
	}
	
	/**
	 * run method. This method runs the entire application. Takes user input using Scanner
	 * for various data to be displayed. Allows the user to change display mode after viewing or quit.
	 * Doesn't return anything. 
	 */
	public void run() {
		Scanner seatInp = new Scanner(System.in);
		Scanner namesInp = new Scanner(System.in);
		Scanner pollsInp = new Scanner(System.in);
		Scanner genInp = new Scanner(System.in);
		
		System.out.println("Welcome to the Poll Tracker");
		System.out.print("How many seats are available in the election? ");
		int seatCount = seatInp.nextInt();
		
		System.out.println("Which parties are in the election (provide names, comma separated): ");

		String nameString = "";
		nameString = namesInp.nextLine();
		String[] nameList = nameString.split(",");

		if (nameString.isEmpty()) {
		System.out.println("Error: Need proper party names.");
		}
			
		
		System.out.println("How many polls do you want to track? ");
		int pollCount = pollsInp.nextInt();
		
		System.out.println("Would you like me to create a random set of polls? ");
		String randomSet = genInp.nextLine();
		
		if (randomSet.equals("yes")) {
			Factory fact = new Factory(seatCount);
			fact.setPartyNames(nameList);
			polls = fact.createRandomPollList(pollCount);
		}
		else if (randomSet.equals("no")){
			polls = new PollList(pollCount, seatCount);
			Poll[] pollList = polls.getPolls();
			Scanner projSeatsInp = new Scanner(System.in); // Initializes scanner for prompting the projected number of seats
			Scanner projVotesInp = new Scanner(System.in); // Initializes scanner for prompting the projected vote percentage
			
			// Fills each Poll in the PollList with parties
			for (int pollIndex = 0; pollIndex < pollCount; pollIndex++) {
				System.out.println("Enter data for poll " + pollIndex+ ": ");
				pollList[pollIndex] = new Poll("Poll" + pollIndex, nameList.length);
				for (int nameIndex = 0; nameIndex<nameList.length; nameIndex++) {
					try {
						pollList[pollIndex].addParty(new Party(nameList[nameIndex]));
					} catch (PollFullException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// Retrieves a sorted array of parties
				Party[] parties = pollList[pollIndex].getPartiesSortedBySeats();
				
				// Prompts user for party data and uses setter methods to set instance variables
				for (int nameIndex = 0; nameIndex < nameList.length; nameIndex++) {
					System.out.println("Enter data for " + nameList[nameIndex] + " party:");
					System.out.println("What is the projected number of seats?");
					float projSeats = projSeatsInp.nextFloat();
					System.out.println("What is the projected percentage of votes (in decimal form)?");
					float projVotes = projVotesInp.nextFloat();
					try {
						parties[nameIndex].setProjectedNumberOfSeats(projSeats);
					} catch (InvalidPartyDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						parties[nameIndex].setProjectedPercentageOfVotes(projVotes);
					} catch (InvalidPartyDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				// Fills polls with the sorted and data-filled parties
				for (int partyIndex = 0; partyIndex < parties.length; partyIndex++) {
					try {
						pollList[pollIndex].addParty(parties[partyIndex]);
					} catch (PollFullException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			for (int index = 0; index < pollList.length; index++) {
				try {
					polls.addPoll(pollList[index]);
				} catch (PollListFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				
		}
		else {
			System.out.println("Enter either yes or no");
		}
		
		// Prompts the user for a choice between displaying all the data, just the aggregate data, or quitting 
		Scanner optionInp = new Scanner(System.in);
		System.out.println("Options: all (show result of all polls), aggregate (show aggregate result), quit (end application)");
		System.out.print("Choose an option: ");
		String option = optionInp.nextLine();
		while (!option.equals("quit")) {
			if (option.equals("all")) {
				displayPollsBySeat(nameList);
			}
			else if (option.equals("aggregate")) {
				Poll aggregate = polls.getAggregatePoll(nameList);
				displayPollDataBySeat(aggregate);
			}
			else {
				System.out.println("Please choose a valid option");
			}
			
			System.out.println("Options: all (show result of all polls), aggregate (show aggregate result), quit (end application)");
			System.out.print("Choose an option: ");
			option = optionInp.nextLine();
			
		}
		System.out.println("End");
	}
	

	public static void main(String[] args) {
		
		TextApplication app = new TextApplication(null);
	
		app.run();
		
	}
}
