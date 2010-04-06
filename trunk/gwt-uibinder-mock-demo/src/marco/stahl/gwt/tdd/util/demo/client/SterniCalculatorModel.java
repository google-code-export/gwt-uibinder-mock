package marco.stahl.gwt.tdd.util.demo.client;

public class SterniCalculatorModel {
	private static final double BEER_ALC_CONTENT_IN_PERCENT = 0.05;
	private static final double BEER_WEIGHT = 0.5;
	private static final double ALC_PER_BEER = BEER_ALC_CONTENT_IN_PERCENT*BEER_WEIGHT;
	
	private int beers;
	private int weight;
	private Gender gender;

	public enum Gender {
		MALE,FEMALE;
	}
	
	public SterniCalculatorModel(int beers,int weight,Gender gender) {
		this.beers = beers;
		this.weight = weight;
		this.gender = gender;
	}
	
	public double getBloodAlcoholConcentrationInPromille() {
		return alcMass()/(weight*reductionFactor())*1000;
	}

	private double reductionFactor() {
		return gender == Gender.MALE ? 0.69 : 0.57;
	}

	private double alcMass() {
		return ALC_PER_BEER*beers;
	}

	public int getBeers() {
		return beers;
	}

	public int getWeight() {
		return weight;
	}

	public Gender getGender() {
		return gender;
	}

	public void setBeers(int beers) {
		this.beers = beers;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	
}
