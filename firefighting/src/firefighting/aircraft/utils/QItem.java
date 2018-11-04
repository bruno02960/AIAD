/**
 * Agents and Distributed Artificial Intelligence
 * Project 1 - Fire Fighting
 * 
 * Authors:
 * 	@author Bernardo Coelho Leite - up201404464@fe.up.pt;
 * 	@author Bruno Miguel Pinto - up201502960@fe.up.pt;
 * 	@author Ruben Andre Barreiro - up201808917@fe.up.pt;
 */

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