// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import frc.robot.subsystems.commandClimb;
// import frc.robot.subsystems.passthrough;
// import frc.robot.subsystems.shooter_cannon;
// import frc.robot.subsystems.intake;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private TalonFX m_climbMotor;

  private TalonFX m_climbEncoder;

  private TalonFX m_pivotMotor_Right;
  private TalonFX m_pivotMotor_Left;

  private static double kDt = 0.02;
  private static double kMaxVelocity = 1.75;
  private static double kMaxAcceleration = 0.75;
  private static double kP = 1.3;
  private static double kI = 0.0;
  private static double kD = 0.7;
  private static double kS = 1.1;
  private static double kG = 1.2;
  private static double kV = 1.3;

  private final TrapezoidProfile.Constraints m_constraints = new TrapezoidProfile.Constraints(kMaxVelocity, kMaxAcceleration);
  private final ProfiledPIDController m_climbController = new ProfiledPIDController(kP, kI, kD, m_constraints, kDt);
  private final ElevatorFeedforward m_feedForward = new ElevatorFeedforward(kS, kG, kV);

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    m_climbMotor = new TalonFX(20);

    m_pivotMotor_Right = new TalonFX(17);
    m_pivotMotor_Left = new TalonFX(18);

    m_climbEncoder = m_climbMotor;
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
    System.out.println(m_pivotMotor_Left.getPosition());
    // if (joystick2.getXButton()){
    //   m_intakeMotor.set(0.5);
    // }
//     if (joystick2.getYButton()){
//       m_climbController.setGoal(1);
//     } else if (joystick2.getAButton()){
//       m_climbController.setGoal(0);
//     }
//     m_climbMotor.setVoltage(
//       m_climbController.calculate(m_climbEncoder.getDifferentialAveragePosition().getValueAsDouble())
//       + m_feedForward.calculate(m_climbController.getSetpoint().velocity));
}

  @Override
  public void teleopExit() {

  }

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
