package base_classes;

public class MealBuilder {
	public Meal prepareVegMeal (){
	      Meal meal = new Meal();
	      meal.addItem(new VegBurger());
	      meal.addItem(new Coca());
	      meal.addItem(new Dew());
	      return meal;
	   }   

	   public Meal prepareNonVegMeal (){
	      Meal meal = new Meal();
	      meal.addItem(new NonVegBurger());
	      meal.addItem(new Dew());
	      meal.addItem(new Coca());
	      return meal;
	   }
}
