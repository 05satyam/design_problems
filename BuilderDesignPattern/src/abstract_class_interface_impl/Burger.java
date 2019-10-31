package abstract_class_interface_impl;

import interfaces.Item;
import interfaces.Packaging;
import interfaces_impl.Wrapper;

public abstract class Burger implements Item {
	@Override
	public Packaging packaging() {
		return new Wrapper();
	}

	@Override
	public abstract float price();
}
