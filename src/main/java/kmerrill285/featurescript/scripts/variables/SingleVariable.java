package kmerrill285.featurescript.scripts.variables;

public class SingleVariable extends Variable< Object > {

	public SingleVariable(String name) {
		super(name);
		this.values.put("value", 0.0f);
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
