package application;
	
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import model.Factory;
import model.InvalidSetupDataException;
import model.PollList;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;


public class PollTrackerApp extends Application {
	public static final int DEFAULT_NUMBER_OF_SEATS = 345;
	public static final String FXML_FILES_LOCATION = "src/view/";
	public static final int DEFAULT_NUMBER_OF_POLLS = 5;
	
	private PollList polls;
	private Factory factory = new Factory(DEFAULT_NUMBER_OF_SEATS);
	
	public PollList getPolls() {
		return polls;
	}
	
	public void setPolls(PollList aList) {
		polls = aList;
	}
	
	public Factory getFactory() {
		return factory;
	}
	
	public void setFactory(Factory aFactory) {
		factory = aFactory;
	}
	
	private Tab createTab(String tabName, String FXMLFilename) {
		Tab aTab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			aTab = new Tab(tabName, loader.load(new FileInputStream(FXMLFilename)));
			PollTrackerController controller = (PollTrackerController)loader.getController();
			aTab.setOnSelectionChanged (e -> controller.refresh());
			controller.setPollTrackerApp(this);
		} catch (IOException e1) {
			System.out.println("Problem loading FXML file " + FXMLFilename);
		}
		return aTab;
	}

	// Remove this method if you use your own visualization.	
	private void updateVisualization(TextArea vizualizationTextArea) {
		ByteArrayOutputStream visualization = new ByteArrayOutputStream();
		PrintStream visualizationStream = new PrintStream(visualization);
		PrintStream old = System.out;
		System.setOut(visualizationStream);
		(new TextApplication(polls)).displayPollsBySeat(factory.getPartyNames());
		System.out.flush();
		System.setOut(old);
		visualizationStream.close();
		
		StringBuilder partyNames = new StringBuilder();
		partyNames.append("Party names: ");
		for (String name : factory.getPartyNames()) {
			partyNames.append(name);
			partyNames.append(", ");
		}
		partyNames.append("\n");
		
		String numOfSeats = "Number of seats: " + polls.getNumOfSeats() + "\n";
		
		String numOfPolls = "Number of polls: " + polls.getPolls().length + "\n";
		
		vizualizationTextArea.setText(partyNames + numOfSeats + numOfPolls + visualization.toString());		
	}
	
	// Remove this method if you use your own visualization.	
	private Tab getDefaultVisualization() {
		// Create a stream to hold the output
		TextArea vizTextArea = new TextArea();
		updateVisualization(vizTextArea);
		Tab vizTab = new Tab("Visualize Polls", vizTextArea); 
		vizTab.setOnSelectionChanged (e -> updateVisualization(vizTextArea));
		return vizTab; 
	}
	
	@Override
	public void start(Stage primaryStage) {
	
		try {
			polls = factory.createRandomPollList(DEFAULT_NUMBER_OF_POLLS);
		} catch (InvalidSetupDataException e) {
			e.printStackTrace();
		}
		//polls = new PollList(DEFAULT_NUMBER_OF_POLLS, DEFAULT_NUMBER_OF_SEATS);
			
		TabPane root = new TabPane(
				createTab("Setup Poll Tracker", FXML_FILES_LOCATION + "SetupPollTrackerView.fxml"),
				createTab("Setup Parties", FXML_FILES_LOCATION + "SetupPartiesView.fxml"),
				createTab("Add Poll", FXML_FILES_LOCATION + "AddPollView.fxml"),
				createTab("Edit Poll", FXML_FILES_LOCATION + "EditPollView.fxml"),
				createTab("Visualize Poll", FXML_FILES_LOCATION + "VisualizePollView.fxml")
				//getDefaultVisualization()
									);
		
		Scene scene = new Scene(root,500,400);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
