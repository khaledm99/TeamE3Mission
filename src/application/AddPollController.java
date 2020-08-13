/**
 * Classname: AddPollController
 *
 * Version: 1
 * 
 * Author: Khaled Mograbee
 * 
 * Description:	Controller for AddPollController FXML. 
 * Allows user to enter a poll name and choose a poll to replace.
 */
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Factory;
import model.Poll;
import model.PollList;

public class AddPollController extends PollTrackerController{

    @FXML
    private TextField PollToAdd; // Textfield to enter poll name

    @FXML
    private ChoiceBox<String> PollPlacement; // Choice box to choose a poll to replace

    @FXML
    private Button AddButton; // Button to add poll to poll list

    @FXML
    private Button ClearButton; // Button to clear inputs

    @FXML
    private AnchorPane ToAddTab; // Anchor Pane to organize add poll tab
    
    @FXML
    private Label AddPollLabel;

    /**
     * addClick method. Takes the user input in the choice box
     * and text field and adds the poll upon clicking the add button.
     * @param event
     * @throws InvalidPartyDataException 
     */
    @FXML
    void addClick(ActionEvent event) throws InvalidPartyDataException {
    	String pollName = PollToAdd.getText();
    	PollList pollList = super.getPollList();
    	Poll[] polls = pollList.getPolls();
    	Factory factory = super.getFactory();
    	Poll poll = factory.createRandomPoll(pollName);
    	String choice = (String) PollPlacement.getValue();
    	String s = choice.substring(0,1);
    	int index = Integer.parseInt(s);
    	polls[index-1] = poll;
    	pollList.setPolls(polls);
    	super.setPollList(pollList);

    }

    @FXML
    void addParty(KeyEvent event) {
    
    }

    @FXML
    void clearClick(ActionEvent event) {
    	refresh();
    }

	/**
	 * refresh method. Overrides refresh method in parent. Clears the
	 * textfield and choicebox, as well as refreshing the choices in the choicebox.
	 */
    @Override
	public void refresh() {
		PollToAdd.clear();
		PollPlacement.getItems().clear();
		String pollName = PollToAdd.getText();
    	PollList pollList = super.getPollList();
    	Poll[] polls = pollList.getPolls();
		for (int i = 0; i < polls.length; i++) {
			PollPlacement.getItems().add(i+1 + " Replace poll " + polls[i].getPollName());
		}
		
		// TODO Auto-generated method stub
		
	}

}
