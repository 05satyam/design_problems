package interfaces_impl;

import interfaces.Packaging;

public class Wrapper implements Packaging{

	@Override
	public String pack() {
		return "Wrapper";
	}

}
