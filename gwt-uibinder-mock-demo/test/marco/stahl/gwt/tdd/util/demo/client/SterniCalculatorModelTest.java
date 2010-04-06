package marco.stahl.gwt.tdd.util.demo.client;

import static org.junit.Assert.*;
import marco.stahl.gwt.tdd.util.demo.client.SterniCalculatorModel.Gender;

import org.junit.Before;
import org.junit.Test;

public class SterniCalculatorModelTest {

	private static final double delta = 0.01;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetBloodAlcoholConcentrationInPromille() {
		assertEquals(0.0, bac(0, 80, Gender.MALE), delta);
		assertEquals(0.459558, bac(1, 80, Gender.MALE), delta);
		assertEquals(1.754385964, bac(2, 50, Gender.FEMALE), delta);
	}
	
	@Test
	public void modifiedValuesChangesBloodAlcoholConcentration() {
		SterniCalculatorModel model = new SterniCalculatorModel(1, 80, Gender.MALE);
		model.setBeers(4);
		model.setGender(Gender.FEMALE);
		model.setWeight(90);
		assertEquals(1.94931773, model.getBloodAlcoholConcentrationInPromille(), delta);
	}

	private double bac(int beers, int weight, Gender gender) {
		return new SterniCalculatorModel(beers, weight, gender)
				.getBloodAlcoholConcentrationInPromille();
	}

}
