package scripted_events;

import java.util.HashMap;

import levels.Level;

public final class ScriptManager {
	private ScriptManager() {}
	
	private static HashMap<String, ScriptedEvent> allEvents;

	static {
		allEvents = new HashMap<>();
		
	}

	public static ScriptedEvent getEvent(String name) {
		return allEvents.get(name);
	}

	/**
	 * @param levelName Name of level
	 * @return all events that can occur in that level
	 */
	public static HashMap<String, ScriptedEvent> getAllEventsForLevel(Level level) {
		HashMap<String, ScriptedEvent> events = new HashMap<String, ScriptedEvent>();
		for (ScriptedEvent e : allEvents.values())
			if (e.getLevel() == level)
				events.put(e.getName(), e);
		return events;
	}
}
