package model;

import model.InvalidPartyDataException;
import application.PollListFullException;
import model.PollFullException;
/**
 * Classname: PollList
 * 
 * Version: 0
 * 
 * Author: William Ho
 * 
 *  Description:    Creates a PollList class from the arguments: numOfPolls (int) and numOfSeats (int). 
 *  				numOfPolls is the total number of Polls in the list. numOfSeats is the number of seats 
 *  				available in the election covered by the polls in the list. The PollList returns a 
 *  				PollList object that contains an Array of Polls and and Int of numOfSeats.
 */
//PollFullExeption not declared to be thrown for entire class. Only needed to resolve exception within selected methods
//Xavier Lewis
public class PollList {
	private Poll[] polls;
	private int numOfSeats;
	
	/**
	 * PollList constructor. Takes the following parameters:
	 * @param numOfPolls: number of polls this list should be able to contain
	 * @param numOfSeats: number of seats that are available in the election covered by the polls in the list.
	 * 
	 * The number of polls should be at least 1. If it is not, the number of polls is set to 5. 
	 * The number of seats should be at least 1. If it is not, the number of seats is set to 10.
	 */
	public PollList(int numOfPolls, int numOfSeats) throws InvalidSetupDataException{
		if (numOfPolls < 1) {
			//polls = new Poll[5];
			//System.out.println("Note: The argument numOfPolls is invalid (less than 1). It is now set it to 5.");
			throw new InvalidSetupDataException();
		} else {
			polls = new Poll[numOfPolls];
		}
		
		if (numOfSeats < 1) {
			this.numOfSeats = 10;
			System.out.println("Note: The arguement numOfSeats is invalid (less than 1). It is now set it to 10.");
		} else { 
			this.numOfSeats = numOfSeats;
		}
	}
	
	// Returns the number of seats as an integer.
	public int getNumOfSeats() {
		return numOfSeats;
	}
	
	// Returns the polls as a Poll object.
	public Poll[] getPolls() {
		return polls;
	}
	
	public void setPolls(Poll[] polls) {
		this.polls = polls;
	}
	
	/**
	 * addPoll method. This method adds a Poll object to the Poll array in PollList. Does not return anything.
	 * @param aPoll: Poll object
	 * @throws PollListFullException 
	 */
	public void addPoll(Poll aPoll) throws PollListFullException  {

		int counter = 0;
		
		// Checks if the Poll argument is null. If it is, print an error and does not add the poll.
		if (aPoll != null) {	
			for (int index = 0; index < polls.length; index++) {
				// Checking if the poll is already in the Poll array. If it is, replace the existing poll with the new poll.
				if (polls[index] == null || polls[index].getPollName().toLowerCase().contains(aPoll.getPollName().toLowerCase())) {
					polls[index] = aPoll;
					index = polls.length;
				} else {
					counter++;
				}

			}

		} else {
			System.out.println("Error: The aPoll argument value is null. The poll did not change.");
		}
		
		if (counter == polls.length) {
			throw new PollListFullException();
		}
		
	}	
		
	/**
	 * getAggregatePoll method. This method takes an aggregate of all polls in the list. 
	 * The total number of seats is normalized to the total number of seats available if there are more seats being divided than the total number of seats available. 
	 * The total percentage of votes is normalized to 100% if it is over 100%.
	 * @param partyNames: An array of party names in string format.
	 * @return Poll object of the aggregate poll.
	 */
	public Poll getAggregatePoll(String[] partyNames) {
		
		// Obtain the occupied size of the partyName string array. Used to create a new Poll object with the appropriate size.
		int size = 0;
		for (String value: partyNames) {
			if (value != null) {
				size++;
			}
		}
		
		Poll result = new Poll("Aggregate", size);
		
		// Introducing sum variables to keep track of total seats and percentage of votes in the poll.
		float seatSum = 0;
		float voteSum = 0;

		// Loops through the polls and adds the projectedNumberOfSeats and projectedPercentageOfVotes for seatSum and voteSum.
		for (String name: partyNames) {
			Party partyValue = this.getAveragePartyData(name);
			seatSum += partyValue.getProjectedNumberOfSeats();
			voteSum += partyValue.getProjectedPercentageOfVotes();
		}

		// Checks if seatSum is over the allowable number of seats. If it is, it will normalize the projectedNumberOfSeat average.
		// Checks if voteSum is over 100%. If it is, it will normalize the projectedPercentageOfVotes to 100%.
		// Creates the Poll Object with the proper data.
		if (seatSum > this.numOfSeats) {
			System.out.println("first if test");
			for (String name: partyNames) {
				Party partyValue = this.getAveragePartyData(name);
				try {
					partyValue.setProjectedNumberOfSeats(partyValue.getProjectedNumberOfSeats() * size * 100 / seatSum);
				} catch (InvalidPartyDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					result.addParty(partyValue);
				} catch (PollFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (voteSum > 1) {
			System.out.println("second if test");
			for (String name: partyNames) {
				Party partyValue = this.getAveragePartyData(name);
				try {
					partyValue.setProjectedPercentageOfVotes(partyValue.getProjectedPercentageOfVotes() / voteSum);
				} catch (InvalidPartyDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					result.addParty(partyValue);
				} catch (PollFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			for (String name: partyNames) {
				Party partyValue = this.getAveragePartyData(name);
				try {
					result.addParty(partyValue);
				} catch (PollFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * getAveragePartyData method. 
	 * This method obtains an average of the projectedNumberOfSeats and projectedPercentageOfVotes for a specified party  
	 * @param partyName： Name of the party specified.
	 * @return Party Object
	 */
	public Party getAveragePartyData(String partyName) {
		float totalProjectedPartySeats = 0;
		float totalProjectedVotes = 0;
		float totalPolls = 0;
		float averageProjectedNumberOfSeats = 0;
		float averageProjectedVotes = 0;

		// Obtaining the occupied size of the Poll object.
		int size = 0;
		for(Poll value : polls) {
			if (value != null) {
				size++;
			}
		}

		for (int index = 0; index < size; index++) {
			//Party[] length = polls[index].getPartiesSortedBySeats;
			//for (int i = 0; i < polls[index].getPartiesSortedBySeats.length);
			
			
			Party partyValue = polls[index].getParty(partyName);
			if (partyValue != null){
				// Checking if partyValue is equal to the specified party name.
				if (partyValue.getName().equals(partyName)) {
					totalProjectedPartySeats += partyValue.getProjectedNumberOfSeats();
					totalProjectedVotes += partyValue.getProjectedPercentageOfVotes();
					totalPolls++;
				}
			}
		}

		// If the party does not exist in any of the polls, set both averageProjectedNumberOfSeats and averageProjectedVotes to 0.
		if  (totalPolls == 0) {
			averageProjectedNumberOfSeats = 0;
			averageProjectedVotes = 0;

		} else {
			// Compute the average for projectedNumberOfSeats and projectedVotes.
			averageProjectedNumberOfSeats = totalProjectedPartySeats / totalPolls;
			averageProjectedVotes = totalProjectedVotes / totalPolls;
		}

		Party partyObject = null;
		try {
			partyObject = new Party(partyName, averageProjectedNumberOfSeats, averageProjectedVotes);
		} catch (InvalidPartyDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return partyObject;
	}

	public Poll adjustPollToMaximums(Poll aPoll) {
		return aPoll;
	}

	// Creates a string of the PollList object, returning the number of seats in a string format.
	@Override
	public String toString() {
		String finalString = "Number of seats: " + this.getNumOfSeats();
		return finalString; 
	}
}
