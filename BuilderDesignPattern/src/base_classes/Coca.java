package base_classes;

import abstract_class_interface_impl.ColdDrink;

public class Coca extends ColdDrink {
	@Override
	public float price() {
		return 30.0f;
	}

	@Override
	public String name() {
		return "Coke";
	}
}
