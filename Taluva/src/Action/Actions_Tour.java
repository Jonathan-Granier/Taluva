package Action;

public class Actions_Tour {
	public Action_Tuile action_tuile;
	public Action_Construction action_construction;
	
	public Actions_Tour(Action_Tuile AT, Action_Construction AC)
	{
		this.action_construction = AC;
		this.action_tuile = AT;
	}
	
	public Action_Tuile getAction_tuile() {
		return action_tuile;
	}
	public void setAction_tuile(Action_Tuile action_tuile) {
		this.action_tuile = action_tuile;
	}
	public Action_Construction getAction_construction() {
		return action_construction;
	}
	public void setAction_construction(Action_Construction action_construction) {
		this.action_construction = action_construction;
	}
}
