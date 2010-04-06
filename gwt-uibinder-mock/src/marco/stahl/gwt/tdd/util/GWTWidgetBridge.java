package marco.stahl.gwt.tdd.util;

import org.mockito.Mockito;

import com.google.gwt.core.client.GWTBridge;
import com.google.gwt.dev.About;
import com.google.gwt.uibinder.client.UiBinder;

public class GWTWidgetBridge extends GWTBridge {

	@Override
	public <T> T create(Class<?> classLiteral) {
		if (UiBinder.class.isAssignableFrom(classLiteral)) {
			return (T) MockedUIBinder.create(classLiteral);
		} else {
			return (T) Mockito.mock(classLiteral);
		}
	}

	@Override
	public String getVersion() {
		return About.getGwtVersionNum();
	}

	@Override
	public boolean isClient() {
		return false;
	}

	@Override
	public void log(String s, Throwable throwable) {
		System.out.println(s);
	}

}
