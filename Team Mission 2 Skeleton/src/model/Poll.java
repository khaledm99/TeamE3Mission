package model;

import java.util.Arrays;

import java.util.Collections;
import java.util.Comparator;
/**
 * Poll class for the CPSC 233 Assignment 1
 * Contents are encapsulated and the skeleton modified 
 *
 * @author Xavier Lewis
 *
 */

public class Poll {
	private String name = "Unnamed Poll";
	private Party[] parties;
	private int numberOfParties = 0;	
	private String [] stringParties;

/**
 * 
 * @param name instance variable of the party from the Party Class
 * @param maxNumberOfParties specified number of parties to be added to the poll class
 * If maxNumberOfParties is not included in the constructor (default 0) the party array is set to size of 10
 * and prints out the constructor argument error
 */
	public Poll(String name, int maxNumberOfParties) {
		this.name = name; 
		if (maxNumberOfParties > 0) {
			parties = new Party[maxNumberOfParties];
			stringParties = new String[maxNumberOfParties];

		}
		else {
			System.out.println("Invalid number of parties");
			parties = new Party[10];
			stringParties = new String [10];

		}
	}
	
	public String getPollName() 
	{
		return name;
	}
	/**
	 * getter method for poll name
	 * @return
	 */
	public int getNumberOfParties() 
	{
		return numberOfParties;
	}
	/**
	 * getter method for instance variable  numberOfParties
	 */
	
	
	/**
	 * 
	 * @param aParty argument provided to the addParty method which adds the argument
	 * to the previously initialized poll array
	 * if a duplicate is found the numberOfParties variable is not changed
	 * and the party in the poll is replaced with the argument provided.
	 * aPartyString is a copy of the aParty argument in lowercase String type
	 * the argument if no duplicate is found is added to the parties array of a set size
	 */
	public void addParty(Party aParty)
	{	
		
		boolean duplicateFound = false;
		String aPartyString = aParty.getName().toLowerCase();
		//System.out.println("aParty : " + aParty);
		for (int i = 0; i < parties.length; i++)
		{
			if (aPartyString.equals(stringParties[i]))
			{
				//System.out.println(aPartyString + " " +stringParties[i]);		
				//System.out.println("duplicateFound");
				duplicateFound = true;
				parties[i] = aParty;
			}
		}
		
		if (parties.length > numberOfParties && duplicateFound == false)
		{
		parties[numberOfParties] = aParty;
		stringParties[numberOfParties] = aPartyString;
		numberOfParties++;
		}
		
	}
	/**
	 * 
	 * @param name argument provided that scans the parties array for a matching aParty.getName() string
	 *  
	 * @return returns the element in the parties array at index i if the argument is found, if not return null
	 */
	
	public Party getParty(String name) 
	{
		for (int i = 0; i  < parties.length; i++) 
		{
			if (name.toLowerCase().equals(stringParties[i])) 
			{
				return parties[i];
			}
			else
			{
				
			}
		}
			
		return null;		
	}
		/**
		 * a comparator compares the float seat values from the getProjectNumberOfSeats method from the Party Class
		 * The following two methods SortedBySeats and SortedByVotes were written by Khaled Mograbee at the request of the author
		 * the two methods functions very similarly 
		 * @return an array of parties but sorted by their float seat values from highest to lowest
		 */
	public Party[] getPartiesSortedBySeats() 
	{	
		Party[] sortedParties = new Party[numberOfParties];    //Comparison added by Khaled
//		Arrays.sort(sortedParties, new SeatPartyComparator()); //Comparison added by Khaled
		int size = 0;
		
		for (Party value: this.parties) {
			if (value != null) {
				size++;
			}
		}
		
		for (int index = 0; index < size; index++) {
			sortedParties[index] = this.parties[index];
		}
	    Arrays.sort(sortedParties, new Comparator<Party>() {
	        @Override
	        public int compare(Party o1, Party o2) {
	            return Float.compare(o1.getProjectedNumberOfSeats(), o2.getProjectedNumberOfSeats());
	        }
	    });
	    Collections.reverse(Arrays.asList(sortedParties));
		return sortedParties;
	}

	public Party[] getPartiesSortedByVotes()
	{
		Party[] sortedParties = new Party[numberOfParties];    //Comparison added by Khaled
//		Arrays.sort(sortedParties, new SeatPartyComparator()); //Comparison added by Khaled
		int size = 0;
		for (Party value: this.parties) {
			if (value != null) {
				size++;
			}
		}
		
		for (int index = 0; index < size; index++) {
			sortedParties[index] = this.parties[index];
		}
	    Arrays.sort(sortedParties, new Comparator<Party>() {
	        @Override
	        public int compare(Party o1, Party o2) {
	            return Float.compare(o1.getProjectedPercentageOfVotes(), o2.getProjectedPercentageOfVotes());
	        }
	    });
	    Collections.reverse(Arrays.asList(sortedParties));
		return sortedParties;
	}
	
	@Override
	/**
	 * @return a string that contains the entire poll array with the name of the poll at the top and the number of
	 * added parties specified by the tally instance variable numberOfParties
	 */
	public String toString()
	{
		String pollPartytoString = "";
		
		for(int index = numberOfParties - 1; index > -1 ; index-- )
		{
			pollPartytoString = parties[index] + "\n" + pollPartytoString ;
		}
		pollPartytoString = this.name + "\n" + pollPartytoString;
		//System.out.print(pollPartytoString);
		return pollPartytoString;
	}

}
