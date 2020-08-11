package application;

import javafx.beans.value.ChangeListener;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.Party;

import java.util.Random;
/**
 *EditPollController class for the pollTrackerApp
 *for Assignment 2 CPSC 233
 *@author Xavier Lewis
 * imported neccesary classes above
 */

public class EditPollController extends PollTrackerController{

    @FXML
    private BorderPane ToEditTab;
    
    @FXML
    private TextField ProPercVotesTextField;

    @FXML
    private ChoiceBox<String> PollToEditDropdown;
    
    @FXML
    void pollDropdownClicked(MouseEvent event) { 
    }

    @FXML
    private TextField ProNumOfSeatsTextField;

    @FXML
    private Button ClearButton;

    @FXML
    private ChoiceBox<String> PartyToUpdateDropdown;

    @FXML
    private Button UpdatePartyButton;
    
    @FXML
    private Label PollUpdatedLabel;
    
    @FXML
    private Label errorLabel;
    
    
    /**
     * Above are private instances created in the Scene Builder
     * @param event On Mouseclicked a empty list is created to replace the dropdown display list
     * The rest of the fields are refreshed
     */
    @FXML
    void clearClicked(MouseEvent event) {  	
    	String [] emptyList = new String[0];
		PartyToUpdateDropdown.setItems(FXCollections.observableArrayList(emptyList));
		PollUpdatedLabel.setText("Party(ies) Updated:");
    	refresh();
    }
   
    @FXML
    void updatePartyClicked(MouseEvent event) {
    }
   
    @FXML
    void initialize() {
        assert ProPercVotesTextField != null : "fx:id=\"ProPercVotesTextField\" was not injected: check your FXML file 'EditPollView.fxml'.";
        assert PollToEditDropdown != null : "fx:id=\"PollToEditDropdown\" was not injected: check your FXML file 'EditPollView.fxml'.";
        assert ProNumOfSeatsTextField != null : "fx:id=\"ProNumOfSeatsTextField\" was not injected: check your FXML file 'EditPollView.fxml'.";
        assert ClearButton != null : "fx:id=\"ClearButton\" was not injected: check your FXML file 'EditPollView.fxml'.";
        assert PartyToUpdateDropdown != null : "fx:id=\"PartyToUpdateDropdown\" was not injected: check your FXML file 'EditPollView.fxml'.";
        assert UpdatePartyButton != null : "fx:id=\"UpdatePartyButton\" was not injected: check your FXML file 'EditPollView.fxml'.";
	
    }

