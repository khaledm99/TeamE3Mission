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
		System.out.println("In refresh");
	
		String[] values = new String[getPollList().getPolls().length + 1];
		
		values[0] = "Aggregate Poll";
		
		for (int index = 1; index < getPollList().getPolls().length + 1; index++) {
			values[index] = getPollList().getPolls()[index - 1].getPollName();
		}
		
		// Initially Loading Aggregate Poll Pie Charts
    	myChoiceBox.setItems(FXCollections.observableArrayList(values));
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
		
		myChoiceBox.setValue("Aggregate Poll");
    	
		// Changing PieCharts if new selection is selected from choice box
    	myChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
    		@Override
    		public void changed(ObservableValue observable, Number oldValue, Number newValue) {
    			int index = newValue.intValue();
    			
    			if (index > 0) {
    				PieChart.Data[] seatData = new PieChart.Data[getPollList().getPolls()[index - 1].getNumberOfParties()]; 
    				PieChart.Data[] voteData = new PieChart.Data[getPollList().getPolls()[index - 1].getNumberOfParties()];

    				String[] partyNames = new String[getPollList().getPolls()[index - 1].getNumberOfParties()];

    				for (int i = 0; i < partyNames.length; i++) {
    					partyNames[i] = getPollList().getPolls()[index - 1].getPartiesSortedBySeats()[i].getName();
    				}
    				
    				for (int dataIndex = 0; dataIndex < partyNames.length; dataIndex++) {
    					seatData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getPolls()[index - 1].getParty(partyNames[dataIndex]).getProjectedNumberOfSeats());
    					voteData[dataIndex] = new PieChart.Data(partyNames[dataIndex], getPollList().getPolls()[index - 1].getParty(partyNames[dataIndex]).getProjectedPercentageOfVotes());
    				}

    				mySeatPieChart.setData(FXCollections.observableArrayList(seatData));
    				myVotePieChart.setData(FXCollections.observableArrayList(voteData));	
    			} else {
    				
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
