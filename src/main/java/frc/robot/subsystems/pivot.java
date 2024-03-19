package frc.robot.subsystems;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.Constants.pivotConstants;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

/** A robot arm subsystem that moves with a motion profile. */
public class pivot extends ProfiledPIDSubsystem {
  private final TalonFX m_motor_right = new TalonFX(pivotConstants.kMotorPort_Right);
  private final TalonFX m_motor_left = new TalonFX(pivotConstants.kMotorPort_Left);
  private final TalonFX m_encoder = m_motor_left;

  private final ArmFeedforward m_feedforward =
      new ArmFeedforward(
          pivotConstants.kSVolts, pivotConstants.kGVolts,
          pivotConstants.kVVoltSecondPerRad, pivotConstants.kAVoltSecondSquaredPerRad);

  public pivot() {
    super(
        new ProfiledPIDController(
            pivotConstants.kP,
            pivotConstants.kI,
            pivotConstants.kD,
            new TrapezoidProfile.Constraints(
                pivotConstants.kMaxVelocityRadPerSecond,
                pivotConstants.kMaxAccelerationRadPerSecSquared)),
        0);
    setGoal(pivotConstants.kPivotOffsetRads);
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {

    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);

    m_motor_right.setVoltage(output + feedforward);
    m_motor_left.setVoltage(output + feedforward);
  }

  @Override
  public double getMeasurement() {
    return (m_encoder.getDifferentialAveragePosition().getValueAsDouble() + pivotConstants.kPivotOffsetRads);
  }
}
