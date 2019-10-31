/**
 * Builder pattern builds a complex object using simple objects and 
 * using a step by step approach. This type of design pattern comes under 
 * creational pattern as this pattern provides one of the best ways to create an object.
 * 
 * 
 * here, MealBuilder a complex object is assorted form of meal which itself comprises if Verburger and nonvegburger, dew, coca object
 * Like this there are many small objects which lead to creation of a complex and much bigger object Mealbuilder
 * 
 */

package base_classes;

public class Main {
	public static void main(String[] args) {

		MealBuilder mealBuilder = new MealBuilder();

		Meal vegMeal = mealBuilder.prepareVegMeal();
		System.out.println("Veg Meal");
		vegMeal.showItems();
		System.out.println("Total Cost: " + vegMeal.getCost());

		Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
		System.out.println("\n\nNon-Veg Meal");
		nonVegMeal.showItems();
		System.out.println("Total Cost: " + nonVegMeal.getCost());
	}
}
