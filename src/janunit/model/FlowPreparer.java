package janunit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import janunit.Helpers;
import janunit.config.Loader;

public class FlowPreparer {
	
	private final static Logger logger = Logger.getLogger(FlowPreparer.class.getName());
	
	public Collection<Object[]> prepareFlow(String flowName) {

		Collection<Object[]> data = new ArrayList<Object[]>();       
		
		List<Flow> flows = createFlows(flowName);
		
		for (Flow flow : flows) {		
			for (Step step : flow.getSteps()) {
				
				//sources have already been configured
				//so let's do the non-sources
				//all input values are references for values already sourced further up the chain

				logger.log(Level.INFO, "step: " + step.getName() + " :: input count: " + step.getInputCount());
				for(Input input : step.getInputs()) {
					logger.log(Level.FINE, input.toString());
					//if it's a source, the value has already been set and and the input has been marked as tainted 
					if(!input.isSource()) {
						logger.log(Level.FINEST, step.getName() + " " + input.getName() + " " + input.getValue());
						//does the value reference a step already set, or do we use the default value?
						if(flow.isReference(input.getValue())) {
							logger.log(Level.FINEST, flow.findInput(input.getValue()).getValue());
							input.setValue(flow.findInput(input.getValue()).getValue());
						}
					}
				}
				flow.updateStep(step);

				//this isn't quite right.  
				//we need to find the source of the input, it may be an input, it may be a parameter.  
				//just use for inputs for now though
				//ie for now, querystrings cannot be the source of tainted input
				for(Parameter parameter : step.getParameters()) {
					logger.log(Level.INFO, "prepare parameter:" + parameter.toString());
					if(flow.isReference(parameter.getValue())) {
						logger.log(Level.FINEST, flow.findInput(parameter.getValue()).getValue());
						parameter.setValue(flow.findInput(parameter.getValue()).getValue());	
					}						
				}
				flow.updateStep(step);				
				
				for(Expectation expectation : step.getExpectations()) {		
					logger.log(Level.INFO, expectation.toString());
					if(flow.findInput(step.getName(), expectation.getName()) != null) {
						logger.log(Level.INFO, "input: " + flow.findInput(step.getName(), expectation.getName()).getValue());
						expectation.setValue(Helpers.replaceToken(expectation.getValue(), expectation.getName(), flow.findInput(step.getName(), expectation.getName()).getValue()));
						logger.log(Level.INFO, "expectation: " + expectation.getValue());						
					}
				}
				flow.updateStep(step);
			}
			
			data.add(new Object[] { flow });
		}
		
		return data;
        
	}


	public List<Flow> createFlows(String flowName) {

		Flow defaultFlow = Loader.loadFlow(flowName);

		List<Flow> flows = new ArrayList<Flow>();

		flows.add(defaultFlow);
		
		//one flow for each source
		//times the number of payloads
		//if new inputs are introduced later in the flow, then they will use the default values from previous steps.		
		for (Step defaultStep : defaultFlow.getSteps()) {	
			if(defaultStep.getSourceCount() > 0) {
				for(int i = 0; i < defaultStep.getInputs().size(); i++) {
					if(defaultStep.getInputs().get(i).isSource()) {
						for (Payload payload : Payload.values()) {	
		
							Flow flow = new Flow(defaultFlow);
							Step step = new Step(defaultStep);
				
							//TODO do we want to add the full payload object so that we can refer to severity during the actual test run?  probably
							step.getInputs().get(i).setValue(payload.getPayload());
							step.getInputs().get(i).setTainted(true);
		
							logger.log(Level.INFO, "step: " + step.getName() + " tainting:" + step.getInputs().get(i).toString());
							
							flow.updateStep(step);
							
							flows.add(new Flow(flow));
				        }
					}
				}
			}		
		}
		logger.log(Level.INFO, "created: " + flows.size() + " flows");
		return flows;
	}
}