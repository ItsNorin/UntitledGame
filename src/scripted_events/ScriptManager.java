package scripted_events;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

public final class ScriptManager {
	private ScriptManager() {}
	
	private static HashMap<String, ScriptedEvent> allEvents;

	static {
		allEvents = new HashMap<>();

		// attempt to load all scripted events
		File dir = new File("res/events");
		if (dir.exists()) {
			FilenameFilter textFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".xml");
				}
			};

			for (File subFile : dir.listFiles(textFilter))
				if (subFile.exists())
					loadEventFromXML(subFile);
		}
	}


	private static ScriptedEvent loadEventFromXML(File eventXMLFile) {
		/*
		 * try {
		 * 
		 * Element clothingSetElement = Element.getDocumentRootElement(eventXMLFile);
		 * 
		 * String loadedSetName =
		 * clothingSetElement.getMandatoryFirstOf("name").getTextContent(); String
		 * loadedDisplayName =
		 * clothingSetElement.getMandatoryFirstOf("displayName").getTextContent();
		 * 
		 * int loadedNumberRequiredForCompleteSet =
		 * Integer.valueOf(clothingSetElement.getMandatoryFirstOf(
		 * "requiredForCompleteSet").getTextContent());
		 * 
		 * List<InventorySlot> loadedBlockedSlotsCountingTowardsFullSet=
		 * clothingSetElement .getMandatoryFirstOf("includedBlockedSlots")
		 * .getAllOf("slot").stream()
		 * .map(Element::getTextContent).map(InventorySlot::valueOf)
		 * .collect(Collectors.toList());
		 */
		/*
		 * List<CombatMove> loadedCombatMoves = clothingSetElement
		 * .getMandatoryFirstOf("combatMoves") .getAllOf("move").stream()
		 * .map(Element::getTextContent).map(CombatMove::getMove)
		 * .collect(Collectors.toList());
		 * 
		 * List<Spell> loadedSpells = clothingSetElement .getMandatoryFirstOf("spells")
		 * .getAllOf("spell").stream() .map(Element::getTextContent).map(Spell::valueOf)
		 * .collect(Collectors.toList());
		 * 
		 * StatusEffect loadedAssociatedStatusEffect =
		 * StatusEffect.valueOf(clothingSetElement.getMandatoryFirstOf("statusEffect").
		 * getTextContent());
		 * 
		 * ClothingSet clothingSet = new ClothingSet(loadedSetName, loadedDisplayName,
		 * loadedAssociatedStatusEffect, loadedNumberRequiredForCompleteSet,
		 * loadedCombatMoves, loadedSpells);
		 * 
		 * allClothingSets.put(loadedSetName, clothingSet);
		 * 
		 * return clothingSet;
		 * 
		 * } catch (XMLLoadException e) { e.printStackTrace(); } catch
		 * (XMLMissingTagException e) { e.printStackTrace(); }
		 */
		return null;
	}

	public static ScriptedEvent getEvent(String name) {
		return allEvents.get(name);
	}

	/**
	 * @param levelName Name of level
	 * @return all events that can occur in that level
	 */
	public static HashMap<String, ScriptedEvent> getAllEventsForLevel(String levelName) {
		HashMap<String, ScriptedEvent> events = new HashMap<String, ScriptedEvent>();
		for (ScriptedEvent e : allEvents.values())
			if (e.getLevelName() == levelName)
				events.put(e.getName(), e);
		return events;
	}
}
