package marco.stahl.gwt.tdd.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

public class MockedUIBinder {
	
	private Map<String,Field> fieldsByName = new HashMap<String, Field>();
	
	private MockedUIBinder() {
	}

	public Object createAndBindUi(Object owner)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		mockUiFields(owner);
		bindHandler(owner);
		return Mockito.mock(Widget.class);
	}

	private void bindHandler(final Object owner) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		for (final Method method : owner.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(UiHandler.class)) {
				method.setAccessible(true);
				UiHandler annotation = method.getAnnotation(UiHandler.class);
				System.out.println(method.getName());
				Class<?> parameterClass = method.getParameterTypes()[0];
				Object handler = null;
				if (ValueChangeEvent.class.isAssignableFrom(parameterClass)) {
					handler = new ValueChangeHandler() {

						@Override
						public void onValueChange(ValueChangeEvent event) {
							try {
								method.invoke(owner, new Object[]{event});
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					};
					for (String uiFieldName : annotation.value()) {
						Field field = fieldsByName.get(uiFieldName);
						HasValueChangeHandlers fieldObject = (HasValueChangeHandlers) field.get(owner);
						fieldObject.addValueChangeHandler((ValueChangeHandler) handler);
					}
				};
			}
		}
		
	}

	private void mockUiFields(Object owner) throws IllegalArgumentException,
			IllegalAccessException {
		for (Field field : owner.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(UiField.class)) {
				field.setAccessible(true);
				field.set(owner, mockUiField(field));
				fieldsByName.put(field.getName(), field);
			}
		}
	}

	private Object mockUiField(Field field) {
		Class<?> type = field.getType();
		return Mockito.mock(type, new WidgetSimulatingAnswer());
	}

	@SuppressWarnings("unchecked")
	public static UiBinder create(Class clazz) {
		return DelegatorSynthesizer.create(clazz, new MockedUIBinder());
	}

}
