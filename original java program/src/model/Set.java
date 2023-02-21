package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Set<T> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private ArrayList<T> set;

	public Set() {
		set = new ArrayList<T>();
	}

	public boolean contains(T t) {
		return set.contains(t);
	}

	public boolean add(T t) {
		if (set.contains(t))
			return false;
		return set.add(t);
	}

	public int size() {
		return set.size();
	}

	public boolean remove(int index) {
		T t = set.get(index);
		return set.remove(t);
	}

	public T get(int index) {
		return set.get(index);
	}

	@Override
	public boolean equals(Object obj) {
		return set.equals(obj);
	}

	@Override
	public String toString() {
		return set.toString();
	}

	@Override
	public Set clone() throws CloneNotSupportedException {
		return (Set) super.clone();
	}

}
