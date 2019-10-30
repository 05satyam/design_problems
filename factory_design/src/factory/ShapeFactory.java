package factory;

import baseInterface.Shape;
import baseInterfaceImpl.Rectangle;
import baseInterfaceImpl.Square;

public class ShapeFactory {

	public Shape getShapeClass(Shape obj) {
		if (obj instanceof Rectangle) {
			return new Rectangle();
		}
		if (obj instanceof Square) {
			return new Square();
		}
		return null;
	}
}
