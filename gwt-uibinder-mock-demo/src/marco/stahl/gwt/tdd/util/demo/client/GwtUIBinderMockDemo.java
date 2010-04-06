package marco.stahl.gwt.tdd.util.demo.client;

import marco.stahl.gwt.tdd.util.demo.client.SterniCalculatorModel.Gender;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtUIBinderMockDemo implements EntryPoint {
	public void onModuleLoad() {
		SterniCalculatorModel model = new SterniCalculatorModel(1, 80, Gender.MALE);
		RootPanel.get("app").add(new SterniCalculator(model).getView());
	}
}
