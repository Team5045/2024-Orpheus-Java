// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.commandclimb;
import frc.robot.subsystems.passthrough;
import frc.robot.subsystems.shooter_cannon;
import frc.robot.Constants.*;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic(){
    XboxController joystick2 = new XboxController(0);
    commandclimb climber = new commandclimb(new TalonFX(Constants.climbconstants.CLIMB_MOTOR_ID));
    passthrough pass = new passthrough(new TalonFX(Constants.passthroughconstants.LEFT_PASS), new TalonFX(Constants.passthroughconstants.RIGHT_PASS));
    shooter_cannon shooter = new shooter_cannon(new TalonFX(Constants.cannonconstants.LEFT_SHOOT_ID), new TalonFX(Constants.cannonconstants.RIGHT_SHOOT_ID));

    // Climber: One single operation per-match
    if(joystick2.getYButtonReleased()) climber.climbup();
    else if(joystick2.getXButtonReleased()) climber.climbstop();

    // Passthrough
    if(joystick2.getLeftBumper()) pass.setspeed(-0.2);
    else if(joystick2.getRightBumper()) pass.setspeed(0.2);
    else pass.setspeed(0.0);
    
    // Shooter
    if(joystick2.getBackButtonReleased()) shooter.shootit();
    else if(joystick2.getStartButtonReleased()) shooter.stop();

    

  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
