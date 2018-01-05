package janunit.model;

import java.util.ArrayList;
import java.util.List;

public class Step {

	private String name;
	private String type;
	private String url;
	private List<Input> inputs;
	private List<Parameter> parameters;
	private String submit;
	private List<Expectation> expectations;

	public Step() {	
		inputs = new ArrayList<Input>();
		expectations = new ArrayList<Expectation>();
		parameters = new ArrayList<Parameter>();
	}
	
	//clone constructor 
	public Step(Step original) {
	    this.name = new String(original.name);
	    this.type = new String(original.type);
	    this.url = new String(original.url);

	    this.inputs = new ArrayList<Input>();
	    if(original.getInputs().size() > 0) {
		    for (Input input : original.getInputs()) {
		    	this.inputs.add(new Input(input));
		    }
	    }
	    this.parameters = new ArrayList<Parameter>();
	    if(original.getParameters().size() > 0) {
		    for (Parameter parameter : original.getParameters()) {
		    	this.parameters.add(new Parameter(parameter));
		    }
	    }
	    
	    if(original.submit != null) {
	    	this.submit = new String(original.submit);
	    }
	    
		this.expectations = new ArrayList<Expectation>();
	    if(original.getExpectations().size() > 0) {
		    for (Expectation expectation : original.getExpectations()) {
		    	this.expectations.add(new Expectation(expectation));
		    }
	    }
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getInputCount() {
		return inputs.size();
	}

	public List<Expectation> getExpectations() {
		return expectations;
	}

	public void setExpectations(List<Expectation> expectations) {
		this.expectations.clear();
		this.expectations.addAll(expectations);
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs.clear();
		this.inputs.addAll(inputs);
		/*
	    if(inputs.size() > 0) {
	    	this.inputs.clear();
		    for (Input input : inputs) {
		    	this.inputs.add(new Input(input));
		    }
	    }
	    */
	}
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters.clear();
		this.parameters.addAll(parameters);
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSourceCount() {
		int count = 0;
		if(inputs != null) {
			for (Input input : inputs) {			
				if(input.isSource()) {
					count++;
				}
			}
		}
		return count;
	}

	public Parameter getParameter(String name) {
		
		for(Parameter parameter : parameters) {
			if(parameter.getName().equals(name)) {
				return parameter;
			}
		}
		return null;
	}
	
	public Input getInput(String name) {
		
		for(Input input : inputs) {
			if(input.getName().equals(name)) {
				return input;
			}
		}
		return null;
	}
	public Expectation getExpectation(String name) {
		
		for(Expectation expectation : expectations) {
			if(expectation.getName().equals(name)) {
				return expectation;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Step [name=" + name + ", type=" + type + ", url=" + url + ", inputs=" + inputs.toString() + ", parameters=" + parameters.toString() + ", submit=" + submit + ", expectations=" + expectations.toString() + "]";
	}
}