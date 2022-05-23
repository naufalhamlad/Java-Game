import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame extends JFrame{
	
	GamePanel panel = new GamePanel();
	
	GameFrame(){
		
		panel = new GamePanel();
		
		this.add(panel);
		this.setTitle("Klask");
		this.setResizable(true);
		this.setBackground(Color.blue);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
		
				
		
	}
	
}
