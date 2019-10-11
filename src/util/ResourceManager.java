package util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import entity.Creature;
import entity.Entity2D;
import entity.Player;
import javafx.scene.image.Image;
import levels.Level;

public enum ResourceManager {
	RM;

	private static DocumentBuilderFactory dbFactory;
	private static final FilenameFilter isXML;

	static {
		dbFactory = DocumentBuilderFactory.newInstance();

		isXML = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		};
	}

	public static Image getImage(String name) {
		Image i = new Image("resources/" + name);
		return i;
	}
	
	
	private static Creature.CreatureParameters loadCreatureParamsFromXML(File XMLFile) throws ParserConfigurationException, SAXException, IOException {
		Creature.CreatureParameters cp = null;

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLFile);
		Element root = doc.getDocumentElement();

		String up = root.getElementsByTagName("upImg").item(0).getTextContent();
		String down = root.getElementsByTagName("downImg").item(0).getTextContent();
		String left = root.getElementsByTagName("leftImg").item(0).getTextContent();
		String right = root.getElementsByTagName("rightImg").item(0).getTextContent();

		double width = Double.parseDouble(root.getElementsByTagName("hitboxWidth").item(0).getTextContent());
		double height = Double.parseDouble(root.getElementsByTagName("hitboxHeight").item(0).getTextContent());

		double spriteXOffset = Double.parseDouble(root.getElementsByTagName("spriteXOffset").item(0).getTextContent());
		double spriteYOffset = Double.parseDouble(root.getElementsByTagName("spriteYOffset").item(0).getTextContent());

		int numFrames = Integer.parseInt(root.getElementsByTagName("numberOfFrames").item(0).getTextContent());

		cp = new Creature.CreatureParameters(up, down, left, right, width, height, spriteXOffset, spriteYOffset, numFrames);

		cp.maxHealth = Double.parseDouble(root.getElementsByTagName("maxHealth").item(0).getTextContent());

		return cp;
	}

	/**
	 * @param XMLFile xml file
	 * @return new entity from contents of XML, will cause exception mayhem if any errors occur
	 * TODO: make loading xmls with errors not cause massive issues
	 */
	public static Entity2D loadEntityFromXML(File XMLFile) {
		Entity2D e = null;

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XMLFile);
			Element root = doc.getDocumentElement();

			String type = root.getAttribute("type");

			switch (type.toLowerCase()) {
			case "player":      e = new Player(loadCreatureParamsFromXML(XMLFile));       break;
			case "bullet":
			default:
			}

		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		}

		return e;
	}

	/**
	 * @param fileName name of xml file, including file extension (.xml)
	 * @return new entity from contents of XML, will cause exception mayhem if any errors occur
	 * TODO: make loading xmls with errors not cause massive issues
	 */
	public static Entity2D loadEntityFromXML(String fileName) {
		return loadEntityFromXML(new File("res/entities/" + fileName));
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
