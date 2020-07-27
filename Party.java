

/**
 * Classname: Party
 *
 * Version: 0
 * 
 * Author: Homing Wat
 * 
 * Description:	This class represents a single political party. As inputs it takes the name of the party, 
 * 				projected number of seats to be won and projected percentage of votes to be gained.
 * 				Then, the class outputs two text visual representations of the data relevant to the party.
 * 				The first displaying a row of stars that represents the expected number of seats and a bar to indicate the number 
 * 				of seats needed for a majority in parliament. The second displaying a similar row of the first but 
 * 				is a visual representation of the percentage of votes the party is expected to win.
 */
	

public class Party {
	
	private String name;
	private float projectedNumberOfSeats;
	private float projectedPercentageOfVotes;
	
	//Party name setter
	public void setName (String givenName) {
		this.name = givenName;
	}
	
	//Party name getter
	public String getName() {
		return this.name;
		
	}
	
	//Projected number of seats setter
	public void setProjectedNumberOfSeats (float ProjectedNrs) {
		if(ProjectedNrs < 0.0) {
			System.out.println("ERROR: Cannot enter negative numbers");
		}
		else {
			this.projectedNumberOfSeats = ProjectedNrs;
		}
	}
	
	//Projected number of seats getter
	public float getProjectedNumberOfSeats() {
		return this.projectedNumberOfSeats;
	}
	
	//Projected percentage of votes setter
	public void setProjectedPercentageOfVotes(float ProjectedPercentage) {
		if(ProjectedPercentage < 0.0 || ProjectedPercentage > 1.000) {
			System.out.println("ERROR: Cannot enter a percentage less than zero or greater than 1");
		}
		else {
			this.projectedPercentageOfVotes = ProjectedPercentage;
		}
	}
	
	//Projected percentage of votes getter
	public float getProjectedPercentageOfVotes() {
		return this.projectedPercentageOfVotes;
	}
	
	/**
	 * Party Constructor. Takes the following parameters :
	 * 
	 * @param givenPartyName: the party name that of the new party
	 * 
	 */
	public Party (String givenPartyName){
		this.name = givenPartyName;
	}
	
	/**
	 * Party constructor. Takes the following parameters:
	 * @param givenPartyName
	 * @param numberOfSeats
	 * @param percentage
	 * 
	 * The number of seats should be at least 0 or greater. If not set to 0. 
	 * The percentage should be 0 or less than equal to 1. If not set to 0. 
	 */
	public Party (String givenPartyName, float numberOfSeats, float percentage) {
		this.name = givenPartyName;
		
		if(numberOfSeats < 0.0) {
			System.out.println("ERROR: Cannot enter negative numbers");
			this.projectedNumberOfSeats = 0;
		}
		else {
			this.projectedNumberOfSeats = numberOfSeats;
		}
		
		if(percentage < 0.0 || percentage > 1.0) {
			System.out.println("ERROR: Cannot enter a percentage less than zero or greater than 1");
			this.projectedPercentageOfVotes = 0;
		}
		
		else {
			this.projectedPercentageOfVotes = percentage;
		}
		 
	}
	
	
	/**
	 * Party method. Takes no arguments. Creates a string consisting of the Party information entered.  
	 */
	public String toString() {
		double projectedPercentageInPercent = projectedPercentageOfVotes*100;
		int projectedPercentage = (int)  projectedPercentageInPercent;
		return name + " (" + projectedPercentage + "%" + " of votes" + ", " + projectedNumberOfSeats + " seats" +")";
	}
	
	/**
	 * projectedPercentageOfSeats method. This method takes the integer totalNumberOfSeats and calculates the percentageOfSeats.
	 * Calculations will only be done if totalNumberOfSeats is greater than 0. Else, the percentage is set to 0. 
	 * @param totalNumberOfSeats
	 * @return percentageOfSeats calculation
	 * 
	 */
	public double projectedPercentOfSeats(int totalNumberOfSeats) {	
		if(totalNumberOfSeats > 0) {
		double percentageOfSeats = this.projectedNumberOfSeats/totalNumberOfSeats;
		return percentageOfSeats;
		}
		
		else {
				System.out.println("ERROR: Cannot have total number of seats equal 0");
				 double percentageOfSeats = 0;
				 return percentageOfSeats;
		}
		
	}
	
