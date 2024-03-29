// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.commandclimb;
import frc.robot.subsystems.shooter_cannon;
import frc.robot.subsystems.shooter_pivot;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.passthrough;

public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  // /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain
  private final shooter_cannon shooter = new shooter_cannon(new TalonFX(16), new TalonFX(15));
  private final commandclimb climb = new commandclimb(new TalonFX(20));
  private final intake intake = new intake(new TalonFX(19));
  private final passthrough passthrough = new passthrough(new TalonFX(14), new TalonFX(13));
  private final shooter_pivot pivot = new shooter_pivot(new TalonFX(18), new TalonFX(17));
  private final TalonFX L = new TalonFX(18);
  private final TalonFX R = new TalonFX(17);

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  // private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  // private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    // joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
    // joystick.b().whileTrue(drivetrain
    //     .applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));
    // reset the field-centric heading on left bumper press

    joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    // CLIMB + (UP) BINDING
    joystick.x().onTrue(new InstantCommand(
          () -> {
            System.out.println("CLIMB MODE CHANGED TOGGLE");
            climb.climbup();
          }, climb));
    // NOTE: Climb still needs some fixes, maybe add PID, but this is a one-time use case

    // INTAKE + PASSTHROUGH UPTAKE BINDINGS
    joystick.leftBumper().onTrue(new RunCommand(() -> intake.setspeed(0.4), intake));
    joystick.leftBumper().onTrue(new RunCommand(() -> passthrough.setspeed(-0.2), passthrough));
    joystick.leftBumper().onFalse(new RunCommand(() -> intake.setspeed(0.0), intake));
    joystick.leftBumper().onFalse(new RunCommand(() -> passthrough.setspeed(0.0), passthrough));

    // INTAKE + PASSTHROUGH EJECTION BINDINGS
    joystick.rightBumper().onTrue(new RunCommand(() -> intake.setspeed(-0.4), intake));
    joystick.rightBumper().onTrue(new RunCommand(() -> passthrough.setspeed(0.2), passthrough));
    joystick.rightBumper().onFalse(new RunCommand(() -> intake.setspeed(0.0), intake));
    joystick.rightBumper().onFalse(new RunCommand(() -> passthrough.setspeed(0.0), passthrough));
    
    // CANNON CONTROL BINDINGS
    joystick.y().onTrue(new InstantCommand(() -> shooter.shootit(), shooter));
    joystick.y().onFalse(new InstantCommand(() -> shooter.stop(), shooter));

    // PIVOT CONTROL BINDINGS
    // joystick.leftTrigger().whileTrue(new RunCommand(() -> pivot.changeTargetPos(-0.03)));
    // joystick.rightTrigger().whileTrue(new RunCommand(() -> pivot.changeTargetPos(0.03)));
    // Basically left -0.03 to the setpoint while right adds 0.03 to the setpoint
    // Limits should also be bounded correctly once tuned :)
    System.out.println(L.getPosition());
    System.out.println(R.getPosition());

  }
  public RobotContainer() {
    configureBindings();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
