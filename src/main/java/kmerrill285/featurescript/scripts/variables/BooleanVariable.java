package kmerrill285.featurescript.scripts.variables;

public class BooleanVariable extends Variable< Object > {

	public BooleanVariable(String name) {
		super(name);
		this.values.put("value", false);
	}

	@Override
	public Variable<Object> parse(String line) {
		return null;
	}

	@Override
	public void setValue(String name, String value) {
		setValue(name, Boolean.parseBoolean(value));
	}

}
