package base_classes;

import abstract_class_interface_impl.Burger;

public class NonVegBurger extends Burger {

	@Override
	public float price() {
		return 50.5f;
	}

	@Override
	public String name() {
		return "Chicken Burger";
	}
}
