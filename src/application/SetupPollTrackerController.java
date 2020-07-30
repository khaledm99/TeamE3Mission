package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.PollList;
import model.Factory;
import model.Poll;

public class SetupPollTrackerController extends PollTrackerController{

    @FXML
    private TextField numOfSeatsEntry;

    @FXML
    private TextField numOfPartiesEntry;

    @FXML
    private Button submitButton;
    	
    @FXML
    private Button clearButton;
  
    @FXML
    private TextField numOfPollsEntry;

    @FXML
    void clearEntries(ActionEvent event) {
    	numOfSeatsEntry.clear();
    	numOfPartiesEntry.clear();
    	numOfPollsEntry.clear();
    }

    @FXML
    void submitEntries(ActionEvent event) {
    	int numPolls = Integer.parseInt(numOfPollsEntry.getText());
    	int numParties = Integer.parseInt(numOfPartiesEntry.getText());
    	int numSeats = Integer.parseInt(numOfSeatsEntry.getText());
    	
    	PollList polllist = new PollList(numPolls, numSeats);
    	int nameCounter = 1;
    	for (int i = 0; i < numPolls; i++) {
    		Poll poll = new Poll("Poll" + nameCounter, numSeats);
    		polllist.addPoll(poll);
    		nameCounter++;
    	}
    	
    	setPollList(polllist);
    	
    	Factory factory = new Factory(numSeats);
    	
    	String[] parties = new String[numParties];
    	
    	for (int i = 0; i < parties.length; i++) {
    		parties[i] = String.valueOf(i + 1);
    	}
    	
    	factory.setPartyNames(parties);
    	setFactory(factory);
    }

	@Override
	public void refresh() {
		numOfSeatsEntry.clear();
    	numOfPartiesEntry.clear();
    	numOfPollsEntry.clear();
	}

}
