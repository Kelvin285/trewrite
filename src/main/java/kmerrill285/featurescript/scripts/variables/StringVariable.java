package kmerrill285.featurescript.scripts.variables;

public class StringVariable extends Variable< Object > {

	public StringVariable(String name) {
		super(name);
		this.values.put("value", "");
	}

	@Override
	public Variable<Object> parse(String line) {
		return null;
	}
	
	@Override
	public void setValue(String name, String value) {
		setValue(name, value);
	}

}
