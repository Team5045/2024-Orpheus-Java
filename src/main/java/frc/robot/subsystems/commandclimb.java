package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class commandclimb extends SubsystemBase{
    
    private TalonFX climb_motor;

    int DOWNWARD_LIMIT = 0;
    int UPWARD_LIMIT = 99;

    public commandclimb(TalonFX climb_motor) {
        this.climb_motor = climb_motor;
    }
    public void climbup(){
        climb_motor.set(0.1);
        // if(climb_motor.getPosition().getValue() > UPWARD_LIMIT){
        //     climb_motor.set(0.0);
        //     System.out.println("climb upper limit reached");
        // }
    }
    public void climbdown(){
        climb_motor.set(-0.1);
        // if(climb_motor.getPosition().getValue() > DOWNWARD_LIMIT){
        //     climb_motor.set(0.0);
        //     System.out.println("climb downward limit reached");
        // }
    }
    public void climbstop(){
        climb_motor.set(0);
    }
}
