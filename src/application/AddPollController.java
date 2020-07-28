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
    private TextField PollToAdd;

    @FXML
    private ChoiceBox PollPlacement;

    @FXML
    private Button AddButton;

    @FXML
    private Button ClearButton;

    @FXML
    private AnchorPane ToAddTab;
    
    @FXML
    private Label AddPartyLabel;

    @FXML
    void addClick(ActionEvent event) {
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
