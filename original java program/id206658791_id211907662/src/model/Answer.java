package model;

import java.io.Serializable;
import java.util.Objects;

public class Answer implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String text;
	private boolean corecct;

	public Answer(String text, boolean corecct) {
		setText(text);
		setCorecct(corecct);
	}

	public String getText() {
		return text;
	}

	public boolean setText(String text) {
		this.text = text;
		return true;
	}

	public boolean getCorecct() {
		return corecct;
	}

	public boolean setCorecct(boolean corecct) {
		this.corecct = corecct;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		return Objects.equals(text, other.text);
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public Answer clone() throws CloneNotSupportedException {
		return (Answer) super.clone();
	}

}
