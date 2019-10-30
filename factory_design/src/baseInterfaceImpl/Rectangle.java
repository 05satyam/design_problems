package baseInterfaceImpl;

import baseInterface.Shape;

public class Rectangle implements Shape{
	
	@Override
	   public String draw() {
	      return ("Inside Rectangle::draw() method.");
	   }
}
