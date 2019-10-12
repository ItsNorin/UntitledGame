package util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import entity.Bullet;
import entity.Creature;
import entity.Entity2D;
import entity.HomingBullet;
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
	
	public static Image getImage(File imgFile) {
		return new Image(imgFile.toURI().toString());
	}

	public static Image getImage(String name) {
		return getImage(new File("res/images/" + name));
	}
	
	
	private static Creature.CreatureParameters loadCreatureParamsFromXML(final Element root) throws ParserConfigurationException, SAXException, IOException {
		Creature.CreatureParameters cp = null;

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
	
	private static Bullet.BulletParams loadBulletParamsFromXML(final Element root) throws ParserConfigurationException, SAXException, IOException {
		Bullet.BulletParams bp = new Bullet.BulletParams();
		
		bp.imageName = root.getElementsByTagName("image").item(0).getTextContent();
		bp.numFrames = Integer.parseInt(root.getElementsByTagName("numberOfFrames").item(0).getTextContent());
		bp.animationLength = Integer.parseInt(root.getElementsByTagName("frameTimeMS").item(0).getTextContent());
		bp.width = Double.parseDouble(root.getElementsByTagName("hitboxWidth").item(0).getTextContent());
		bp.height = Double.parseDouble(root.getElementsByTagName("hitboxHeight").item(0).getTextContent());
		bp.imageXOffset = Integer.parseInt(root.getElementsByTagName("spriteXOffset").item(0).getTextContent());
		bp.imageYOffset = Integer.parseInt(root.getElementsByTagName("spriteYOffset").item(0).getTextContent());
		bp.damage = Double.parseDouble(root.getElementsByTagName("damage").item(0).getTextContent());
		bp.lifespanMS = Integer.parseInt(root.getElementsByTagName("lifespanMS").item(0).getTextContent());
		
		return bp;
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
			case "player":        e = new Player(loadCreatureParamsFromXML(root));       break;
			case "bullet":        e = new Bullet(loadBulletParamsFromXML(root));         break;
			case "homingbullet":  e = new HomingBullet(loadBulletParamsFromXML(root));   break;
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
