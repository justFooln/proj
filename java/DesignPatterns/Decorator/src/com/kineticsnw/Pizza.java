package com.kineticsnw;

// Blueprint for classes that will have decorators
// Note this is an interface instead of abstract class

public interface Pizza {

  public String getDescription();

  public double getCost();

}


/*

Bad approach using inheritance
public abstract class Pizza{


	public abstract void setDescription(String newDescription);
	public abstract String getDescription();

	public abstract void setCost(double newCost);
	public abstract double getCost();

	// I could use getter and setter methods for every
	// potential Pizza topping

}
*/