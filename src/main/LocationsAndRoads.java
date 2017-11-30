package main;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class LocationsAndRoads {
	
	//Class contains and manages Location objects.
	//Also contains algorithm to draw the map
	private String CitiesTXTLocation = "cities.txt";
	private String RoadsTXTLocation = "roads.txt";
	private ArrayList<Location> LocationList = new ArrayList<>();
	private ArrayList<Road> RoadsList = new ArrayList<>();
	private double totalDistance;
	
	public LocationsAndRoads() throws IOException 
	{
		
		genLocationsTable();
		genRoadsTable();
		SetMap();
		
		
	}
	
	public void genLocationsTable() throws IOException 
	{

		try 
		{
			FileInputStream F = new FileInputStream(CitiesTXTLocation);
			BufferedReader BR = new BufferedReader(new InputStreamReader(F));
			
			String line = "";
			String name = "";
			int CordX;
			int CordY;
			
			while ((line = BR.readLine()) != null) {	//Go thorugh the lines
				//System.out.println(line);
				if (!line.startsWith("*")) {
				
				String[] explode = line.split(" "); // Explode the string for each empty space
				
				name = explode[0];
				CordX = Integer.valueOf(explode[1]);
				CordY = Integer.valueOf(explode[2]);
				
				Location a = new Location(CordX, CordY , name);
				
				LocationList.add(a);
				
				}
			}
		 
			BR.close();
		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
	
	}
	
	
	public void genRoadsTable() throws IOException 
	{
		
		try 
		{
			FileInputStream F = new FileInputStream(RoadsTXTLocation);
			BufferedReader BR = new BufferedReader(new InputStreamReader(F));
			
			String line = "";
			String city1 = "";
			String city2 = "";
			Location city1Object = null;
			Location city2Object = null;
			
			
			while ((line = BR.readLine()) != null) {	//Go thorugh the lines
				//System.out.println(line);
				if (!line.startsWith("*")) {
				String[] explode = line.split(" "); // Explode the string for each empty space
				
		
				city1 = explode[0];
				city2 = explode[1];
				
				for (Location value : LocationList) 	//Find the object city1-2 strings correspond to
				{
					
					if (value.getName().equals(city1)) 
					{
						city1Object = value;
						
					}
					
					else if (value.getName().equals(city2))
					{
						city2Object = value;
					}
					else { }
					
				}
				
				Road a = new Road( city1Object, city2Object , city1Object.getName() + "-" + city2Object.getName());
				Road b = new Road( city2Object , city1Object, city2Object.getName() + "-" + city1Object.getName());
				RoadsList.add(a);
				RoadsList.add(b);
				
				//System.out.println( a.getDistance() + " " + a.getRoadName() + " " + a.getC1().getName() + " " + a.getC2().getName());
				
				}
				
			}
		 
			BR.close();
		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	public void SetMap() 
	{
		
		int screenW = 896;
		int screenH = 472;
		
		ArrayList<Location> selectedList = new ArrayList<>();
		ArrayList<Boolean> selections = new ArrayList<>();
		selections.add(0,false);
		selections.add(1,false);
		selections.add(2,false);
		
		Group root = new Group();
    	Scene scene = new Scene(root, screenW, screenH);
     	Stage stage = new Stage();	//Create a new stage for the class
     	stage.setResizable(false);
     	
     	Image image = new Image("file:turkey.png");
     	ImageView IV = new ImageView(image);
     	IV.setSmooth(true);
     	IV.setVisible(true);
     	root.getChildren().add(IV);
     	
     	Label lab = new Label();
     	lab.setLayoutY(screenH - 50);
     	lab.setLayoutX(screenW/2);
     	lab.setText("TOTAL DISTANCE: ");
     	root.getChildren().add(lab);
     	//System.out.println(image.getHeight() + " " + image.getWidth());
     	
     	
     	//Set Locations and Place them!
     	for (Location value : LocationList) 
     	{
     		
     		Circle c = new Circle();
     		c.setRadius(3.5);
     		c.setStrokeWidth(1.2);
     		c.setCenterX(value.getX());
     		c.setCenterY(value.getY());
     		c.setVisible(true);
     		c.setStroke(Color.BLACK);
     		c.setFill(Color.RED);
     		
     		
     		Label l = new Label();
     		l.setTextFill(Color.RED);
     		Random rnd = new Random();
     
     		
     		
     		c.setOnMouseEntered(new EventHandler<MouseEvent>() {	//Event for mouse thing that does stuff
     		      public void handle(MouseEvent me) {
     		    
     		    	  l.setFont(Font.font("Verdana" , FontWeight.BOLD, 12));
     		    	  l.setTranslateX(c.getCenterX() + 10);
     		    	  l.setTranslateY(c.getCenterY() - 30);
     		    	  l.setText(value.getName() + "\nx: " + c.getCenterX() + " y: " + c.getCenterY());
     		    	  l.setTextFill(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
     		    	  root.getChildren().add(l);
     		    	  c.toFront();// this line is lazy
     		    	  
     		      }
     		    });
     		
     		 c.setOnMouseExited(new EventHandler<MouseEvent>() {	//Fix the mess upper code made
     		      public void handle(MouseEvent me) {

     		        root.getChildren().remove(l);
     		        
     		      }
     		    });
     		
     	
     		// THIS IS HOW I HANDLED THE MOUSE CLICK EVENT 3 MONTHS AGO 
     		 c.setOnMouseClicked(new EventHandler<MouseEvent>() {	//Fix the mess upper code made
    		      public void handle(MouseEvent me) {

    		   	  System.out.println("CITY " + value.getName());
    		   	
    		   	  //City selection
    		   	  
    		   	  if (!selections.get(0) & !selections.get(1))//If either the 1st or 2nd city is not selected do this
    		   	  {
    		   		  /*
    		   		for (Location item : LocationList) //reset all colours
    		   		{
    		   			item.getCircleObject().setStroke(Color.BLACK);
    		   			item.getCircleObject().setFill(Color.RED);
    		   		}	
    		   		
    		   		for (Road item : RoadsList)	//reset all colours
    		   		{
    		   			item.getLineObject().setStroke(Color.RED);
    		   			item.getLineObject().setStroke(Color.RED);
    		   		}
    		   		*/
    		   		
    		   		  selectedList.add(0,value);
    		   		  selections.add(0,true);	//1st city is now selected its index is always 0!
    		   		  selectedList.get(0).getCircleObject().setFill(Color.BLUE);
    		   	  }
    		   	  else if (selections.get(0) & !selections.get(1))//If 1st city is selected but the 2nd city is not yet selected do this
    		   	  {
    		   		   selectedList.add(1, value);
    		   		   selectedList.get(1).getCircleObject().setFill(Color.CYAN);
    		   		   selections.add(1, true);
    		   		   //TODO TODO
    		   		   //Find suitable roads
    		   		   //To find this 2 params:
    		   		   //C1 , C2
    		   		   //And need knowledge of avaible roads
    		   		   //compare coordinates of 2 cities 
    		   		   //x1 , y1 first city x2 , y2 2nd city x , y a city in between
    		   		   //if x1 < x < x2 and y1 < y < y2 ; find the closest to starting city(foreach city in the list)(if the city met required condition)(add them to a new list)(draw a line between)(use distance from a point to a line formula to find the (lowest distance from line to current city) + (distance of current city to next city))
    		   		   //Determine the roadname by equating it with a string such as "c1.name()" + "-" + "c2.name()".equals(road)
    		   		   //save that road and add it to the list
    		   		   //Change the certain Road objects Colour accordingly
    		   		   
    		   		   determineNavigation(selectedList.get(0),selectedList.get(1));	//This is where the magic happens
    		   		   lab.setText("TOTAL DISTANCE: " + Math.ceil(totalDistance/2) + " MEASUREMENT UNITS STUFF");
    		   		   if (totalDistance == 0) {lab.setText("IMPROBABLE COMMAND!!");}
    		   		   totalDistance = 0;
    		   		   
    		   		   
    		   		   
    		   		   
    		   	  } 
    		   	  else if(selections.get(0) & selections.get(1))//If both cities are selected do this
    		   	  {
    		   		System.out.println("SELECT RESET: " + selectedList.get(0).getName());
    		   		selectedList.get(0).getCircleObject().setFill(Color.RED); //Reset the Colors of old selections
    		   		selectedList.get(1).getCircleObject().setFill(Color.RED);
    		   		selections.add(0, false);
    		   		selections.add(1, false);
    		   	  
    		   	  
    		   		for (Location item : LocationList) //reset all colours
    		   		{/*
    		   			item.getCircleObject().setStroke(Color.BLACK);
    		   			item.getCircleObject().setFill(Color.RED);*/
    		   			//System.out.println("Resetting: "+item.getName());
    		   			
    		   			try 
    		   			{
    		   				item.getCircleObject().setStroke(Color.BLACK);
    		   				item.getCircleObject().setFill(Color.RED);
    		   			}
    		   			catch(java.lang.NullPointerException e) 
    		   			{
    		   				//System.out.println(e.toString());
    		   				//I AM A LAZY C AND I NEED TO FIX THIS
    		   			}
    		   			//ALSO FIX THESE COMMENTS BEFORE SOMEONE SEES THIS 
    		   			//TODO

    		   		}	
    		   		for (Road item : RoadsList)	//reset all colours
    		   		{
    		   			try 
    		   			{
    		   				item.getLineObject().setStroke(Color.RED);
    		   				item.getLineObject().setFill(Color.BLACK);
    		   			}
    		   			catch(java.lang.NullPointerException e) 
    		   			{
    		   				//System.out.println(e.toString());
    		   			}
    		   		}	   	  
    		   	  } 
    		        
    		      }
    		    });
     		 
     		value.setCircleObject(c);
    		root.getChildren().add(c);
    		
     		
     	}
     	
     	//Set Roads and Place Them!
     	// SETS ROADS AND PUSHES THEM INTO THE LIST
     	for(Road value : RoadsList) 
     	{
     		Line l = new Line();
     		l.setFill(Color.YELLOW);
     		l.setStroke(Color.RED);
     		l.setStrokeWidth(1.8);
     		
     		l.setStartX(value.getC1().getX());
     		l.setStartY(value.getC1().getY());
     		l.setEndX(value.getC2().getX());
     		l.setEndY(value.getC2().getY());
     		
     		Label la = new Label();
     		la.setTextFill(Color.RED);
     		//Random rnd = new Random();
     		
     		l.setOnMouseEntered(new EventHandler<MouseEvent>() {	//Event for mouse thing that does stuff
   		      public void handle(MouseEvent me) {
   		    
   		    	  la.setFont(Font.font("Verdana" , FontWeight.BOLD, 12));
   		    	  la.setTranslateX((value.getC1().getX() + value.getC2().getX())/2);
   		    	  la.setTranslateY((value.getC1().getY() + value.getC2().getY())/2);
   		    	  la.setText(value.getRoadName() + " Distance: " + (int)value.getDistance());
   		    	 // la.setTextFill(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
   		    	  la.setTextFill(Color.INDIANRED);
   		    	  root.getChildren().add(la);
   		    	  l.toBack(); // these lines are lazy
   		    	  IV.toBack();
   		      }
   		    });
   		
   		 l.setOnMouseExited(new EventHandler<MouseEvent>() {	//Fix the mess upper code made
   		      public void handle(MouseEvent me) {

   		        root.getChildren().remove(la);
   		        
   		      }
   		    });
   		
     		
     		l.toBack();
     		value.setLineObject(l);
     		root.getChildren().add(l);

     	}
     	
     	stage.setScene(scene);
     	stage.show();
		
	}
	   //Find suitable roads D
	   //To find this 2 params: D
	   //C1 , C2				D
	   //And need knowledge of avaible roads (RoadsList)	D
	   //compare coordinates of 2 cities 	D
	   //x1 , y1 first city x2 , y2 2nd city x , y a city in between	D
	   //if x1 < x < x2 and y1 < y < y2 ; find the closest to starting city
	   //(foreach city in the list)(if the city met required condition)(add them to a new list)(draw a line between)(use distance from a point to a line formula to find the (lowest distance from line to current city) + (distance of current city to next city))
	   //Determine the roadname by equating it with a string such as "c1.name()" + "-" + "c2.name()".equals(road)
	   //save that road and add it to the list
	   //Change the certain Road objects Colour accordingly
	   
	
	public void determineNavigation(Location c1 , Location c2) //C1 IS STARTING POINT(SOURCE) C2 IS END (FINAL) POINT
	{
		//Dijkstra method
		
		ArrayList<Location> vertices = new ArrayList<Location>();
		
		for (Location item : LocationList) 	//SETUP
		{
			item.setDistance(99999999);
			item.setPrevious(null);
			if (item.getName().equals(c1.getName())) {item.setDistance(0);}	//Set distance to 0 if its the starting point
			vertices.add(item);
		}

		while (!vertices.isEmpty()) 	//THE MAGIC
		{
			
			
			Location u = null;
			double min = 100000000;
			for(Location item : vertices) 	//Find the lowest distance valued vertex  (First one will always be the starting point)
			{
				if(item.getDistance() < min) 
				{
					u = item;
					min = item.getDistance();
				}
			}
			
			if (u.getName().equals(c2.getName())) 	//IF U IS OUR TARGET THEN WE ARE DONE! 
			{
				ArrayList<Location> LOCATIONSTHATWEAREUSINGLOL = new ArrayList<Location>();
				ArrayList<Road> ROADSTHATWEAREUSINGLOL = new ArrayList<Road>();
				ArrayList<Road> ROADSTHATWEAREACTUALLYUSINGLOLOL = new ArrayList<Road>();
				
				System.out.println("ITS OVER!");
				
				while(c2.getPrevious() != null) 	//C2 IS OUR TARGET SO WE TRACE IT BACK
				{
					LOCATIONSTHATWEAREUSINGLOL.add(c2);
					c2 = c2.getPrevious();
				}
				LOCATIONSTHATWEAREUSINGLOL.add(c2);
				
				
				for (Location item : LOCATIONSTHATWEAREUSINGLOL)
				{
					for (Road r : RoadsList) 
					{
						if(item.getName().equals(r.getC1().getName()))
						{
							
							ROADSTHATWEAREUSINGLOL.add(r);
							
						}
					}
				}
				
				for (Location item : LOCATIONSTHATWEAREUSINGLOL) 	
				{
					for (Road r : ROADSTHATWEAREUSINGLOL) 
					{
						if(item.getName().equals(r.getC2().getName()))
						{
							ROADSTHATWEAREACTUALLYUSINGLOLOL.add(r);
						}
					}
				}
				
				
				
				for (Location item : LOCATIONSTHATWEAREUSINGLOL) 
				{
					item.getCircleObject().setStroke(Color.GOLD);
					item.getCircleObject().setFill(Color.SILVER);
					
				}
				for (Road item : ROADSTHATWEAREACTUALLYUSINGLOLOL) 
				{
					item.getLineObject().setStroke(Color.GOLD);
					item.getLineObject().setFill(Color.SILVER);
					this.totalDistance += item.getDistance();
				}
				
				
			}
			
			vertices.remove(u);	//Remove the object with lowest distance value!
			
			//Find neighboring Locations(Ones that has roads in between)
			ArrayList<Location> neighbors = new ArrayList<Location>();
			for (Road r : RoadsList) 
			{
				if(r.getC1().getName().equals(u.getName())) //If any of the cities this road connects to has the same name as our current city(u)
				{	
					//r.getLineObject().setStroke(Color.GOLD);
					//System.out.println(r.getRoadName());
					neighbors.add(r.getC2());	//Add the neighboring city to the list
				}	
			}
		
			for (Location item : neighbors) //For each neighbouring (ITEM) location to our u
			{
				double alt = u.getDistance() + u.getLength(item); 
				if (alt < item.getDistance()) 
				{
					item.setDistance(alt);
					item.setPrevious(u);
					//System.out.println(u.getName());
				}
			}

		}
		
		
	
		/*
		Location CurrentLocation = c1;
		
		//LocationsList
		//RoadsList
		
		//Find the location that is closest to end point but still has connection to last point we are looking at (current location)
		
		for (Road value : RoadsList) 
		{
			Location tempLocation = null;
			double lastDistance = 9999999;
			
			if (value.getC1().equals(CurrentLocation)) 
			{


				double currentDistance = Math.sqrt( Math.pow((c2.getX() - value.getC2().getX()),2) + Math.pow((c2.getY() - value.getC2().getY()),2) );
				
				if ( currentDistance  < lastDistance)
				{
					lastDistance = currentDistance;
					tempLocation = value.getC2();
					System.out.println(value.getC1().getName() + " " + CurrentLocation.getName() +" "+ tempLocation.getName());
					CurrentLocation = tempLocation;
					
				}
				
			}
		}
		
		
		System.out.println("te " + CurrentLocation.getName());
		
		
		*/
		
		
		
		
		
		
		
		
		
		/*
		ArrayList<Road> workableRoads = new ArrayList<>();
		ArrayList<Location> workableLocations = new ArrayList<>();
		ArrayList<Road> selectedRoads = new ArrayList<>();	//This list will contain end results
		
		for (Road value : RoadsList) {

			if ( (value.getC1().getX() > c1.getX()  &  c1.getX() > value.getC2().getX()) | (value.getC1().getX() < c1.getX() &  c1.getX() < value.getC2().getX())) 
			{	System.out.println("SHIEEEET" + value.getRoadName());
				workableRoads.add(value);	//We did this to decrease the workload.
			}
		}
		
		for (Location value : LocationList) 
		{
			
			if ( (value.getX() < c1.getX() &  c1.getX() < value.getX()) | (value.getX() > c1.getX() &  c1.getX() > value.getX())) 
			{
				workableLocations.add(value);	//We did this to decrease the workload.
			}
			
		}
		
		double Distance = 2139999999;	//Arbitrarily high number 
		Location previousLocation = c1;	//At start we set the previous location as the starting position
		Road currentRoad = null;
		
		
		for (Location value : workableLocations)
		{   //If we say that there is an straight logical line between starting Location and end location
			//We can take distance of each Location to this line and distance each Location has between the next one
			//We can find the shortest way
			//TODO
			//Distance to a line from a point. (line is the logical straight line between 2 cities, point is the city we are looking at (value) )
			
			for (Road roadValue : workableRoads) 
			{
				
				if (roadValue.getRoadName().equals( previousLocation.getName() + "-" + value.getName())) 
				{
					
					currentRoad = roadValue;
					currentRoad.getLineObject().setStroke(Color.GREEN);
					selectedRoads.add(currentRoad);
					
				}
				
			}
			
			
			double calculatedDistanceToImaginationLine = ( Math.abs( ( ( c2.getY() - c1.getY() ) * value.getX() ) - ( (  c2.getX() - c1.getX() ) * value.getY() ) + ( c2.getX() * c1.getY() ) -( c2.getY() * c1.getX() ) ) ) / Math.sqrt( ( ( c2.getY() - c1.getY() ) * ( c2.getY() - c1.getY() ) ) + ( ( c2.getX() - c1.getX() ) * ( c2.getX() - c1.getX() ) ));
			double tempDistance = calculatedDistanceToImaginationLine  + currentRoad.getDistance();
			
			if (tempDistance < Distance)
			{
				Distance = tempDistance;
				
			}
			
		}
		
		*/
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	
	
	
}