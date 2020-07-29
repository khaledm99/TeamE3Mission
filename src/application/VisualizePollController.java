/**
 * Classname: VisualizePollController
 * 
 * Author: William Ho 
 * 
 * Description: Controller for the VisualizePollView FXML file. Creates pie charts of seats and votes for each party in a specified poll that the user selects from a
 * 			    choice box.
 * 
 */

package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

public class VisualizePollController extends PollTrackerController {

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private BorderPane VisualizePollController;

    @FXML
    private PieChart myVotePieChart;

    @FXML
    private PieChart mySeatPieChart;
    

	@Override
	public void refresh() {
		// Printing to the console to let the programmer know that this part is running
		System.out.println("In refresh");
	
		// Creating a new array of strings to hold poll names for the choicebox 
		String[] values = new String[getPollList().getPolls().length + 1];
		
		// Setting first value to Aggregate Poll (Similar to the assignment handout)
		values[0] = "Aggregate Poll";
		
		// Getting all the poll names
		for (int index = 1; index < getPollList().getPolls().length + 1; index++) {
			values[index] = getPollList().getPolls()[index - 1].getPollName();
		}
		
		// Initially Loading Aggregate Poll Pie Charts this is the view the user sees when they first enter this tab
    	myChoiceBox.setItems(FXCollections.observableArrayList(values));
    	PieChart.Data[] seatData = new PieChart.Data[getPollList().getPolls()[0].getNumberOfParties()];
		PieChart.Data[] voteData = new PieChart.Data[getPollList().getPolls()[0].getNumberOfParties()];
		
		// Obtaining the party names from the polls
		String[] partyNames = new String[getPollList().getPolls()[0].getNumberOfParties()];
		
		for (int i = 0; i < partyNames.length; i++) {
			partyNames[i] = getPollList().getPolls()[0].getPartiesSortedBySeats()[i].getName();
		}
		
		// Setting data for seats and votes
		for (int dataIndex = 0; dataIndex < partyNames.length; dataIndex++) {
			seatData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getAggregatePoll(partyNames).getParty(partyNames[dataIndex]).getProjectedNumberOfSeats());
			voteData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getAggregatePoll(partyNames).getParty(partyNames[dataIndex]).getProjectedPercentageOfVotes());
		}
		
		// Updating the pie chart to seat data and vote data for aggregate poll
		mySeatPieChart.setData(FXCollections.observableArrayList(seatData));
		myVotePieChart.setData(FXCollections.observableArrayList(voteData));	
		
		// Setting choicebox value to Aggregate Poll so that the user knows the pie charts are from an aggregate poll
		myChoiceBox.setValue("Aggregate Poll");
    	
		// Changing PieCharts if new selection is selected from choice box
    	myChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
    		@Override
    		public void changed(ObservableValue observable, Number oldValue, Number newValue) {
    			int index = newValue.intValue();
    			
    			// Creates pie charts based on polls that are selected (if aggregate poll is not selected)
    			if (index > 0) {
    				// Creating new seatData and voteData for the updated pie chart
    				PieChart.Data[] seatData = new PieChart.Data[getPollList().getPolls()[index - 1].getNumberOfParties()]; 
    				PieChart.Data[] voteData = new PieChart.Data[getPollList().getPolls()[index - 1].getNumberOfParties()];

    				//Obtaining a list of party names that are in the selected poll
    				String[] partyNames = new String[getPollList().getPolls()[index - 1].getNumberOfParties()];

    				for (int i = 0; i < partyNames.length; i++) {
    					partyNames[i] = getPollList().getPolls()[index - 1].getPartiesSortedBySeats()[i].getName();
    				}
    				
    				// Obtaining seat and vote data from each party from the poll
    				for (int dataIndex = 0; dataIndex < partyNames.length; dataIndex++) {
    					seatData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getPolls()[index - 1].getParty(partyNames[dataIndex]).getProjectedNumberOfSeats());
    					voteData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getPolls()[index - 1].getParty(partyNames[dataIndex]).getProjectedPercentageOfVotes());
    				}

    				// Setting the seat and vote data into their respective pie charts
    				mySeatPieChart.setData(FXCollections.observableArrayList(seatData));
    				myVotePieChart.setData(FXCollections.observableArrayList(voteData));	

    			} else {
    				
    				// Creates pie charts based on aggregate poll data if it is selected
    				PieChart.Data[] seatData = new PieChart.Data[getPollList().getPolls()[0].getNumberOfParties()];
    				PieChart.Data[] voteData = new PieChart.Data[getPollList().getPolls()[0].getNumberOfParties()];
    				
    				String[] partyNames = new String[getPollList().getPolls()[0].getNumberOfParties()];
    				
    				for (int i = 0; i < partyNames.length; i++) {
    					partyNames[i] = getPollList().getPolls()[0].getPartiesSortedBySeats()[i].getName();
    				}
    				
    				for (int dataIndex = 0; dataIndex < partyNames.length; dataIndex++) {
    					seatData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getAggregatePoll(partyNames).getParty(partyNames[dataIndex]).getProjectedNumberOfSeats());
    					voteData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getAggregatePoll(partyNames).getParty(partyNames[dataIndex]).getProjectedPercentageOfVotes());
    				}
    				
    				mySeatPieChart.setData(FXCollections.observableArrayList(seatData));
    				myVotePieChart.setData(FXCollections.observableArrayList(voteData));
    				
    			}
    		}
    	});
    	
    		}
	}
