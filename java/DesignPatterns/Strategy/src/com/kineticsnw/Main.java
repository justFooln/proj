package com.kineticsnw;

/**
 * Created by Derek Banas
 * Copyright, all rights reserved
 */
public class Main {

    public static void main(String[] args) {

      Animal sparky = new Dog();
      Animal tweety = new Bird();

      System.out.println("Dog: " + sparky.tryToFly());

      System.out.println("Bird: " + tweety.tryToFly());

      // This allows dynamic changes for flyingType
      // Note use of method defined in Animal to change flyingType attribute in Animal
      // Note this call changes nothing in the Dog class; happens during runtime!
      // Decoupling bliss!

      sparky.setFlyingAbility(new ItFlys());

      System.out.println("Dog: " + sparky.tryToFly());

    }
}
