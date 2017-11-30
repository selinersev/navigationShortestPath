package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WindowManager extends Application{

	
	public void start(Stage primaryStage) throws Exception
	{
		drawMenu(primaryStage);		
	}

	public void drawMenu(Stage primaryStage) 
	{
		
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 400, 600);
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(3);
		
		//Add controls
		Button bShowMap = new Button("Show Map");
		GridPane.setConstraints(bShowMap,0,0);
		grid.getChildren().add(bShowMap);
		/*
		Button bSelectDest = new Button("Select Starting and End points");
		GridPane.setConstraints(bSelectDest,0,1);
		grid.getChildren().add(bSelectDest);
		
		Button bInfo = new Button("Show Details");
		GridPane.setConstraints(bInfo,0,2);
		grid.getChildren().add(bInfo);
		*/
		
		
		Label info = new Label("Information will be displayed here!");
		GridPane.setConstraints(info,0,4);
		grid.getChildren().add(info);
		//Controls added
		
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene); 
		primaryStage.setResizable(false);
		primaryStage.show(); 
		
		//Event handler setup
		 
			bShowMap.setOnAction(new EventHandler<ActionEvent>() { 
	            public void handle(ActionEvent event) {
	            
	            	LocationsAndRoads n = null;
					try {
						n = new LocationsAndRoads();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	try {
						n.genLocationsTable();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            }
	        });
			
		
			
			/*
			bInfo.setOnAction(new EventHandler<ActionEvent>() { 	
	            public void handle(ActionEvent event) {
	            	
	            	
	            }
	        });
			
			bSelectDest.setOnAction(new EventHandler<ActionEvent>() { 	
	            public void handle(ActionEvent event) {
	            
	            }
	        });
		//Event handlers set
		*/
	}
}
