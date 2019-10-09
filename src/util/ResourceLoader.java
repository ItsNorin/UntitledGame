package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import entity.Entity2D;
import javafx.scene.image.Image;
import levels.Level;

public enum ResourceLoader {
	LOADER;
	
	private static final FilenameFilter isXML;
	
	static {
		isXML = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		};
	}

	public static Image getImage(String name) {
		Image i = new Image("resources\\" + name);
		return i;
	}

	public static Entity2D loadEntityFromXML(File XMLFile) {
		// TODO
		Entity2D e = null;
		return e;
	}

	public static Level loadLevelFromXML(File XMLFile) {
		// TODO
		return null;
	}

	public static ArrayList<Level> loadAllLevels() {
		ArrayList<Level> levels = new ArrayList<Level>();
		
		// attempt to load all scripted events
		File dir = new File("res/levels");
		if (dir.exists()) {
			for (File subFile : dir.listFiles(isXML))
				if (subFile.exists())
					levels.add(loadLevelFromXML(subFile));
		}
		return levels;
	}
}
