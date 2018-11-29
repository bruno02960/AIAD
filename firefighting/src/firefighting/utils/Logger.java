package firefighting.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public final class Logger {
	static StringBuilder sb;
	static PrintWriter pw;
	
	Logger() {
		File file = new File("test.csv");
		sb = new StringBuilder();
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				pw = new PrintWriter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			sb.append("width");
			sb.append(',');
			sb.append("height");
			sb.append(',');
			sb.append("water_res");
			sb.append(',');
			sb.append("aircrafts");
			sb.append(',');
			sb.append("max_fires");
			sb.append(',');
			sb.append("time_extinguish");
			sb.append('\n');
		}
		else {
			try {
				pw = new PrintWriter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void appendConfigValues(long time_extinguish) {
		sb.append(Config.GRID_WIDTH);
		sb.append(',');
		sb.append(Config.GRID_HEIGHT);
		sb.append(',');
		sb.append(Config.NUM_MAX_WATER_RESOURCES);
		sb.append(',');
		sb.append(Config.NUM_MAX_AIRCRAFTS);
		sb.append(',');
		sb.append(Config.NUM_MAX_FIRES);
		sb.append(',');
		sb.append(time_extinguish);
		sb.append('\n');
	}
	
	public static void closeStream() {
        pw.write(sb.toString());
        pw.close();
	}
}
