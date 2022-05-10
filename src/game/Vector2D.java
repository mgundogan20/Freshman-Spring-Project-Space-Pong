package game;

import java.util.Random;

public class Vector2D {
	private double x;
	private double y;
	private static Random rand = new Random();
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector2D sub(Vector2D first, Vector2D second) {
		// returns Vector1-Vector2 as a new Vector2D object
		double x = first.getX() - second.getX();
		double y= first.getY() - second.getY();
		
		return new Vector2D(x,y);
	}
	public static Vector2D sum(Vector2D first, Vector2D second) {
		// returns Vector1+Vector2 as a new Vector2D object
		double x = first.getX() + second.getX();
		double y= first.getY() + second.getY();
		
		return new Vector2D(x,y);
	}
	
	public void add(double x, double y) {
		// Adds the indicated values to the components of the caller
		this.setX(this.getX()+x);
		this.setY(this.getY()+y);
	}
	public void add(Vector2D other) {
		// Adds the indicated values to the components of the caller
		this.setX(this.getX()+other.getX());
		this.setY(this.getY()+other.getY());
	}
	
	public double getMag() {
		// Calculates the magnitude of the vector using Pythagor's Formula
		double mag = 0;
		mag += Math.pow(this.getX(),2);
		mag += Math.pow(this.getY(),2);
		mag = Math.sqrt(mag);
		
		return mag;
	}
	
	public Vector2D copy() {
		// Returns a new Vector2D that has the exact same field values with the caller
		Vector2D clone = new Vector2D(this.getX(), this.getY());
		return clone;
	}
	

	public void scaleX(double scalar) {
		// Scales the x component of the caller by multiplying it by a scalar
		this.setX(scalar*this.getX());
	}
	

	public void scaleY(double scalar) {
		//Scales the y component of the caller by multiplying it by a scalar
		this.setY(scalar*this.getY());
	}
	

	public void scale(double d) {
		//Scales the caller by multiplying both of its components by a scalar
		this.scaleX(d);
		this.scaleY(d);
	}
	
	public static double dotProduct(Vector2D frs, Vector2D sec) {
		double output = 0;
		output += frs.getX() * sec.getX();
		output += frs.getY() * sec.getY();
		return output;
	}
	
	public void normalize() {
		this.scale(1/this.getMag());
	}

	public void projectOn(Vector2D target) {
		target.normalize();
		target.scale(Vector2D.dotProduct(target, this));
	}
	
	public Vector2D scaled(double d) {
		//Returns a scaled vector
		Vector2D scaled = new Vector2D(d*this.getX(), d*this.getY());
		return scaled;
	}

	public static Vector2D distOfCenters(GameObject frs, GameObject sec) {
		// Returns the distance between the "centers" of two GameObjects
		Vector2D frsCenter = Vector2D.sum(frs.getPosition(), new Vector2D(frs.getRadius(),frs.getRadius()));
		Vector2D secCenter = Vector2D.sum(sec.getPosition(), new Vector2D(sec.getRadius(),sec.getRadius()));
		Vector2D dist = Vector2D.sub(frsCenter, secCenter);
		return dist;
	}
	
	public static Vector2D random2D(int x, int y) {
		int randX = rand.nextInt(x);
		int randY = rand.nextInt(y);
		return new Vector2D(randX, randY);
	}
	
	//*************************
	//***getters and setters***
	
	public String toString() {
		return String.format("(%f,%f)", getX(), getY());
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
