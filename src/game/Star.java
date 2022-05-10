package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import userInterface.TopPanel;

public class Star extends GameObject {
	private boolean active = true;
    private int imageIx = 0;
	private BufferedImage[] images = new BufferedImage[7];
	private BufferedImage image;
	private int[] animationSteps = {0,0,0,0,0,0,0,0,0,0,1,2,3,4,5};
	
	public Star(int radius, ArrayList<GameObject> neighbours){
        this.velocity = new Vector2D(0,0);
        shape = "circle";
        this.width = 2*radius;
        this.height = 2*radius;
        this.radius = radius;
        this.neighbours = neighbours;
        objectReset();
        try {
        	images[0] = ImageIO.read(new File("./starPics/star coin rotate 1.png"));
        	images[1] = ImageIO.read(new File("./starPics/star coin rotate 2.png"));
        	images[2] = ImageIO.read(new File("./starPics/star coin rotate 3.png"));
        	images[3] = ImageIO.read(new File("./starPics/star coin rotate 4.png"));
        	images[4] = ImageIO.read(new File("./starPics/star coin rotate 5.png"));
        	images[5] = ImageIO.read(new File("./starPics/star coin rotate 6.png"));
        	images[6] = ImageIO.read(new File("./starPics/star blink.png"));
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
	
	public Star(Vector2D position, int radius, ArrayList<GameObject> neighbours) {
		this.position = position.copy();
		this.velocity = new Vector2D(0,0);
		shape = "circle";
		this.radius = radius;
		this.width = 2*radius;
		this.height = 2*radius;
        this.neighbours = neighbours;
        try {
        	images[0] = ImageIO.read(new File("./starPics/star coin 1.png"));
        	images[1] = ImageIO.read(new File("./starPics/star coin 2.png"));
        	images[2] = ImageIO.read(new File("./starPics/star coin 3.png"));
        	images[3] = ImageIO.read(new File("./starPics/star coin 4.png"));
        	images[4] = ImageIO.read(new File("./starPics/star coin 5.png"));
        	images[5] = ImageIO.read(new File("./starPics/star coin 6.png"));
        	images[6] = ImageIO.read(new File("./starPics/star blink.png"));
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
		if(active)
			return images[animationSteps[imageIx++/4%15]];
		else
			return images[6];
	}
	
	@Override
	public void update() {
		
	}
	
	private void bonus() {
		if(active) {
			active = false;
			TopPanel.setScore(TopPanel.getScore()+10);;
		}
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
    		bonus();
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
