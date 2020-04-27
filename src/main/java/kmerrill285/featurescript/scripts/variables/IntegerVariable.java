package kmerrill285.featurescript.scripts.variables;

public class IntegerVariable extends Variable< Object > {

	public IntegerVariable(String name) {
		super(name);
		this.values.put("value", 0);
	}

	@Override
	public Variable<Object> parse(String line) {
		return null;
	}
	
	@Override
	public void setValue(String name, String value) {
		setValue(name, Integer.parseInt(value));
	}

}