	/**
	 * visStringCreator method. This method is used by both textVisualizationBySeats and textVisualizationByVotes methods to 
	 * create a string array which is a visual representation of the data relevant to this party by displaying a row of stars
	 * that represents the expected number of seats and a bar to indicate the number of seats needed for a majority in parliament.
	 * @param maxStars
	 * @param nrOfStarsToPrint
	 * @param starsNeededForMajority
	 * @param projectedPercentageOfVotes
	 * @return string array created.
	 */
	private String visStringCreator(int maxStars, int nrOfStarsToPrint, int starsNeededForMajority, float projectedPercentageOfVotes) {

		int percentage = (int) (projectedPercentageOfVotes*100);
		
		String[] visualizationArray = new String[maxStars+2];
		
		int size = visualizationArray.length;
		
		int count = 0;
		
		int indexOfMajority = starsNeededForMajority;
		
		int index = 0;
		
		//Iterate through generated visualizationArray to add strings (Stars and line indicating when majority is won)
		while (index <= size-1) {
			
			if(nrOfStarsToPrint == 0) {
				if(!(index == indexOfMajority))
				visualizationArray[index]=" ";
				
				if (index == indexOfMajority) 
					visualizationArray[index]="|";
			}
			
			else {
					if (index == indexOfMajority) 
						visualizationArray[index]="|";
					
					if(!(index == indexOfMajority)) { 
						if(count < nrOfStarsToPrint) { 
							visualizationArray[index]="*";
							count++;
						}
						
						else {
							visualizationArray[index]=" ";
						}
					}
				}
			
			if(index == size-1)
				visualizationArray[size-1] = " " + this.name + " (" + percentage + "%" + " of votes" + ", " + projectedNumberOfSeats + " seats" +")";
			
			index++;
		
		}
		
		String visString = "";
		
		//Combine all strings in the visualizationArray into one string.
		for (int i=0; i<size; i++ ) {
			visString =  visString + visualizationArray[i];
			
		}
		
		return visString;
		
	}
	
	/**
	 * textVisualizationBySeats method. Takes the maxStars, starsNeededForMajority and numOfSeatsPerStar which are the text visualization constraints and 
	 * generates a text visualization within these constraints. 
	 * The projectedNumerOfSeats of the party must be greater than 0.0 in order to print stars. 
	 * @param maxStars
	 * @param starsNeededForMajority
	 * @param numOfSeatsPerStar
	 * @return text visualization string of the party information. 
	 */
	
	public String textVisualizationBySeats(int maxStars, int starsNeededForMajority, double numOfSeatsPerStar) {
		
		int nrOfStarsToPrint = 0;
		
		if(this.projectedNumberOfSeats > 0.0) {
			nrOfStarsToPrint = (int)(this.projectedNumberOfSeats/numOfSeatsPerStar);
			
		}

		String visualizationString = visStringCreator(maxStars,nrOfStarsToPrint, starsNeededForMajority,projectedPercentageOfVotes);
					
		return visualizationString;
	} 
	
	/**
	 * textVisualizationByVotes method. Takes the maxStars, starsNeededForMajority and percentOfVotesPerStar which are the text visualization constraints and 
	 * generates a text visualization within these constraints. 
	 * The projectedPercentageOfVotes must be greater than 0.0 in order to print stars.
	 * @param maxStars
	 * @param starsNeededForMajority
	 * @param percentOfVotesPerStar
	 * @return text visualization string of the party information. 
	 */
	
	public String textVisualizationByVotes(int maxStars, int starsNeededForMajority, double percentOfVotesPerStar) {
		
		int nrOfStarsToPrint = 0;
		
		if(this.projectedPercentageOfVotes > 0.0) {
			nrOfStarsToPrint = (int)((this.projectedPercentageOfVotes*100)/percentOfVotesPerStar);
		}
		
		String visualizationString = visStringCreator(maxStars,nrOfStarsToPrint, starsNeededForMajority,projectedPercentageOfVotes);
		
		return visualizationString;
	}
	
	
}
