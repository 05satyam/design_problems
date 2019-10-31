package abstract_class_interface_impl;

import interfaces.Item;
import interfaces.Packaging;
import interfaces_impl.Bottle;

public abstract class ColdDrink implements Item {

	@Override
	public Packaging packaging() {
		return new Bottle();
	}

	@Override
	public abstract float price();
}
