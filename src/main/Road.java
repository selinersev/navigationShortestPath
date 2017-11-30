package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Road {
	//Manages road object for easy access
	private String roadName;
	private Location c1;
	private Location c2;
	private Line LineObject;
//	private Color DefaultColor;
	
	public Road(Location c1 , Location c2 , String roadName)
	{
		
		this.c1 = c1;
		this.c2 = c2;
		this.roadName = roadName;
		
	}
	
	public double getDistance() {return Math.sqrt((Math.pow((c1.getX() - c2.getX()),2) + Math.pow((c1.getY() - c2.getY()),2)));}
	public Location getC1() {return c1;}
	public Location getC2() {return c2;}
	
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public Line getLineObject() {
		return LineObject;
	}

	public void setLineObject(Line lineObject) {
		LineObject = lineObject;
	}
	
}
