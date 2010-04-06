package marco.stahl.gwt.tdd.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class BeanSynthesizer implements InvocationHandler {
	private Map<String, Object> valuesByName = new HashMap<String, Object>();

	private BeanSynthesizer() {
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName = method.getName();
		if (methodName.startsWith("set") && args != null && args.length == 1) {
			setValue(methodName, args[0]);
		} else if (methodName.startsWith("get") && (args == null || args.length == 0)) {
			return getValue(methodName);
		}
		return null;
	}

	private Object getValue(String methodName) {
		return valuesByName.get(getAttributeName(methodName));
	}

	private void setValue(String methodName, Object value) {
		valuesByName.put(getAttributeName(methodName), value);
	}

	private String getAttributeName(String methodName) {
		return methodName.substring(3);
	}

	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { clazz },
				new BeanSynthesizer());
	}

}
