package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class commandClimb extends SubsystemBase{
    
    private TalonFX climb_motor;

    int DOWNWARD_LIMIT = 0;
    int UPWARD_LIMIT = 99;

    public commandClimb(TalonFX climb_motor) {
        this.climb_motor = climb_motor;
    }
    public void climbUp(){
        climb_motor.set(0.1);
        // if(climb_motor.getPosition().getValue() > UPWARD_LIMIT){
        //     climb_motor.set(0.0);
        //     System.out.println("climb upper limit reached");
        // }
    }
    public void climbDown(){
        climb_motor.set(-0.1);
        // if(climb_motor.getPosition().getValue() > DOWNWARD_LIMIT){
        //     climb_motor.set(0.0);
        //     System.out.println("climb downward limit reached");
        // }
    }
    public void climbStop(){
        climb_motor.set(0);
    }
}
