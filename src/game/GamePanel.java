package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage failImage = null;

    ArrayList<GameObject> gameObjects = new ArrayList<>();
    
    public static int FRAME_WIDTH;
    public static int FRAME_HEIGHT;
    
    
    
    public GamePanel(int frameWidth, int frameHeight){
        FRAME_WIDTH = frameWidth;
        FRAME_HEIGHT = frameHeight;

        this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        
    }
    
    public void gameReset() {
    	for(GameObject obj : gameObjects) {
    		obj.objectReset();
    	}
    	repaint();
    }
    
    public void addGameObject(GameObject object) {
    	gameObjects.add(object);    	
    }
    
    public void updateObjects() {
    	for(GameObject object : gameObjects) {
    		object.update();
    	}
    	repaint();
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	for(GameObject object : gameObjects) {
    		object.paintObject(g);
    	}

    	g.setColor(Color.DARK_GRAY);
       	g.fillRect(5,0,FRAME_WIDTH,5);
       	g.fillRect(0,0,5,FRAME_HEIGHT);
       	g.fillRect(FRAME_WIDTH-19, 0,5,FRAME_HEIGHT);
       	g.fillRect(0, FRAME_HEIGHT, FRAME_WIDTH,10);

       	g.drawImage(failImage, 240, 140, null);
    }
    
    public void printFail() {
        try {
			failImage = ImageIO.read(new File("./fail.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
    
 
}
