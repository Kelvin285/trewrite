package kmerrill285.featurescript.scripts.variables;

public class LongVariable extends Variable< Object > {

	public LongVariable(String name) {
		super(name);
		this.values.put("value", 0L);
	}

	@Override
	public Variable<Object> parse(String line) {
		return null;
	}

	@Override
	public void setValue(String name, String value) {
		setValue(name, Long.parseLong(value));
	}
}
