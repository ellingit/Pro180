package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {
	GridLayout boardLayout = new GridLayout(8, 8);
	JPanel board;
	
	public GUI(){
		init();
	}
	private void init(){
		this.setSize(800, 700);
		board = new JPanel(boardLayout);
		board.setPreferredSize(new Dimension(600, 600));
		for(int i=0; i<64; i++){
			BoardSquare bs = new BoardSquare();
			bs.setSize(70, 70);
			bs.setBackground(Color.WHITE);
			bs.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			board.add(bs);
		}
		this.add(board);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
