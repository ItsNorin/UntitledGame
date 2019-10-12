package scripted_events;

import levels.Level;

/**
 * Scripted events contain information about what should happen in game, and what conditions must be met
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 *
 */
public abstract class ScriptedEvent {
	protected final String name;
	protected final Level level;
	
	public ScriptedEvent(String name, Level level) {
		this.name = name;
		this.level = level;
	}
	
	/** @return Name of event for identification */
	public final String getName() {
		return name;
	}
	
	public final Level getLevel() {
		return level;
	}
	
	/** executes the event */
	public abstract void run();
}
