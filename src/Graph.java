import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Graph extends JPanel implements ActionListener{
	//adding elements, scale is scale factor for graph lines
	int scale = 30;
	//integer to represent number of times the hull button is clicked
	//starts on an odd number for convenience 
	int eoHull = 1;
	//the arrayList where all created point data is saved
	private ArrayList<Point> points;
	//used for getting a specific point from the arrayList
	private Point pc;
	//the graphics of the graph's JPanel
	Graphics g;
	//Boolean for drawing the Convex Hull
	private boolean doHull = false;
	//Boolean to make sure the wrap is only done once
	int once = 0;
	//Vector of saved points for the wrapped Convex Hull
	Vector<Point> hull = new Vector<Point>();
	//Boolean for the Intersection Button
	private boolean doIntr = false;
	//Array List of start points for the intersect Lines
	private ArrayList<Line> hullLines = new ArrayList();
	private ArrayList<Point> pOne = new ArrayList();
	private ArrayList<Point> pTwo = new ArrayList();
	private ArrayList<Line> intrLines = new ArrayList();
	private ArrayList<Point> intrPoint = new ArrayList();
	int sOut;
	
	
	public Graph() {
		//initializing all elements
		points = new ArrayList<Point>();
		setBackground(Color.WHITE);
		setSize(360, 360);
		
		
		//adding a mouse listener to the class to save the coordinates of where
		//you click on the panel
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				points.add(new Point(e.getX(), e.getY()));
				pc = new Point(e.getX(), e.getY());
				repaint();
			}
		});
		
		getGraphics();
		paintComponents(g);
	}
	
	//method for finding the orientation of a point on the graph to be used for 
	//the convex hull drawing and returning an int depending on their position:
	//if 0: p, q, and r are colinear
	//if 1 it is counterclockwise
	//if 2 it is counterclockwise
	public static int orientation(Point p, Point q, Point r) {
		int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
		if (val == 0) return 0; //colinear
		return (val > 0)? 1: 2; //clock or counterclockwise
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//paints black graph lines over the Panel
		g.setColor(Color.BLACK);
		for(int i=0; i<11; i++) {
			g.drawLine((i+1)*scale, 0, (i+1)*scale, 360);
			g.drawLine(0, (i+1)*scale, 360, (i+1)*scale);
			}
		repaint();
		int s = points.size();
		//paints a red dot on the panel where the panel was clicked
		g.setColor(Color.RED);
		for (Point point : points) {
			g.fillOval(point.x, point.y, 10, 10);
		}
		repaint(); 
		
		//boolean for the convex Hull button
		if(doHull == true) {
			//the convex hull lines will be painted in blue
			g.setColor(Color.BLUE);
				for(Line hLine : hullLines) {
					g.drawLine(hLine.startX(), hLine.startY(), hLine.endX(), hLine.endY());	
				}
			//draws the result
			repaint();
		}
		else {
			//if the vector button is pressed again it erases the line
			if(g.getColor() == Color.BLUE) {
				g.setColor(null);
			}
			repaint();
		}
		//If there is an even amount of points, and there are more than 3
		//lines will be drawn between all points.
		//assigns the start and end points for all these lines
	if(doIntr == true) {
		g.setColor(Color.GREEN);
		for(Line iLine : intrLines)
		g.drawLine(iLine.startX(), iLine.startY(), iLine.endX(), iLine.endY());
		
		g.setColor(Color.ORANGE);
		for(Point iP: intrPoint) 
		g.fillOval(iP.x, iP.y, 10, 10);
		
		repaint();
		}

	else {
		//clears when triggered off
		if(g.getColor() == Color.GREEN || g.getColor() == Color.ORANGE) {
			g.setColor(null);
		}
		repaint();
	}
}
	public void hullFinder() {
		int size = points.size();
		if (size == 2) {
			Line single = new Line(points.get(0), points.get(1));
			hullLines.add(single);
		}
		else if(size >= 3) {
		//integers to represent positions in the array and how they translate to
		//the hull vector:
		//represents the farthest left point
		int l = 0;
		//represents the current point in the vector
		int n = 0;
		//the next farthest outside point counterclockwise
		int o;
		
		//for loop that runs through the array of points and searches for the farthest
		//left point
		
		for (int i = 0; i<size; i++) {
			if (points.get(i).x < points.get(l).x) {
				l=i;
			}
		}
		
		//sets the first point of the vector to that farthest left point
		n = l;
		
		do {
			//adds points to the hull vector
			hull.add(points.get(n));
			//a formula that searches for the point 'o' that 
			//orientation(n, l, o) is counterclockwise for all points l
			//the goal is to track the last visited most counterclockwise point in q
			//if any point 'i' is more counterclockwise, then it is updated
			o = (n + 1) % size;
			for (int i = 0; i<size; i++) {
				if (orientation(points.get(n), points.get(i), points.get(o)) == 2) 
					o = i;
			}
			//once the most counterclockwise point is found it moves to the next point
			//it is then added to the hull vector
			n=o;
		} while(n != l);//while we don't reach the first point


			//represents the next point of the vector while there are next points
			Point next;
			//the last point of the vector
			Point last = hull.get(hull.size()-1);
			//the first point of the vector
			Point first = hull.get(0);
			//
			Line hLine;
			//
			Line lLine = new Line(last, first);
			for(int i=0; i< (hull.size()); i++) {
				if(i < (hull.size()-1)) {
				//gets the value of the next available point while there is one
				//and draws a line between the current and next point
				next = hull.get(i+1);
				hLine = new Line(hull.get(i), next);
				hullLines.add(hLine);
				}
				else
					hullLines.add(lLine);
			}
			
			//if it is the last point of the vector, draws a line between the last
			//and last point
				
	}
	}

	public void intersects() {
		if(doIntr == true) {
			int s = points.size();
			if(s >=3 && (s % 2 == 0)) {
				for(int i = 0; i <= (s-1); i+=2)
						pOne.add(points.get(i));
				
				for(int j = 1; j <= (s-1); j+=2) 
					pTwo.add(points.get(j));
				
			for(int i = 0; i < pOne.size(); i++)
				intrLines.add(new Line(pOne.get(i), pTwo.get(i)));
			
				//Runs through each of the start and end points of the lines
				//calculates if there are intersections at any point
				//then draws a new point at each intersection
					for(int i=0; i<intrLines.size(); i++) {
						for (int j=0; j<intrLines.size(); j++) {
							Line l1 = intrLines.get(i);
							Line l2 = intrLines.get(j);
						//formula to represent line 1
						int a1 = l1.endY()-l1.startY();
						int b1 = l1.startX()-l1.endX();
						int c1 = a1*(l1.startX())+b1*(l1.startY());
						
						//formula to represent line 2
						int a2 = l2.endY()-l2.startY();
						int b2 = l2.startX()-l2.endX();
						int c2 = a2*(l2.startX())+b2*(l2.startY());
						
						int determinant = a1*b2 - a2*b1;
						if(determinant == 0) {

						}
						else {
				            int xIntr = (b2*c1 - b1*c2)/determinant; 
				            int yIntr = (a1*c2 - a2*c1)/determinant;
				            int xInt = Math.round(xIntr);
				            int yInt = Math.round(yIntr);
				            Point ins = new Point(xInt, yInt);
				            if(((ins.x > l1.startX() && ins.x < l1.endX()) || (ins.y > l1.startY() && ins.y < l1.endY())) || ((ins.x > l2.startX() && ins.x < l2.endX()) || (ins.y > l2.startY() && ins.y < l2.endY()))) 
				            intrPoint.add(ins);
						}
					
				}
		}
		}
		}
		else {
			
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	if(e.getActionCommand() == "hull") {
		if (doHull == false) {
			doHull = true;
			hullFinder();
			System.out.println("on pressed");
	}
		else if (doHull == true) {
			doHull = false;
			hull.clear();
			hullLines.clear();
			System.out.println("off pressed");
	}
	}
	else if(e.getActionCommand() == "intr") {
	if (doIntr == false) {
		doIntr = true;
		intersects();

		System.out.println("on pressed");
	}
	else if (doIntr == true) {
		doIntr = false;
		pOne.clear();
		pTwo.clear();
		intrLines.clear();
		intrPoint.clear();
		System.out.println("off pressed");
	}
	}
	}
}

