package firefighting.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class Logger {
	static PrintWriter out;
	static BufferedWriter bw;
	boolean newFile = false;
	
	Logger() {
		File file = new File("data.csv");
		
		if(!file.exists()) newFile = true;
		
		FileWriter fw;
		try {
			fw = new FileWriter("data.csv", true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    
		    if(newFile) {
		    	out.println("total_area,water_res,aircrafts,max_fires,avg_distance_water_resources,avg_num_fires_per_sec,avg_num_occuped_aircrafts_per_sec,time_extinguish");
		    }
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void appendConfigValues(double avg_distance_water_resources, double avgNumFiresPerSecond, double avgNumOccupedAircraftsPerSecond, long time_extinguish) {
    	out.println((Config.GRID_WIDTH * Config.GRID_HEIGHT) + "," + Config.NUM_MAX_WATER_RESOURCES +
    			"," + Config.NUM_MAX_AIRCRAFTS + "," + Config.NUM_MAX_FIRES + "," + avg_distance_water_resources + "," + 
    			avgNumFiresPerSecond + "," + avgNumOccupedAircraftsPerSecond + "," + time_extinguish);
	}
	
	public static void closeStream() throws IOException {
        out.close();
        bw.close();
	}
}
