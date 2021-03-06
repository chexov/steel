package org.steel.example.todo.item;

import static org.steel.html.HTML.a;
import static org.steel.html.HTML.div;
import static org.steel.html.HTML.inputCheckbox;
import static org.steel.html.HTML.inputText;
import static org.steel.html.HTML.label;

import org.steel.html.HTMLTag;
import org.steel.html.InputTag;
import org.steel.model.Model;
import org.stjs.javascript.annotation.Template;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.functions.Callback1;

public class TodoItemView extends HTMLTag<TodoItemView> {
	@Template("property")
	private TodoItem item;

	private InputTag<?> input;

	@Template("property")
	private boolean editMode;

	private Callback1<TodoItem> ondeleteHandler;

	public TodoItemView() {
		super("li");
		Model.model(this);

		item = new TodoItem("", false);
		editMode = false;
	}

	public TodoItemView todo(TodoItem anItem) {
		item = anItem;
		return this;
	}

	@Override
	public TodoItemView appendTo(Element container) {
		TodoItemCSS css = (TodoItemCSS) getCssRule();
		// @formatter:off
		css(css.editing, () -> editMode);
		css(css.done, () -> item.done);

		html(
			div().css(css.view).html(
				inputCheckbox().css(css.toggle).checked(()->item.done).onclick(this::toggleDone), //
				label().css(css.label).text(() -> item.task).ondblclick(this::edit),//
				a().css(css.destroy).onclick(this::clear)),
			input = inputText().css(css.edit).value(() -> item.task).onblur(this::closeEdit).onkeypress(this::updateOnEnter));
		return super.appendTo(container);
		// @formatter:on
	}

	public void clear() {
		if (this.ondeleteHandler != null) {
			this.ondeleteHandler.$invoke(item);
		}
	}

	public TodoItemView ondelete(Callback1<TodoItem> ondelete) {
		this.ondeleteHandler = ondelete;
		return this;
	}

	public void toggleDone() {
		this.item.done = !this.item.done;
	}

	public void edit() {
		editMode = true;
	}

	public void closeEdit() {
		item.task = input.element().value;
		if (item.task == "") {
			clear();
		} else {
			editMode = false;
		}
	}

	public boolean updateOnEnter(DOMEvent ev) {
		if (ev.keyCode == 13) {
			this.closeEdit();
		}
		return true;
	}

}
