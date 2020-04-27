package kmerrill285.featurescript.scripts.variables;

import java.util.HashMap;

public abstract class Variable<T> {
	public HashMap<String, T> values = new HashMap<String, T>();
	
	public String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public T getValue() {
		return getValue("");
	}
	
	public void setValue(T value) {
		setValue("", value);
	}
	
	public T getValue(String name) {
		if (values.size() == 1) {
			for (String str : values.keySet()) {
				return values.get(str);
			}
		}
		return values.get(name);
	}
	
	public void setValue(String name, T value) {
		if (values.size() == 1) {
			for (String str : values.keySet()) {
				values.put(str, value);
				return;
			}
		}
		if (!values.containsKey(name)) {
			System.err.println(name + " (typeof " + this.getClass().getName() + ") does not contain the value of " + name + "!");
			System.exit(1);
		}
		values.put(name, value);
	}
	
	public abstract void setValue(String name, String value);
	
	public void getPrintValue() {
		String str = "";
		str += name + " [";
		int i = 0;
		for (String name : values.keySet()) {
			T value = values.get(name);
			if (i < values.size() - 1) {
				str += name + ": " + value + ", ";
			} else {
				str += name + ": " + value + "]";
			}
			i++;
		}
		System.out.println(str);
	}
	
	public abstract Variable<Object> parse(String line);
}
