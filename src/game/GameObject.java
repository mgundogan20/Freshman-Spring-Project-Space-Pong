package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameObject {
	protected String shape;
    protected int radius;
    protected Vector2D position;
	protected Vector2D velocity;
	protected int[] rect= new int[4];
	protected Rectangle dirtyRegion;
	
    protected ArrayList<GameObject> neighbours;

	protected int width;
	protected int height;
	
	public abstract void update();
	public abstract void objectReset();
	public abstract void objectFunction(Ball ball);
	public abstract void paintObject(Graphics g);
	public abstract Rectangle getDirtyRegion();

    public Vector2D getPosition() {
		return position;
	}
    
	public Vector2D getVelocity() {
		return velocity;
	}
	
	public String getShape() {
		return shape;
	}
	
	public int getRadius() {
		return radius;
	}


    public Vector2D getCenter() {
    	return Vector2D.sum(this.getPosition(), new Vector2D(getRadius(), getRadius()));
    }
    

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
