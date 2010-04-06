package marco.stahl.gwt.tdd.util;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class HasValueMock<T> implements HasValue<T> {
	T value;
	
	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(T value) {
		setValue(value,false);
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		this.value = value;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<T> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		// TODO Auto-generated method stub
		
	}

}
