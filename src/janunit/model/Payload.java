package janunit.model;

//might want to add attributes like:
//  alert - this payload may generate an alert box, then we can check for the existence of one during the expectation analysis
//  etc
public enum Payload {
	LOW(1, "<74n_3v1l_p@yl0@d>"),
	MEDIUM(2, "script alert document cookie eval"),
	HIGH(3, "<>()'/\":;"),
	SURE(4, "alert(document.cookie)");//,
	//CERTAIN(5, "<script>alert(document.cookie)</script>");//,
	//DEFINITE(5, "\"><script>alert(\'74n_3v1l_p@yl0@d\')</script>");
	
	private int severity;
	private String payload;
	
	private Payload(int severity, String payload) {
		this.severity = severity;
		this.payload = payload;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public String getPayload() {
		return payload;
	}
}