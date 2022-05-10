package userInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.*;

public class Main {
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 768;
    

    public static final int PLANE_WIDTH = 1024;
    public static final int PLANE_HEIGHT = 608;
    
	private static Timer timer;
	
    public static void main(String[] args) {
		GamePanel gamePanel = new GamePanel(PLANE_WIDTH, PLANE_HEIGHT);
		
		Ball ball = new Ball(new Vector2D(470,145), new Vector2D(20, 1), 5, gamePanel.getGameObjects());
		gamePanel.addGameObject(ball);

		Paddle paddle = new Paddle(new Vector2D(PLANE_WIDTH/2-70, PLANE_HEIGHT-40), 120, 20, ball);
		gamePanel.addGameObject(paddle);

		
		createObjects(gamePanel);
		swingSetUp(gamePanel, ball, paddle);
	}
    
    public static void createObjects(GamePanel gamePanel) {

    	//Block block = new Block(new Vector2D(470,150), 20, gamePanel.getGameObjects());
    	//gamePanel.addGameObject(block);

    	Star star= new Star(new Vector2D(40,50), 20, gamePanel.getGameObjects());
		gamePanel.addGameObject(star);

		Meteorite mete = new Meteorite(new Vector2D(100,150), 20, gamePanel.getGameObjects());
		gamePanel.addGameObject(mete);

		UFO ufo = new UFO(new Vector2D(150,200), 30, gamePanel.getGameObjects());
		gamePanel.addGameObject(ufo);

		Block[] blockList = new Block[4];
		for(Block bloc : blockList) {
			bloc = new Block(30, gamePanel.getGameObjects());
			gamePanel.addGameObject(bloc);
		}
    }
    
    public static void swingSetUp(GamePanel gamePanel, Ball ball, Paddle paddle) {
    	GameFrame frame = new GameFrame(FRAME_WIDTH, FRAME_HEIGHT, "Space Pong", paddle);
	    
    	TopPanel topPanel = new TopPanel(gamePanel, ball);
	    BotPanel botPanel = new BotPanel(paddle);
	    
	    JPanel mainPanel = new JPanel();
	    BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
	    mainPanel.setLayout(boxLayout);
	    
        topPanel.setBackground(new Color(20, 20, 20));
        gamePanel.setBackground(new Color(0, 0, 40));
        botPanel.setBackground(new Color(20, 20, 20));
        
	    mainPanel.add(topPanel);
	    mainPanel.add(gamePanel);
	    mainPanel.add(botPanel);
	    
	    frame.add(mainPanel);
	    frame.pack();
	    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	    	    
        timer = new Timer(10, new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(TopPanel.getDeltaT() > 0)
        			topPanel.updatePanel();
    				gamePanel.updateObjects();
    				botPanel.updatePanel();
        	}
        });
        
        timer.setInitialDelay(40);

        timer.start();		// start the timer
    }
}
