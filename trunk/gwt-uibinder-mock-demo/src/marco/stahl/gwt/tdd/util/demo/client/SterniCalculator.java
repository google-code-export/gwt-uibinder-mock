package marco.stahl.gwt.tdd.util.demo.client;

import marco.stahl.gwt.tdd.util.demo.client.SterniCalculatorModel.Gender;
import marco.stahl.gwt.tdd.util.demo.client.util.CollectionUtils;
import marco.stahl.gwt.tdd.util.demo.client.utilwidgets.ValueListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SterniCalculator {

	private static SterniCalculatorWidgetUiBinder uiBinder = GWT
			.create(SterniCalculatorWidgetUiBinder.class);

	interface SterniCalculatorWidgetUiBinder extends
			UiBinder<Widget, SterniCalculator> {
	}

	@UiField
	ValueListBox<Integer> beers;

	@UiField
	TextBox weight;

	@UiField
	RadioButton male;
	@UiField
	RadioButton female;
	@UiField
	Label bac;

	private Widget view;

	private final SterniCalculatorModel model;

	public SterniCalculator(SterniCalculatorModel model) {
		view = uiBinder.createAndBindUi(this);
		beers.setValueRange(CollectionUtils.integerRange(1, 20));
		beers.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				onChangeBeers();
			}
		});
		
		this.model = model;
		initUiFromModel(model);
	}

	protected void refresh() {
		refreshModelFromUi();
		refreshBac();
	}

	private void refreshModelFromUi() {
		model.setBeers(beers.getValue());
		model.setGender(male.getValue() ? Gender.MALE : Gender.FEMALE);
	}

	private void initUiFromModel(SterniCalculatorModel model) {
		beers.setValue(model.getBeers());
		weight.setValue(String.valueOf(model.getWeight()));
		female.setValue(model.getGender() == Gender.FEMALE);
		male.setValue(model.getGender() == Gender.MALE);
		refreshBac();
	}

	private void refreshBac() {
		bac.setText(roundedBac() + " ‰");
	}

	private double roundedBac() {
		return Math.round(model.getBloodAlcoholConcentrationInPromille() * 10) / 10.0;
	}

	@UiHandler( { "male", "female" })
	void onChangeGender(ValueChangeEvent<Boolean> e) {
		refresh();
	}

	@UiHandler("weight")
	void onChange(KeyUpEvent e) {
	}

	void onChangeBeers() {
		refresh();
	}

	public Widget getView() {
		return view;
	}

}
