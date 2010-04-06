package marco.stahl.gwt.tdd.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

@SuppressWarnings("unchecked")
public class WidgetSimulatingAnswer implements Answer, HasValueChangeHandlers {
	private Map<String, Object> valuesByName = new HashMap<String, Object>();
	private HandlerManager handlerManager;

	@Override
	public Object answer(InvocationOnMock invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] args = invocation.getArguments();
		String methodName = method.getName();
		if (methodName.startsWith("set") && args != null && args.length == 1) {
			setValueByMethodName(methodName, args[0]);
		} else if (methodName.startsWith("get")
				&& (args == null || args.length == 0)) {
			return getValue(methodName);
		} else if (methodName.equals("setValue") && args != null
				&& args.length == 2) {
			setValueByMethodName(methodName, args[0]);
			if ((Boolean) args[1]) {
				ValueChangeEvent.fire(this, args[0]);
			}
		} else if (methodName.equals("addValueChangeHandler") && args != null
				&& args.length == 1) {
			return addValueChangeHandler((ValueChangeHandler) args[0]);
		}
		return null;
	}

	HandlerManager ensureHandlers() {
		return handlerManager == null ? handlerManager = new HandlerManager(
				this) : handlerManager;
	}

	protected final <H extends EventHandler> HandlerRegistration addHandler(
			final H handler, GwtEvent.Type<H> type) {
		return ensureHandlers().addHandler(type, handler);
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	private Object getValue(String methodName) {
		return valuesByName.get(getAttributeName(methodName));
	}

	private void setValueByMethodName(String methodName, Object value) {
		valuesByName.put(getAttributeName(methodName), value);
	}

	private String getAttributeName(String methodName) {
		return methodName.substring(3);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if (handlerManager != null) {
			handlerManager.fireEvent(event);
		}
	}
}
