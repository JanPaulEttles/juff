package janunit.flow;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import janunit.JUFFBaseFlow;
import janunit.model.Flow;
import janunit.model.FlowPreparer;


@RunWith(Parameterized.class)
public class JUFFFlowViewNote
	extends JUFFBaseFlow {
	
	@Parameterized.Parameters
    public static Collection<Object[]> data() {
    	return new FlowPreparer().prepareFlow("viewnote");
    }

    public JUFFFlowViewNote(Flow flow) {
    	super(flow);
    }
}