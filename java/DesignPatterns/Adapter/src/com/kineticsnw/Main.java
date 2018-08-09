package com.kineticsnw;

public class Main {

    public static void main(String[] args) {

      // Create a tank, a robot, and the robot adapter so it can be used like a tank
      EnemyTank rx7Tank = new EnemyTank();
      EnemyRobot fredTheRobot = new EnemyRobot();
      EnemyAttacker robotAdapter = new EnemyRobotAdapter(fredTheRobot);

      //--------- Exercise the robot
      System.out.println("The Robot");
      fredTheRobot.reactToHuman("Paul");
      fredTheRobot.walkForward();
      fredTheRobot.smashWithHands();
      System.out.println();

      //----------Exercise the tank
      System.out.println("The Enemy Tank");
      rx7Tank.assignDriver("Frank");
      rx7Tank.driveForward();
      rx7Tank.fireWeapon();
      System.out.println();

      //---------- Exercise the robot using the adapter
      System.out.println("The Robot with Adapter");
      robotAdapter.assignDriver("Mark");
      robotAdapter.driveForward();
      robotAdapter.fireWeapon();

    }
}
