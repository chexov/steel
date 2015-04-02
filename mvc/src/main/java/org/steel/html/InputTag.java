package org.steel.html;

import org.steel.css.CSSRule;
import org.stjs.javascript.JSGlobal;
import org.stjs.javascript.functions.Function0;

public class InputTag<T extends InputTag<T>> extends Tag<T> {
	public InputTag(String tagName, CSSRule css) {
		super(tagName, css);
	}

	public T type(String value) {
		return attr("type", value);
	}

	public T disabled(String value) {
		return attr("disabled", value);
	}

	public T placeholder(String value) {
		return attr("placeholder", value);
	}

	public native T checked(Boolean value);

	public native T checked(Function0<Boolean> value);

	@SuppressWarnings("unchecked")
	protected T checked(Object value) {
		if (JSGlobal.typeof(value) == "function") {
			return dynAttr("checked", () -> ((Function0<Boolean>) value).$invoke() ? "checked" : "");
		}
		return attr("checked", (Boolean) value ? "checked" : "");
	}

	public native T value(String value);

	public native T value(Function0<String> value);

	protected T value(Object value) {
		return attr("value", value);
	}

}