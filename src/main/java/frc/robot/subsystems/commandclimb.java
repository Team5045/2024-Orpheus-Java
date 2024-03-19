package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class commandclimb extends SubsystemBase{
    
    private TalonFX climb_motor;

    public commandclimb(TalonFX climb_motor) {
        this.climb_motor = climb_motor;
    }

    public void climbup(){
        climb_motor.set(0.1);
    }
    public void climbdown(){
        climb_motor.set(-0.1);
    }
}