	@Override
	/**
	 * Refresh() abstract method called after initialize and runs after Update button is clicked
	 * or clear is clicked, or when switching between tabs
	 */
	public void refresh() {	
		
		PollUpdatedLabel.setOpacity(0.0);
		ProNumOfSeatsTextField.setText("");
		ProPercVotesTextField.setText("");
		/**
		 * pollNames[] is set as the dropdown menu for the PollToUpdate choicebox 
		 */
    	String[] pollNames = new String[getPollList().getPolls().length];
    	int i = 0;
    	while (i< getPollList().getPolls().length) {
    		
    		pollNames[i] = getPollList().adjustPollToMaximums(getPollList().getPolls()[i]).getPollName();
    		i++;
    	}
    	
    	PollToEditDropdown.setItems(FXCollections.observableArrayList(pollNames));
    	/**
    	 * Listener added to monitor which poll is selected and if that selection changes 
    	 * in order to update the parties available to update. This code will look very similar to the lecture demo example 
    	 * and functions similarly
    	 */
    	PollToEditDropdown.getSelectionModel().selectedIndexProperty().addListener(
    			new ChangeListener<Number>() {
    				
    				@Override
    				public void changed(@SuppressWarnings("rawtypes") ObservableValue observable, Number oldValue, Number newValue) {
    					int index = newValue.intValue();
    					if (index >= 0) {
    						
    						String[] partyNames = new String[getFactory().getPartyNames().length];
    						int i = 0;
    						while (i < getFactory().getPartyNames().length) {
    							
    							if (index > -1) {
    								
    								partyNames[i] = getPollList().getPolls()[index].getPartiesSortedByVotes()[i] + "" ;
    						    	//System.out.println(getPollList().getPolls()[index].getPartiesSortedByVotes()[i].getProjectedPercentageOfVotes());
    						    	/**
    						    	 * On mouseClicked on the Update button, the current poll is accessed, the parties are accessed sorted by votes
    						    	 * Whichever index the selected party is at has it's seats set first, then votes as the parties are accessed by order of votes for each
    						    	 * run of the setOnAction.
    						    	 */
    						    	UpdatePartyButton.setOnAction((event) -> {
    						    		/**
    						    		 * 
    						    		 */
    						    		
    						   			try {
											getPollList().getPolls()[index].getPartiesSortedByVotes()[PartyToUpdateDropdown.getSelectionModel()
											    .selectedIndexProperty().intValue()].setProjectedNumberOfSeats((Integer.valueOf(ProNumOfSeatsTextField.getText())));
											errorLabel.setText("");
										} catch (InvalidPartyDataException e) {
											// TODO Auto-generated catch block
											errorLabel.setText(e.getMessage());
											
										}	
    						   			
    						   			
    						    		Party selectedParty = getPollList().getPolls()[index].getPartiesSortedByVotes()[PartyToUpdateDropdown.getSelectionModel().selectedIndexProperty().intValue()];
    						   			float percVotes = Float.valueOf(ProPercVotesTextField.getText());
    						   			if (percVotes > 0 && percVotes <= 100){
    						   				
    						   				percVotes = percVotes/100;
    						   			}
    						   			
    						    		try {
											getPollList().getPolls()[index].getPartiesSortedByVotes()[PartyToUpdateDropdown.getSelectionModel().selectedIndexProperty().intValue()].setProjectedPercentageOfVotes(percVotes);
											errorLabel.setText("");
										} catch (InvalidPartyDataException e) {
											// TODO Auto-generated catch block
											errorLabel.setText(e.getMessage());
										}  						    	
    						    		
    						    		
    						    		
    						    		refresh();
    			    				 	/**
    			    				 	 * After the seats and votes are set in the poll list the controller runs refresh() and clears all fields and choiceBoxes
    			    				 	 */
    						    		ProNumOfSeatsTextField.clear();
    			    					ProPercVotesTextField.clear();
    			    					String [] emptyList = new String[0];
    			    					PartyToUpdateDropdown.setItems(FXCollections.observableArrayList(emptyList));
    			    					/**
    			    					 * To improve user interface readability (and for the bonus) each time a party is updated 
    			    					 * the party with it's new vote and seat values is displayed at the top of the window, in a random
    			    					 * colour each time to indicate a new party has been updated. The label is made visible until clear or refresh is called.
    			    					 */
    			    					Random random = new Random();
    			    					int r = random.nextInt(255);
    			    					int g = random.nextInt(255);
    			    					int b = random.nextInt(255);
    			    					Color c = Color.rgb(r,g,b); 
        			    				PollUpdatedLabel.setText(PollUpdatedLabel.getText() + " " + selectedParty.getName() + "("
        			    				+ (int)(selectedParty.getProjectedPercentageOfVotes()*100)  + "%, " + (int) selectedParty.getProjectedNumberOfSeats() + " Seats) ");    			    				    			    					
    			    					PollUpdatedLabel.setTextFill(c);
    						    		PollUpdatedLabel.setOpacity(1);	
    						    	});     
    						    	/*
    						    	 * this marks the end of the actionEvent of UpdateParty being clicked
    						    	 */
    							}
    							
    							i++;
    						}
    						
    						PartyToUpdateDropdown.setItems(FXCollections.observableArrayList(partyNames));
    					}
    				}
    			}
    			);
    	}
	}
	


	