package janunit.model;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Expectation {

	private String name;
	private String value;
	private String element;
	
	@JsonCreator
	public Expectation(@JsonProperty("name") String name, @JsonProperty("element") String element, @JsonProperty("value") String value) {
		this.name = name;
		this.element = element;
		this.value = value;
	}
	
	public Expectation(Expectation original) {
		this.name = original.getName();
		this.element = original.getElement();
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

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getInputName() {
		String inputName = name;
		
		if(name.contains(".")) {
		    String[] split = name.split(Pattern.quote("."));
		    inputName = split[1];
		}
		return inputName;
	}
	
	@Override
	public String toString() {
		return "Expectation [name=" + name + ", value=" + value + ", element=" + element + "]";
	}
}
