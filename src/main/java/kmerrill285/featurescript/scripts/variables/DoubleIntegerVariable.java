package kmerrill285.featurescript.scripts.variables;

public class DoubleIntegerVariable extends Variable< Object > {

	public DoubleIntegerVariable(String name) {
		super(name);
		this.values.put("x", 0);
		this.values.put("y", 0);
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
