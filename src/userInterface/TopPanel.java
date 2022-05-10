package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import game.Ball;
import game.GamePanel;


public class TopPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static long timeRemaining;
	private static long timeElapsed;
	private static long startTime;
	private static int levelDuration = 60;
	private static GamePanel gamePanel;
	private static Ball ball;
	private static double deltaT = 0.1;
	private static double gravity = 9.81;
	private static int lives = 3;
	private static int level = 1;
	private static int score = 0;
	private JLabel scoreLabel;
	private JLabel levelLabel;
	private JPanel timePanel;
	BoxLayout boxLayout;
	private JLabel timeLabel;
	private JProgressBar progBar;
	private BufferedImage image;

    public TopPanel(GamePanel gamePanel, Ball ball){
    	this.setPreferredSize(new Dimension(1024,50));
    	TopPanel.ball = ball;
    	TopPanel.gamePanel = gamePanel;
    	scoreLabel = new JLabel();
    	this.add(scoreLabel);
    	levelLabel = new JLabel();
    	this.add(levelLabel);
    	
    	timePanel = new JPanel();
    	boxLayout = new BoxLayout(timePanel, BoxLayout.Y_AXIS);
	    timePanel.setLayout(boxLayout);
    	timeLabel = new JLabel();
    	progBar = new JProgressBar(0, levelDuration);
    	progBar.setStringPainted(false);
    	timePanel.add(timeLabel);
    	timePanel.add(progBar);
    	timePanel.setBackground(new Color(10,150,200));
    	this.add(timePanel);
    	
    	timeLabel.setForeground(new Color(20,20,20));
    	levelLabel.setForeground(new Color(10,150,200));
    	scoreLabel.setForeground(new Color(10,150,200));
    	
    	
    	timeRemaining = levelDuration;
        startTime = System.currentTimeMillis();
        

    	try {
			image = ImageIO.read(new File("./Heart.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public static void loseLife() {
    	lives--;
    	gamePanel.gameReset();
    }
    
    public static void pause() {
    	deltaT = 0;
    }
    public static void unPause() {
    	deltaT = 0.2;
    	startTime = System.currentTimeMillis()-timeElapsed;
    }
    
    public void gainLife() {
    	lives++;
    }
    
    public void gainLife(int i) {
    	lives += i;
    }
    
    public void levelUp() {
    	level++;
    	gainLife();
    	timeRemaining = levelDuration;
    	ball.setInitialVelocity(ball.getInitialVelocity().scaled(1.5));
    	gamePanel.gameReset();
    }
    
    public void gameOver() {
    	pause();
    	gamePanel.printFail();
    }
    
	public static void incScore() {
		score++;
	}
	
	public static void decScore() {
		score--;
	}
	
	void updatePanel() {
		repaint();
		scoreLabel.setText(String.format("Score: %d", score));
		levelLabel.setText(String.format("Level: %d", level));
		
		timeElapsed = System.currentTimeMillis() - startTime;
		if(deltaT != 0) {	
			calculateTime();
			progBar.setValue((int) (levelDuration- timeRemaining));
		}
		timeLabel.setText(String.format("Remaining time: %d", timeRemaining));
		
		if(lives == 0) {
			pause();
			gameOver();
		}
		else if(timeRemaining <= 0) {
			levelUp();
	    	startTime = System.currentTimeMillis();
		}
	}
	
	private void calculateTime() {
		timeRemaining = levelDuration - (timeElapsed/1000);
	}

	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	for(int i=0; i<lives ;i++) {
			g.drawImage(image,
					10+40*i,
					10,
					40+40*i,
					40,
					0,
					0,
					image.getWidth(),
					image.getHeight(),
					null);
    	}
    }

	
	
	//Bunch of Getters and Setters
	
	
	public static double getGravity() {
		return gravity;
	}

	public static void setGravity(double gravity) {
		TopPanel.gravity = gravity;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		TopPanel.level = level;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		TopPanel.score = score;
	}

	public static double getDeltaT() {
		return deltaT;
	}

	public static long getTimeRemaining() {
		return timeRemaining;
	}

	public static void setTimeRemaining(long timeRemaining) {
		TopPanel.timeRemaining = timeRemaining;
	}

	public static long getTimeElapsed() {
		return timeElapsed;
	}

	public static void setTimeElapsed(long timeElapsed) {
		TopPanel.timeElapsed = timeElapsed;
	}

	public static long getStartTime() {
		return startTime;
	}

	public static void setStartTime(long currentTime) {
		TopPanel.startTime = currentTime;
	}

	public static int getLevelDuration() {
		return levelDuration;
	}

	public static void setLevelDuration(int levelDuration) {
		TopPanel.levelDuration = levelDuration;
	}

	public static int getLives() {
		return lives;
	}



}
