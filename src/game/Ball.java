package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import userInterface.TopPanel;

public class Ball extends GameObject{
    private Vector2D initialPos;
    private Vector2D initialVel;
    
    public Ball(Vector2D position, Vector2D velocity, int radius, ArrayList<GameObject> neighbours){
        this.initialPos = position.copy();
        this.initialVel = velocity.copy();
        shape = "circle";
        this.radius = radius;
        this.width = 2*radius;
        this.height = 2*radius;
        this.neighbours = neighbours;
        objectReset();
    }
    
    @Override
    public void update() {
    	interact();
    	container();
    	physUpdate();
    }
    
    @Override
	public void objectReset() {
    	this.position = initialPos.copy();
    	this.velocity = initialVel.copy();
    }
    
    private void interact() {
    	for(GameObject obj : neighbours) {
    		obj.objectFunction(this);
    	}
    }

    private void container(){
        if(position.getX()<=0) {
        	velocity.scaleX(-1);
            position.setX(0);
        }        	
        else if(position.getX() >= GamePanel.FRAME_WIDTH-19-2*radius){
            velocity.scaleX(-1);
            position.setX(GamePanel.FRAME_WIDTH-19-2*radius);
        }
        if(position.getY()<=0) {
        	velocity.scaleY(-1);
            position.setY(0);
        }        	
        else if(position.getY() >= GamePanel.FRAME_HEIGHT-2*radius){
            velocity.scaleY(-1);
            position.setY(GamePanel.FRAME_HEIGHT-2*radius);
            TopPanel.loseLife();
        }
    }

    private void physUpdate(){
    	velocity.add(0, TopPanel.getGravity()*TopPanel.getDeltaT());
    	if(velocity.getMag()>300) {
    		velocity.scale(200/velocity.getMag());
    	}
    	position = Vector2D.sum(position, velocity.scaled(TopPanel.getDeltaT()));
    }

    public Vector2D getInitialVelocity() {
    	return initialVel;
    }
    
    public void setInitialVelocity(Vector2D Initial) {
    	initialVel = Initial.copy();
    }
    



	@Override
	public void objectFunction(Ball ball) {
		
	}
	



	@Override
	public void paintObject(Graphics g) {
    	g.setColor(Color.RED);
		g.fillOval((int)getPosition().getX(), (int)getPosition().getY(), getWidth(), getHeight());    			
	}

	@Override
	public Rectangle getDirtyRegion() {
		Rectangle dirtyRegion;
		dirtyRegion = new Rectangle(
			(int)(getPosition().getX()-0.3*Math.abs(getVelocity().getX())),
			(int)(getPosition().getY()-0.3*Math.abs(getVelocity().getY())),
			(int)(getPosition().getX()+getWidth()+0.3*Math.abs(getVelocity().getX())),
			(int)(getPosition().getY()+getHeight()+0.3*Math.abs(getVelocity().getY())));

		return dirtyRegion;
	}
}
