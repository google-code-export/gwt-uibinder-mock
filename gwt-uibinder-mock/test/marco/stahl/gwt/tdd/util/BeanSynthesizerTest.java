package marco.stahl.gwt.tdd.util;

import static org.junit.Assert.*;

import marco.stahl.gwt.tdd.util.BeanSynthesizer;

import org.junit.Before;
import org.junit.Test;

public class BeanSynthesizerTest {

	private MyBean bean;

	interface MyBean {
		String getName();
		void setName(String name);
		int getAge();
		void setAge(int age);
		String getUnknown();
	}
	
	
	@Before
	public void setUp() throws Exception {
		bean =  BeanSynthesizer.create(MyBean.class);
	}

	@Test
	public void testSetAndGetValues() {
		bean.setAge(31);
		bean.setName("Marco");
		assertEquals(31, bean.getAge());
		assertEquals("Marco", bean.getName());
	}

}
