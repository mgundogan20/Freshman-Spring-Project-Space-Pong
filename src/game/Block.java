package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Block extends GameObject{
	private boolean active = true;
	private static int pic = 0;
	private BufferedImage image;

	public Block(int radius, ArrayList<GameObject> neighbours){
        this.velocity = new Vector2D(0,0);
        shape = "circle";
        this.width = 2*radius;
        this.height = 2*radius;
        this.radius = radius;
        this.neighbours = neighbours;
        objectReset();
        try {
        	switch(pic) {
        	case 0:
        		image = ImageIO.read(new File("./planetPics/0.png"));
        		break;
        	case 1:
        		image = ImageIO.read(new File("./planetPics/1.png"));
        		break;
        	case 2:
        		image = ImageIO.read(new File("./planetPics/2.png"));   
        		break;
        	case 3:
        		image = ImageIO.read(new File("./planetPics/3.png"));
        		pic = 0;
        		break;
        	}
        	pic++;
        }
        catch(IOException e) {
        	e.printStackTrace();        	
        }
        

		rect[0] = (int)getPosition().getX();
		rect[1] = (int)getPosition().getY();
		rect[2] = (int)getPosition().getX()+getWidth();
		rect[3] = (int)getPosition().getY()+getHeight();
		
		dirtyRegion = new Rectangle(rect[0],rect[1],rect[2],rect[3]);
    }
	public Block(Vector2D position, int radius, ArrayList<GameObject> neighbours){
        this.position = position.copy();
        this.velocity = new Vector2D(0,0);
        shape = "circle";
        this.width = 2*radius;
        this.height = 2*radius;
        this.radius = radius;
        this.neighbours = neighbours;
        try {
        	switch(pic) {
        	case 0:
        		image = ImageIO.read(new File("./planetPics/0.png"));
        		break;
        	case 1:
        		image = ImageIO.read(new File("./planetPics/1.png"));
        		break;
        	case 2:
        		image = ImageIO.read(new File("./planetPics/2.png"));   
        		break;
        	case 3:
        		image = ImageIO.read(new File("./planetPics/3.png"));
        		pic = 0;
        		break;
        	}
        	pic++;
        }
        catch(IOException e) {
        	e.printStackTrace();        	
        }
        

		rect[0] = (int)getPosition().getX();
		rect[1] = (int)getPosition().getY();
		rect[2] = (int)getPosition().getX()+getWidth();
		rect[3] = (int)getPosition().getY()+getHeight();
		
		dirtyRegion = new Rectangle(rect[0],rect[1],rect[2],rect[3]);
    }
	
	public BufferedImage getImage() {
		return image;
	}
	
	@Override
	public void update() {
		
	}
	public void setActive(boolean value) {
		active = value;
	}
	public boolean getActive() {
		return active;
	}
	

	@Override
	public void objectReset() {
		boolean collides;
		do {
			collides = false;
			position = Vector2D.random2D(GamePanel.FRAME_WIDTH-this.getWidth()-5, GamePanel.FRAME_HEIGHT-2*getRadius()-100);
			position.add(5, 5);
			for(GameObject obj : neighbours) {
				if(obj != this) {
					if(Collision.collides(this, obj)) {
						collides = true;
						break;
					}
				}
			}
		}while(collides);
	}
	@Override
	public void objectFunction(Ball ball) {
    	if(Collision.collides(ball, this)) {
    		if(getActive()) {    			
    		Vector2D reaction = Vector2D.distOfCenters(this, ball);
    		reaction.normalize();
    		reaction.scale(Vector2D.dotProduct(ball.getVelocity(), reaction));
    		ball.getVelocity().add(reaction.scaled(-2));
    		}
    		setActive(false);
    	}
    	else
    		setActive(true);
	}
	@Override
	public void paintObject(Graphics g) {
		image = getImage();
		g.drawImage(image,
				(int)getPosition().getX(),
				(int)getPosition().getY(),
				(int)getPosition().getX()+ getWidth(),
				(int)getPosition().getY()+ getHeight(),
				0,
				0,
				image.getWidth(),
				image.getHeight(),
				null);		
	}
	

	
	@Override
	public Rectangle getDirtyRegion() {
		return dirtyRegion;
	}
}
