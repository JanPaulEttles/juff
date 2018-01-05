package janunit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameter {

	private String name;
	private String value;

	@JsonCreator
	public Parameter(@JsonProperty("name") String name, @JsonProperty("value") String value) {
		this.setName(name);
		this.value = value;
	}

	public Parameter(Parameter original) {
		this.name = original.getName();
		this.value = original.getValue();
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Input [name=" + name + ", value=" + value + "]";
	}	
}
