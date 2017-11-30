package main;

import javafx.scene.shape.Circle;

public class Location {

	//Location class that is used to transfer information about a location more easily 
	
	private String Name;
	private int x;
	private int y;
	private Circle CircleObject;
	
	private double distance;	//These 2 values are used to calculate navigation in dijkstra metod
	private Location previous;
	
	
	public Location(int x , int y , String Name, Circle CircleObject) 
	{
		this.x = x;
		this.y = y;
		this.Name = Name;
		this.setCircleObject(CircleObject);
	}
	
	public Location(int x , int y , String Name) 
	{
		this.x = x;
		this.y = y;
		this.Name = Name;

	}
	
	public Location() {}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	} 
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public Circle getCircleObject() {
		return CircleObject;
	}

	public void setCircleObject(Circle circleObject) {
		CircleObject = circleObject;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Location getPrevious() {
		return previous;
	}

	public void setPrevious(Location previous) {
		this.previous = previous;
	}
	
	public double getLength(Location a) 
	{
		return Math.sqrt((Math.pow((this.getX() - a.getX()),2) + Math.pow((this.getY() - a.getY()),2)));
	}
	
}
