package firefighting.aircraft.utils;

import java.awt.Point;
import java.util.ArrayList;

public class QItem {
	public int row;
	public int col;
	public int dist;
	public ArrayList<Point> path;

	public QItem(int x, int y, int w, ArrayList<Point> s) {
		row = x;
		col = y;
		dist = w; 
		path = s;
	}
	
	ArrayList<Point> getPath() {
		return path;
	}
}
