package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import game.Paddle;


public class BotPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton pause = new JButton("Pause");
	JRadioButton bot = new JRadioButton("Toggle Bot");

    public BotPanel(Paddle paddle){
    	this.setPreferredSize(new Dimension(1024,50));
        //TODO Create the button action
    	pause.setFocusable(false);
    	bot.setFocusable(false);
    	pause.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			pauseTriggered();
    		}
    	});
    	bot.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			paddle.toggleBot();
    		}
    	});
    	

        bot.setBackground(new Color(20, 20, 20));
    	bot.setForeground(new Color(10,150,200));
    	
    	this.add(bot);
        this.add(pause);
    	
    }


	void updatePanel() {
		repaint();	
	}
	
	private void pauseTriggered() {
		if(TopPanel.getDeltaT() == 0 && TopPanel.getLives() != 0) {
			TopPanel.unPause();
			pause.setText("Pause");
		}
		else {			
			TopPanel.pause();
			pause.setText("Unpause");
		}
	}
}
