package interfaces_impl;

import interfaces.Packaging;

public class Bottle implements Packaging{

	@Override
	public String pack() {
		return "Bottle";
	}

}
