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
import java.util.Random;

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
    void clearClicked(MouseEvent event) {  	
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
	public void refresh() {	
		
		PollUpdatedLabel.setOpacity(0.0);
		ProNumOfSeatsTextField.setText("");
		ProPercVotesTextField.setText("");
    	String[] pollNames = new String[getPollList().getPolls().length];
    	int i = 0;
    	while (i< getPollList().getPolls().length) {
    		pollNames[i] = getPollList().adjustPollToMaximums(getPollList().getPolls()[i]).getPollName();
    		i++;
    	}
    	
    	PollToEditDropdown.setItems(FXCollections.observableArrayList(pollNames));
    	PollToEditDropdown.getSelectionModel().selectedIndexProperty().addListener(
    			new ChangeListener<Number>() {
    				
    				@Override
    				public void changed(@SuppressWarnings("rawtypes") ObservableValue observable, Number oldValue, Number newValue) {
    					int index = newValue.intValue();
    					if (index >= 0)
    						System.out.println("Selected " + pollNames[index] + " at location " + index);
    						String[] partyNames = new String[getFactory().getPartyNames().length];
    						int i = 0;
    						while (i < getFactory().getPartyNames().length) {
    							
    							if (index > -1) {
    								partyNames[i] = getPollList().getPolls()[index].getPartiesSortedByVotes()[i] + "" ;
    						    	//System.out.println(getPollList().getPolls()[index].getPartiesSortedByVotes()[i].getProjectedPercentageOfVotes());
    						    	UpdatePartyButton.setOnAction((event) -> {
    						    		
    						   			getPollList().getPolls()[index].getPartiesSortedByVotes()[PartyToUpdateDropdown.getSelectionModel().selectedIndexProperty().intValue()].setProjectedNumberOfSeats((Integer.valueOf(ProNumOfSeatsTextField.getText())));				    		
    						    		getPollList().getPolls()[index].getPartiesSortedByVotes()[PartyToUpdateDropdown.getSelectionModel().selectedIndexProperty().intValue()].setProjectedPercentageOfVotes(Float.valueOf(ProPercVotesTextField.getText()));
    						    		refresh();
    			    					ProNumOfSeatsTextField.setText("");
    			    					ProPercVotesTextField.setText("");
    			    					Random random = new Random();
    			    					int r = random.nextInt(255);
    			    					int g = random.nextInt(255);
    			    					int b = random.nextInt(255);
    			    					Color c = Color.rgb(r,g,b); 
    			    					PollUpdatedLabel.setTextFill(c);
    						    		PollUpdatedLabel.setOpacity(1);	
    						    	});     						    		  						    	   						    	
    							}
    							i++;
    						}
    						PartyToUpdateDropdown.setItems(FXCollections.observableArrayList(partyNames));
    				}
    			}
    			);
    	}
	}
	


	