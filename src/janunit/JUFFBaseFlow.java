package janunit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import janunit.model.Expectation;
import janunit.model.Flow;
import janunit.model.Input;
import janunit.model.Step;


public abstract class JUFFBaseFlow
	extends JanUnitBaseFirefox {

	private final static Logger logger = Logger.getLogger(JUFFBaseFlow.class.getName());

    private Flow flow;
    public JUFFBaseFlow(Flow flow) {
        this.flow = flow;
    }

	@Test
	public void testFlow() throws InterruptedException {	
		
		for (Step step : flow.getSteps()) {			
			logger.log(Level.INFO, step.toString()); 

			switch (step.getType()) {
				case "form":
					processForm(step);
					break;
				case "get":
					processGet(step);
					break;
				default:
					throw new IllegalArgumentException("Invalid step type");
			}
			processExpectations(step);
		}
	}

	private void processGet(Step step) throws InterruptedException {
		navigateAndSubmitGet(buildURL(flow.getUrl(), step.getUrl(), step.getParameters()));
	}

	private void processForm(Step step) throws InterruptedException {		
		navigateAndSubmitForm(buildURL(flow.getUrl(), step.getUrl(), step.getParameters()), step.getInputs(), step.getSubmit());
	}

	private void processExpectations(Step step) throws InterruptedException {
		
		/*
		String alert = checkAlert();
		if(!alert.equals("NO_ALERT_MESSAGE")) {
			logger.log(Level.INFO, "Payload generated an alert: " + alert);
			fail("\nStep: " + step.getName() + " generated a alert.\nValue returned: " + alert);
		}
		
		else {*/
			for(Expectation expectation : step.getExpectations()) {
				logger.log(Level.INFO, "Result: " + waitForResultSource(expectation.getElement()));
				if(flow.findInput(step.getName(), expectation.getName()) != null) {					
					logger.log(Level.INFO, "Step: " + step.getName() + " : : " + expectation.getInputName() + " : : " + flow.findInput(step.getName(), expectation.getName()).getName());
	
					if(flow.findInput(step.getName(), expectation.getName()).isTainted()) {
						if(waitForResultSource(expectation.getElement()).toLowerCase().contains(flow.findInput(step.getName(), expectation.getName()).getValue().toLowerCase())) {
							fail("\nInput : " + flow.findInput(step.getName(), expectation.getName()).getName() + " is not being validated in Step: " + step.getName() + ".\nValue returned: " + waitForResultSource(expectation.getElement()));
		    			}
					}
		        	else {
		        		//input is not tainted, so pass if the expectation is met
		        		assertThat(waitForResultSource(expectation.getElement()), containsString(expectation.getValue()));
		        	}
				}
				else {
					//there is no corresponding input, so just check the expectation is there
					//logger.log(Level.INFO, "Step: " + step.getName() + " : no input : " + expectation.getInputName());
					assertThat(waitForResultSource(expectation.getElement()), containsString(expectation.getValue()));
				}
			}
		//}
	}
}