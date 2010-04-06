package marco.stahl.gwt.tdd.util;

import static marco.stahl.gwt.tdd.util.DelegatorSynthesizer.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DelegatorSynthesizerTest {

	interface Triathlete {
		public String run();

		public String swim();
	}

	interface Runner {
		public String run();
	}

	interface Swimmer {
		public String swim();
	}

	@Mock
	Runner runner;

	@Mock
	Swimmer swimmer;
	
	@Mock
	Triathlete triathlete;

	private Triathlete synthesizedTriathlete;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(runner.run()).thenReturn("run");
		when(swimmer.swim()).thenReturn("swim");
		
		when(triathlete.run()).thenReturn("trun");
		when(triathlete.swim()).thenReturn("tswim");
	}

	@Test
	public void singleDelegateIsCalled() {
		synthesizedTriathlete = create(Triathlete.class, runner);
		String result = synthesizedTriathlete.run();		
		verify(runner).run();
		assertEquals("run", result);
	}
	
	@Test
	public void doesNothingAndReturnsNullIfNoMatchingDelegateIsFound() {
		synthesizedTriathlete = create(Triathlete.class, swimmer);
		String result = synthesizedTriathlete.run();
		assertNull(result);
		verify(swimmer,never()).swim();
	}
	
	@Test
	public void delegateToTheFirstMatchingDelegate() {
		synthesizedTriathlete = create(Triathlete.class, runner,swimmer,triathlete);
		String result = synthesizedTriathlete.swim();		
		verify(swimmer).swim();
		assertEquals("swim", result);
	}
	


}
