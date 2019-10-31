package base_classes;

import abstract_class_interface_impl.ColdDrink;

public class Dew extends ColdDrink {
	@Override
	public float price() {
		return 35.0f;
	}

	@Override
	public String name() {
		return "Dew";
	}
}
