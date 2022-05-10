package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Paddle paddle;
	public GameFrame(int Width, int Height, String title, Paddle paddle) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Width, Height);
		this.addKeyListener(this);
		this.setVisible(true);
		this.setTitle(title);
		this.paddle = paddle;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && !paddle.isBotActive())
			paddle.setDirection(1);
		if(e.getKeyCode() == KeyEvent.VK_LEFT && !paddle.isBotActive())
			paddle.setDirection(-1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.getDirection() == 1 && !paddle.isBotActive())
				paddle.setDirection(0);
			if(e.getKeyCode() == KeyEvent.VK_LEFT  && paddle.getDirection() == -1 && !paddle.isBotActive())
				paddle.setDirection(0);
	}

}
