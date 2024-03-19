package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.intakeConstants;

public class intake extends SubsystemBase{
    private final TalonFX m_motor = new TalonFX(intakeConstants.kMotorPort);

    public Command intakeCommand() {
        return run(() -> m_motor.set(0.3));
    }

    public Command outtakeCommand() {
        return run(() -> m_motor.set(-0.2));
    }
}
