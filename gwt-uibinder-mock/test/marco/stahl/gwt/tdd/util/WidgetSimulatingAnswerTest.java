package marco.stahl.gwt.tdd.util;

import static org.junit.Assert.*;
import marco.stahl.gwt.tdd.util.WidgetSimulatingAnswer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class WidgetSimulatingAnswerTest {

	private MyBean bean;

	interface MyBean extends HasValue<String>{
		String getName();
		void setName(String name);
		int getAge();
		void setAge(int age);
		String getUnknown();
	}
	
	
	@Before
	public void setUp() throws Exception {
		bean = Mockito.mock(MyBean.class,new WidgetSimulatingAnswer());
	}

	@Test
	public void testSetAndGetGeneralBeanValues() {
		bean.setAge(31);
		bean.setName("Marco");
		assertEquals(31, bean.getAge());
		assertEquals("Marco", bean.getName());
	}

	@Test
	public void testSetValuesForHasValues() {
		bean.setValue("Hola",false);
		assertEquals("Hola", bean.getValue());
	}
	
	@Test
	public void testSetValuesAndFiringChangeValueHandler() {
		final String[] result = new String[1];
		HandlerRegistration handlerRegistration = bean.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				result[0]=bean.getValue();
			}
		});
		assertNotNull(handlerRegistration);
		bean.setValue("Hola",true);
		assertEquals("Hola", result[0]);
	}
	
}
