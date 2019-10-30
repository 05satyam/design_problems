package app;

import baseInterface.Shape;
import baseInterfaceImpl.Rectangle;
import baseInterfaceImpl.Square;
import factory.ShapeFactory;

public class Main {

	public static void main(String[] args) {

		ShapeFactory shape = new ShapeFactory();
		Shape obj1 = shape.getShapeClass(new Rectangle());
		System.out.println(obj1.draw());

		Shape obj2 = shape.getShapeClass( new Square());
		System.out.println(obj2.draw());
	}

}
