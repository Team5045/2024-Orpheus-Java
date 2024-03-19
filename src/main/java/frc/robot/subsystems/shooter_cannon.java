package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.shooterConstants;

public class shooter_cannon extends SubsystemBase{
    private final TalonFX m_motor_right = new TalonFX(shooterConstants.kMotorPort_Right);
    private final TalonFX m_motor_left = new TalonFX(shooterConstants.kMotorPort_Right);

    public Command shoot() {
        return run(() -> m_motor_right.set(0.3)).alongWith(run(() -> m_motor_left.set(0.3)));

    }

    public Command unshoot() {
        return run(() -> m_motor_right.set(-0.3)).alongWith(run(() -> m_motor_left.set(-0.3)));
    }
}
