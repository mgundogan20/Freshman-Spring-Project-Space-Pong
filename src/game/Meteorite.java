package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Meteorite extends GameObject {
	private boolean active = true;
    private int imageIx = 0;
	private BufferedImage[] images = new BufferedImage[4];
	private BufferedImage image;
	
	public Meteorite(int radius, ArrayList<GameObject> neighbours){
        this.velocity = new Vector2D(0,0);
        shape = "circle";
        this.width = 2*radius;
        this.height = 2*radius;
        this.radius = radius;
        this.neighbours = neighbours;
        objectReset();
        try {
        	images[0] = ImageIO.read(new File("./metePics/0.png"));
        	images[1] = ImageIO.read(new File("./metePics/1.png"));
        	images[2] = ImageIO.read(new File("./metePics/2.png"));
        	images[3] = images[0];
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
	public Meteorite(Vector2D position, int radius, ArrayList<GameObject> neighbours) {
		this.position = position.copy();
		this.velocity = new Vector2D(0,0);
		shape = "circle";
		this.radius = radius;
		this.width = 2*radius;
		this.height = 2*radius;
        this.neighbours = neighbours;
        try {
        	images[0] = ImageIO.read(new File("./metePics/0.png"));
        	images[1] = ImageIO.read(new File("./metePics/1.png"));
        	images[2] = ImageIO.read(new File("./metePics/2.png"));
        	images[3] = images[0];
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
		return images[imageIx++/4%4];
	}
	
	@Override
	public void update() {
		
	}
	
	private float accRate() {
		if(active) {
			active = false;
			return 1.5f;
		}
		else
			return 1;
	}
	
	private void activate() {
		active = true;
	}

	@Override
	public void objectReset() {
		boolean collides;
		do {
			collides = false;
			position = Vector2D.random2D(GamePanel.FRAME_WIDTH-this.getWidth()-20, GamePanel.FRAME_HEIGHT-100);
			position.add(5, 0);
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
    	if(Collision.collidesCircle(this, ball)) {
    		ball.velocity.scale(accRate());
    	}
    	else
    		activate();
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
