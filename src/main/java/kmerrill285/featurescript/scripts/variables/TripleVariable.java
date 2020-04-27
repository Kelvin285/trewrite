package kmerrill285.featurescript.scripts.variables;

public class TripleVariable extends Variable< Object > {

	public TripleVariable(String name) {
		super(name);
		this.values.put("x", 0.0f);
		this.values.put("y", 0.0f);
		this.values.put("z", 0.0f);
	}

	@Override
	public Variable<Object> parse(String line) {
		return null;
	}
	
	@Override
	public void setValue(String name, String value) {
		setValue(name, Double.parseDouble(value));
	}
}
