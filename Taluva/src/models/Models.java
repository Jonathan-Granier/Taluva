package models;

import java.util.ArrayList;
import java.util.List;

public class Models {
	
	private List<Model> models;
	
	public Models(){
		this.models = new ArrayList<Model>();
	}
	
	public Models(List<Model> models){
		this.models = new ArrayList<Model>(models);
	}

	public List<Model> getModels() {
		return models;
	}

	public void add(Model model){
		this.models.add(model);
	}
}
