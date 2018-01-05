package janunit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Input {

	private String name;
	private String value;
	private boolean source;
	private boolean tainted;

	@JsonCreator
	public Input(@JsonProperty("name") String name, @JsonProperty("value") String value, @JsonProperty("source") boolean source) {
		this.setName(name);
		this.value = value;
		this.source = source;
		this.tainted = false;
	}

	public Input(Input original) {
		this.name = original.getName();
		this.value = original.getValue();
		this.source = original.isSource();
		this.tainted = original.isTainted();
	}

	public boolean isSource() {
		return source;
	}
	public void setSource(boolean source) {
		this.source = source;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isTainted() {
		return tainted;
	}
	public void setTainted(boolean tainted) {
		this.tainted = tainted;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Input [name=" + name + ", value=" + value + ", tainted=" + tainted + ", source=" + source + "]";
	}	



}
