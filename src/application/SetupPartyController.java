package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import model.Factory;



/**
 * Classname: SetupPartyController
 *
 * Version: 0
 * 
 * Author: Homing Wat
 * 
 * Description:	This class is the controller class of the "Setup Party" tab of the GUI. It contains action handlers for
 * 				all buttons. There are five FXML controls for which 3 of them are buttons, one of them is a combobox and 
 * 				the last controls is a textfield.The controller class functions by allowing the user to set/modify the 
 * 				party names of the parties. 
 */


public class SetupPartyController extends PollTrackerController {
	
	private ArrayList<String> originalPartyNames = new ArrayList<String>();
	private ObservableList<String> partyNames = FXCollections.observableArrayList(); 
	private Factory currentFactory = new Factory(345);
	private String [] factoryPartyNames = currentFactory.getPartyNames();	
	private int size = factoryPartyNames.length;
	
    @FXML
    private Button SubmitPartyInfo;

    @FXML
    private Button ClearButton;

    @FXML
    private Button SetPartyInfoButton;

    @FXML
    private ComboBox <String> PartyNameComboBox;

    @FXML
    private TextArea PartyNameTextField;

    @FXML
    
    /**
     * ClearClicked action handler. This action handler replaces the party names set in the combobox
     * with the original party names of the factory, thereby removing the users edits and reseting the 
     * combobox to its original state.
     * @param event
     */
    void ClearClicked(ActionEvent event) {
 
    	for(int i=0; i<partyNames.size(); i++) {
    			partyNames.set(i, originalPartyNames.get(i));
    			
    	}
    	
    	PartyNameComboBox.setItems(partyNames);	
    
    }
    		
   
    @FXML
    /**
     * SetPartyClicked action handler. This action handler replaces the party name selected in the combobox with
     * the party name entered in the text field.
     * @param event
     */
    void SetPartyClicked(ActionEvent event) {
    	
    	int i;
    	
    	String temp = PartyNameTextField.getText();
       	
    	String itemSelected = PartyNameComboBox.getValue();
    	
       	if(temp == "") 
    		return;
    	
    	else {
    			for (String name : partyNames) {
    				if(name == itemSelected) {
    					i = partyNames.indexOf(itemSelected);
    					partyNames.set(i, temp);
    				}
    			}
    		}
    	
    	PartyNameTextField.clear();
     }
    	   
    
    @FXML
    /**
     * SubmitPartyInfoClicked action handler. This action handler takes the strings found in the observable list
     * and adds them to a string array which is fed back to the factory object to set the party names. 
     * @param event
     */
    void SubmitPartyInfoClicked(ActionEvent event) {
    	
    	String [] finalPartyNames = new String [size];
    	
    	for (int i = 0; i < size; i++) {
    		finalPartyNames[i] = partyNames.get(i);
    	}
    	
    	getFactory().setPartyNames(finalPartyNames);
    	
    	
    }
	
	@FXML
	/**
	 * initialize() method which initializes the tab view with default factory party names
	 * by using the populateObersvableList method to populate the observable list before 
	 * assigning it to the combobox.  
	 */
	void initialize() {
		
		partyNames = populateObervableList(factoryPartyNames);
		
		PartyNameComboBox.setItems(partyNames);
	}

	/**
	 * populateObersvableList method. This method takes a string array of party names and
	 * adds them to an empty observable list which it then returns. 
	 * @param defaultPartynames
	 * @return partyNames an observable list filled with party name strings
	 */
	private ObservableList<String> populateObervableList (String [] defaultPartynames) {
		
		for(int i = 0; i < size; i++) {
			partyNames.add(defaultPartynames[i]);
			originalPartyNames.add(i,defaultPartynames[i]);
		}
		
		return partyNames;
		
	}
	
	@Override
	public void refresh() {

		
		// TODO Auto-generated method stub
		
		
	}
	
	

}
