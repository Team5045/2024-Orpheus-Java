package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class passthrough extends SubsystemBase{
    private TalonFX right_motor;
    private TalonFX left_motor;

    public passthrough(TalonFX L, TalonFX R){
        this.left_motor = L;
        this.right_motor = R;
    }
    public void setspeed(double speed){
        left_motor.set(speed);
        right_motor.set(speed);
    }
}
