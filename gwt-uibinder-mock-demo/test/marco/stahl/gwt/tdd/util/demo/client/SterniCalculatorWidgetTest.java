package marco.stahl.gwt.tdd.util.demo.client;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import marco.stahl.gwt.tdd.util.MockitoGWTMockUtilities;
import marco.stahl.gwt.tdd.util.demo.client.SterniCalculatorModel.Gender;

import org.junit.Before;
import org.junit.Test;

public class SterniCalculatorWidgetTest {

	private SterniCalculatorModel model;
	private SterniCalculator widget;

	@Before
	public void setUp() throws Exception {
		MockitoGWTMockUtilities.disarm();
		model = new SterniCalculatorModel(2, 80, Gender.FEMALE);
	}

	@Test
	public void writesInitiallyInputValuesToUI() throws Exception {
		widget = new SterniCalculator(model);
		// verify the method calls
		verify(widget.beers).setValueRange(anyList());
		verify(widget.beers).setValue(2);
		verify(widget.weight).setValue("80");
		verify(widget.female).setValue(true);

		// or assert directly the final values
		assertEquals(20, widget.beers.getValueRange().size());
		assertEquals("80", widget.weight.getValue());
		assertEquals(false, widget.male.getValue());
	}

	@Test
	public void writesInitialBAC() throws Exception {
		widget = new SterniCalculator(model);
		assertEquals("1.1 ‰", widget.bac.getText());
	}

@Test
public void changingNumberOfBeersChangesBloodAlcoholConcentration() throws Exception {
	widget = new SterniCalculator(model);
	reset(widget.bac);
	
	widget.beers.setValue(4, true);
	assertEquals((Integer) 4, widget.beers.getValue());
	
	// verify the method call
	verify(widget.bac).setText(anyString());
	
	// or verify the final value
	assertEquals("2.2 ‰", widget.bac.getText());
}

	@Test
	public void changingGenderChangesBAC() throws Exception {
		widget = new SterniCalculator(model);
		reset(widget.bac);
		
		widget.male.setValue(true, true);
		
		verify(widget.bac).setText(anyString());
	}

}
