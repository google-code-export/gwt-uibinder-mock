package marco.stahl.gwt.tdd.util.demo.client.utilwidgets;

import java.util.Collection;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class ValueListBox<T> extends Composite implements HasValue<T> {
	private final ListBox listBox;
	private int idCounter = 0;

	private List<T> valueRange;
	private boolean valueChangeHandlerInitialized;

	public ValueListBox() {
		listBox = new ListBox();
		initWidget(listBox);
	}

	public void setValueRange(List<T> valueRange) {
		this.valueRange = valueRange;
		listBox.clear();
		for (T t : valueRange) {
			String id = String.valueOf(idCounter++);
			listBox.addItem(String.valueOf(t), id);
		}
	}

	public Collection<T> getValueRange() {
		return valueRange;
	}

	@Override
	public T getValue() {
		return valueRange.get(listBox.getSelectedIndex());
	}

	@Override
	public void setValue(T value) {
		setValue(value, false);
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		T oldValue = getValue();
		listBox.setSelectedIndex(valueRange.indexOf(value));
		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<T> handler) {
	    if (!valueChangeHandlerInitialized) {
	        valueChangeHandlerInitialized = true;
	        listBox.addChangeHandler(new ChangeHandler() {
	          public void onChange(ChangeEvent event) {
	            ValueChangeEvent.fire(ValueListBox.this, getValue());
	          }
	        });
	      }
		return addHandler(handler, ValueChangeEvent.getType());
	}


}
