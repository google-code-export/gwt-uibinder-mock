package marco.stahl.gwt.tdd.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DelegatorSynthesizer implements InvocationHandler {

	private final Object[] delegates;

	private DelegatorSynthesizer(Object... delegates) {
		this.delegates = delegates;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		for (Object delegate : delegates) {
			Class<?> delegatesClass = delegate.getClass();
			try {
				Method delegateMethod = delegatesClass.getMethod(method
						.getName(), method.getParameterTypes());
				return delegateMethod.invoke(delegate, args);
			} catch (NoSuchMethodException e) {
				// just ignore it
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> clazz, Object... delegates) {
		ClassLoader classLoader = clazz.getClassLoader();
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { clazz },
				new DelegatorSynthesizer(delegates));
	}

}
