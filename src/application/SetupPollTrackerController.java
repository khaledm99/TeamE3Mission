package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.PollList;
import model.PollListFullException;
import model.Factory;
import model.Poll;

/**
 * Public Class: SetupPollTrackerController
 * This class acts as the controller for the related
 * SetupPollTrackerView fxml file. When data is entered into the
 * text fields and the buttons are pressed, the fields will either
 * be cleared or be inputted into the PollList and Factory objects
 * in the polltrackerapp class.
 * @author colec
 *
 */
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
    private Label ErrorLabel0;

    /**
     * public method clearEntries
     * This simple method is triggered by the clearButton control
     * when the button is pressed, the refresh method is called, wiping
     * the entered data from the text fields
     * @param event
     */
    @FXML
    public void clearEntries(ActionEvent event) {
    	refresh();
    }

    
    /**
     * public method submitEntries
     * This method takes the entries inputted into the textfields and
     * sets them as the values of a new blank poll list and factory.
     * The objects in polltrackerapp are then set to these new instances.
     * @param event
     */
    @FXML
    public void submitEntries(ActionEvent event) {
    	int numPolls = Integer.parseInt(numOfPollsEntry.getText());
    	int numParties = Integer.parseInt(numOfPartiesEntry.getText());
    	int numSeats = Integer.parseInt(numOfSeatsEntry.getText());
    	
    	PollList polllist = new PollList(numPolls, numSeats);
    	int nameCounter = 1;
    	for (int i = 0; i < numPolls; i++) {
    		Poll poll = new Poll("Poll" + nameCounter, numSeats);
    		try {
				polllist.addPoll(poll);
			} catch (PollListFullException e) {
				e.printStackTrace();
				ErrorLabel0.setText("Error: PollList is full. Cannot add to PollList.");
			}
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

    /**
     * public override method refresh
     * this function simply clears the text fields
     * in the display window and allows the user to enter new values.
     */
	@Override
	public void refresh() {
		numOfSeatsEntry.clear();
    	numOfPartiesEntry.clear();
    	numOfPollsEntry.clear();
    	ErrorLabel0.setText("");
	}

}
