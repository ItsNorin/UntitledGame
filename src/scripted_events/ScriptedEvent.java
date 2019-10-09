package scripted_events;

/**
 * Scripted events contain information about what should happen in game, and what conditions must be met
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 *
 */
public abstract class ScriptedEvent {
	
	/** @return Name of event for identification */
	public abstract String getName();
	
	/** @return Name of level for event */
	public abstract String getLevelName();
	
	/** executes the event */
	public abstract void run();
}
