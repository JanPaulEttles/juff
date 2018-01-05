package janunit.config;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import janunit.model.Flow;
import janunit.model.FlowPreparer;
import janunit.model.Step;

public class Loader {

	private final static Logger logger = Logger.getLogger(Loader.class.getName());
	
	public static Flow loadFlow(String flowName) {
		logger.log(Level.INFO, "flows" + File.separator + flowName + File.separator + "flow." + flowName + ".json");
		
		Flow flow = new Flow();
		
		try {
			ObjectMapper mapper = new ObjectMapper();


			FlowConfig flowConfig = mapper.readValue(new File("flows" + File.separator + flowName + File.separator + "flow." + flowName + ".json"), FlowConfig.class);
			
			flow.setName(flowConfig.getName());
			flow.setUrl(flowConfig.getUrl());
			
			for (String stepName : flowConfig.getSteps()) {
				Step defaultStep = Loader.loadStep(flowName, stepName);
				flow.addStep(defaultStep);			
			}
		} 
		catch (JsonGenerationException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		return flow;
	}

	private static Step loadStep(String flowName, String stepName) {
		logger.log(Level.INFO, "flows" + File.separator + flowName + File.separator + "step." + stepName + ".json");
		
		Step step = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			step = mapper.readValue(new File("flows" + File.separator + flowName + File.separator + "step." + stepName + ".json"), Step.class);
		} 
		catch (JsonGenerationException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		return step;
	}

}
