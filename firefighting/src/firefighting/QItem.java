package firefighting;

import java.awt.Point;
import java.util.ArrayList;

public class QItem {
	int row;
	int col;
	int dist;
	ArrayList<Point> path;

	QItem(int x, int y, int w, ArrayList<Point> s) {
		row = x;
		col = y;
		dist = w; 
		path = s;
	}
	
	ArrayList<Point> getPath() {
		return path;
	}
}
