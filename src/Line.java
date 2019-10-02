import java.awt.*;


public class Line {
Point start = new Point();
Point end = new Point();

public Line(Point p1, Point p2) {
	start.setLocation(p1);;
	end.setLocation(p2);;
}

public Point getStart() {
	return start;
}
public Point getEnd() {
	return end;
}

public int startX() {
	return start.x;
}

public int startY() {
	return start.y;
}

public int endX() {
	return end.x;
}

public int endY() {
	return end.y;
}
}
