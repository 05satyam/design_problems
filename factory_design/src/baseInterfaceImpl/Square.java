package baseInterfaceImpl;

import baseInterface.Shape;

public class Square implements Shape{
	
	@Override
	   public String draw() {
	      return ("Inside Square::draw() method.");
	   }
}
