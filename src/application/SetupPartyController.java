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
	

	
	private String [] factoryPartyNames =  getFactory().getPartyNames();
	
	
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
    void ClearClicked(ActionEvent event) {
    	
    	// for loop to reset the combobox to the original party names
    	for(int i=0; i<partyNames.size(); i++) {
    		
    			partyNames.set(i, originalPartyNames.get(i));	
    			
    	}
    	
    	PartyNameComboBox.setItems(partyNames);	
    
    }
    		
   
    @FXML
    void SetPartyClicked(ActionEvent event) {
    	int i;
    	String temp = PartyNameTextField.getText();
       	String itemSelected = PartyNameComboBox.getValue();
    	if(!(temp == "")) {
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
    void SubmitPartyInfoClicked(ActionEvent event) {
    	String [] finalPartyNames = new String [size];
    	for (int i = 0; i < size; i++) {
    		finalPartyNames[i] = partyNames.get(i);
    	}
    	
    	getFactory().setPartyNames(finalPartyNames);
    	
    	
    }

    @FXML
    void PartyNameTyped(ActionEvent event) {

    }

	ObservableList<String> controllerGetPartyNames() {
		return partyNames;
	}

	void controllerSetPartyNames(ObservableList<String> partyNames) {
		this.partyNames = partyNames;
	}
	
	
	@FXML
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
		
		//for loop to add party names to the observable list as well the arraylist which holds the original party names
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
