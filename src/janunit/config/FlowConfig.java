package janunit.config;

import java.util.ArrayList;
import java.util.List;

public class FlowConfig {

	private String name;
	private String url;
	private List<String> steps;
	
	public FlowConfig() {		
	}	

/*	
	public FlowConfig(FlowConfig original) {
		this.name = new String(original.name);
	    this.url = new String(original.url);
	    this.steps = new ArrayList<String>();
		steps.addAll(original.steps);
	}
	*/
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getSteps() {
		return steps;
	}	
	public void setSteps(List<String> steps) {
		this.steps = steps;
	}
}

