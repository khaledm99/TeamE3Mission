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




public class SetupPartyController extends PollTrackerController {
	
	private ArrayList<String> originalPartyNames = new ArrayList<String>();
	private ObservableList<String> partyNames = FXCollections.observableArrayList(); 
	
	
	//LOL PLZ LET US TALK ABOUT DISSS
	private Factory currentFactory = new Factory(345);
	private String [] factoryPartyNames = currentFactory.getPartyNames();
			
	private int size = factoryPartyNames.length;
	
	private ObservableList<String> populateObervableList (String [] defaultPartynames) {
		
		for(int i = 0; i < size; i++) {
			partyNames.add(defaultPartynames[i]);
			originalPartyNames.add(i,defaultPartynames[i]);
		}
		
		return partyNames;
		
	}
	
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

	@Override
	public void refresh() {

		
		// TODO Auto-generated method stub
		
		
	}
	
	

}
