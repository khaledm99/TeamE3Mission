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
	
	 
	private ObservableList<String> partyNames = FXCollections.observableArrayList(); 
	
	private String[] originalPartyNames;

	
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
    			partyNames.set(i, originalPartyNames[i]);
    			
    	}
    	
    	PartyNameComboBox.setItems(partyNames);	
    
    }
    		
   
    @FXML
    void SetPartyClicked(ActionEvent event) {
    	int i;
    	String temp = PartyNameTextField.getText();
       	String itemSelected = PartyNameComboBox.getValue();
    	
    	for (String name : partyNames) {
    		if(name == itemSelected) {
    			i = partyNames.indexOf(itemSelected);
    			partyNames.set(i, temp);
    		}
    	}
    	
    	PartyNameTextField.clear();
     }
    	   
    
    @FXML
    void SubmitPartyInfoClicked(ActionEvent event) {

    }

    @FXML
    void PartyNameTyped(ActionEvent event) {

    }

	ObservableList<String> getPartyNames() {
		return partyNames;
	}

	void setPartyNames(ObservableList<String> partyNames) {
		this.partyNames = partyNames;
	}
	
	
	@FXML
	void initialize() {
		
		//Factory currentFactory = super.getFactory();
				
		//String [] currentPartyNames = currentFactory.getPartyNames();
		//String [] test = {"one", "two", "three", "four", "five"};
		
		
		//1#For loop to add parties to partyNames observable
		//for(int i = 0; i < test.length; i++) {
		//	partyNames.add(test[i]);
		//}
		
		//originalPartyNames = test.clone()
	
		
		
		//PartyNameComboBox.setItems(partyNames);
		
	}

	@Override
	public void refresh() {
		String[] partyNameList = new  String [getFactory().getPartyNames().length];
		for(int i = 0; i < getFactory().getPartyNames().length; i++) {
			
			partyNameList[i] = getPollList().getPolls()[0].getPartiesSortedByVotes()[i] + "";
			System.out.println(partyNameList[i]);
		}
		// TODO Auto-generated method stub
		
	}
	

	
	
	

}
