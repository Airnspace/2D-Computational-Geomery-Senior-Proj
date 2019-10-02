import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class GuiDisplay extends JFrame {
	//General initializations, inCord is number of UI elements
	int inCord = 2;
	int x, y;
	JFrame window = new JFrame("Coordinate Algorithms");
	JPanel bDrop = new JPanel(new GridLayout(inCord, 4));
	JButton intr = new JButton("Line Intersection");
	JButton cHull = new JButton("Convex Hull");
	//new class for graph
	Graph g1 = new Graph();
	
	
	public GuiDisplay() {
		//initialization of all elements, including the graph
		window.setLayout(new GridLayout(1, 0));
		window.add(bDrop);
		window.add(g1);
	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bDrop.add(cHull);
		bDrop.add(intr);
		bDrop.add(new JPanel());
		bDrop.add(new JPanel());
		
		

		window.pack();
		window.setBounds(0, 0, 720, 360);
		//setting things visible
		cHull.setVisible(true);
		bDrop.setVisible(true);
		g1.setVisible(true);
		window.setVisible(true);
		g1.repaint();
		
		
		//action listener added to convex hull button
		cHull.addActionListener(new ActionListener()
		{
			//action listener for when the Convex Hull button is pressed
			@Override
			public void actionPerformed(ActionEvent e1) {
				g1.actionPerformed(e1);
			}
		});
		cHull.setActionCommand("hull");
		intr.addActionListener(new ActionListener()
		{
			//action listener for when the Convex Hull button is pressed
			@Override
			public void actionPerformed(ActionEvent e) {
				g1.actionPerformed(e);
			}
		});
		intr.setActionCommand("intr");
	}
	
	private void createGUI() {
		
	}
	}