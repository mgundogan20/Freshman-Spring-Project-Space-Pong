package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import userInterface.TopPanel;

public class Paddle extends GameObject{
	private int direction = 0;
    private Vector2D initialPos;
    private Vector2D initialVel;
	private BufferedImage image;
	private boolean botActive = false;
	public Ball ball;

	public Paddle(Vector2D position, int width, int height, Ball ball){
        this.initialPos = position.copy();
        this.initialVel = new Vector2D(0,0);
        shape = "rectangle";
        this.width = width;
        this.height = height;
        objectReset();
        this.ball = ball;
        try {
        	image = ImageIO.read(new File("./Paddle.png"));
        }
        catch(IOException e) {
        	e.printStackTrace();        	
        }
    }
	
	public BufferedImage getImage() {
		return image;
	}
	
	@Override
	public void update() {
		if(botActive) {
			botDirCalc();
		}
		position.add(100*direction*TopPanel.getDeltaT(),0);
		if(this.getPosition().getX()+this.getWidth() >= GamePanel.FRAME_WIDTH && direction > 0) {
			this.getPosition().setX(GamePanel.FRAME_WIDTH-this.getWidth());
		}
		if(this.getPosition().getX() <= 0 && direction < 0) {
			this.getPosition().setX(0);
		}
	}
    @Override
	public void objectReset() {
    	this.position = initialPos.copy();
    	this.velocity = initialVel.copy();
    }

	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	public void toggleBot() {
		botActive = (botActive==true) ?false:true;
	}

	public boolean isBotActive() {
		return botActive;
	}
	
	private int botFallCalc() {
		double t = (position.getY()-ball.getPosition().getY()) / Math.abs(ball.getVelocity().getY());
		int x = (int) (t*ball.getVelocity().getX() + ball.getPosition().getX());
		if((x/GamePanel.FRAME_WIDTH) % 2 == 0) {
			return (x % GamePanel.FRAME_WIDTH) - getWidth()/2;
		}
		else {
			return GamePanel.FRAME_WIDTH -1*(x % GamePanel.FRAME_WIDTH) - getWidth()/2;
		}
	}
	private void botDirCalc() {
		double diff = botFallCalc()-position.getX();
		if(diff < 10 && diff > -10)
			setDirection(0);
		else if(diff > 0)
			setDirection(1);
		else if(diff < 0)
			setDirection(-1);
	}

	public Ball getBall() {
		return ball;
	}

	@Override
	public void objectFunction(Ball ball) {
    	Vector2D next = Vector2D.sum(ball.getCenter(), ball.getVelocity().scaled(TopPanel.getDeltaT()));
    	Vector2D leftCorner = getPosition().copy();
    	Vector2D rightCorner = Vector2D.sum(leftCorner, new Vector2D(getWidth(),0));
    	if(Collision.lineSegmentInterSection(ball.getCenter(), next, leftCorner, rightCorner)) {
    		TopPanel.incScore();
    		ball.getVelocity().scaleY(-1);
    		ball.getPosition().setY(getPosition().getY()-2*ball.getRadius());
    		ball.getVelocity().add(0,-5);
    	}
    	else if(ball.getPosition().getY() > getPosition().getY()) {
    		if(Collision.collides(this, ball)){    			
    			ball.getVelocity().scaleY(-1);
    			ball.getPosition().setY(getPosition().getY()-2*radius);
        		ball.getVelocity().add(0,-5);
    		}
    	}
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
		Rectangle dirtyRegion;
		dirtyRegion = new Rectangle(
			(int)getPosition().getX()-25,
			(int)getPosition().getY()-25,
			(int)getPosition().getX()+getWidth()+25,
			(int)getPosition().getY()+getHeight()+25);

		return dirtyRegion;
	}
}
