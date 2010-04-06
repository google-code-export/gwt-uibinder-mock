package marco.stahl.gwt.tdd.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWTBridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MockitoGWTMockUtilities {
	 public static void disarm() {
		    GWTBridge bridge = new GWTWidgetBridge(); /** our change **/
		    setGwtBridge(bridge);
		  }
		 
		  public static void restore() {
		    setGwtBridge(null);
		  }
		 
		  private static void setGwtBridge(GWTBridge bridge) {
		    Class gwtClass = GWT.class;
		    Class[] paramTypes = new Class[] {GWTBridge.class};
		    Method setBridgeMethod = null;
		    try {
		      setBridgeMethod = gwtClass.getDeclaredMethod("setBridge", paramTypes);
		    } catch (NoSuchMethodException e) {
		      throw new RuntimeException(e);
		    }
		    setBridgeMethod.setAccessible(true);
		    try {
		      setBridgeMethod.invoke(gwtClass, new Object[] {bridge});
		    } catch (IllegalAccessException e) {
		      throw new RuntimeException(e);
		    } catch (InvocationTargetException e) {
		      throw new RuntimeException(e);
		    }
		  }
}
