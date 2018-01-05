package janunit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Flow {

	private String name;
	private String url;
	private List<Step> steps;	
	
	public Flow() {
		steps = new ArrayList<Step>();		
	}	
	
	public Flow(Flow original) {
	    this.name = new String(original.name);
	    this.url = new String(original.url);
	    this.steps = new ArrayList<Step>();

	    for (Step step : original.getSteps()) {
	    	this.steps.add(new Step(step));
	    }
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Step> getSteps() {
		return steps;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
	public void addStep(Step step) {
		steps.add(step);
	}

	public Step getStep(String name) {
		
		for(Step step : steps) {
			if(step.getName().equals(name)) {
				return step;
			}
		}
		return null;
	}
	
	public void updateStep(Step replace) {
		
		int index = 0;
		for(Step step : steps) {
			if(step.getName().equals(replace.getName())) {
				break;
			}
			index++;
		}
		
		steps.set(index, replace);
	}
	//stepName.inputName
	public Input findInput(String currentStep, String name) {
		
		Input input = null;
		
		String stepName = currentStep;
		String inputName = name;

		if(name.contains(".")) {
		    String[] split = name.split(Pattern.quote("."));
		    stepName = split[0];
		    inputName = split[1];
		}
		
		if(getStep(stepName) != null) {
			if(getStep(stepName).getInput(inputName) != null) {
				input = new Input(getStep(stepName).getInput(inputName));
			}
		}
		return input;
	}
	public Input findInput(String name) {
		
		Input input = null;
		
		String stepName = null;
		String inputName = null;

		if(name.contains(".")) {
		    String[] split = name.split(Pattern.quote("."));
		    stepName = split[0];
		    inputName = split[1];
		}
		
		if(getStep(stepName) != null) {
			if(getStep(stepName).getInput(inputName) != null) {
				input = new Input(getStep(stepName).getInput(inputName));
			}
		}
		return input;
	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public boolean isReference(String name) {
		
		if(name.contains(".")) {
		    String[] split = name.split(Pattern.quote("."));
		    String stepName = split[0];
		    String inputName = split[1];
		    
			if(getStep(stepName) != null) {
				if(getStep(stepName).getInput(inputName) != null) {
					return true;
				}
			}
		}
		return false;
	}

	


	//@TODO find inout for parameter
	//stepName.inputName
	public Parameter findParameter(String currentStep, String name) {
		
		Parameter parameter = null;
		
		String stepName = currentStep;
		String parameterName = name;

		if(name.contains(".")) {
		    String[] split = name.split(Pattern.quote("."));
		    stepName = split[0];
		    parameterName = split[1];
		}
		
		if(getStep(stepName) != null) {
			if(getStep(stepName).getParameter(parameterName) != null) {
				parameter = new Parameter(getStep(stepName).getParameter(parameterName));
			}
		}
		return parameter;
	}

	@Override
	public String toString() {
		return "Flow [name=" + name + ", url=" + url + ", steps=" + steps.toString() + "]";
	}

	
	
}
