package scripted_events;


public abstract class EventConditions {
	protected ScriptedEvent event;
	
	/** @return true if event should be triggered */
	public abstract boolean conditionsMet();
}
