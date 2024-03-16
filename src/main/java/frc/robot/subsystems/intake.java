package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intake extends SubsystemBase{
    private TalonFX intakemotor;

    public intake(TalonFX s){
        this.intakemotor = s;
    }
    public void setspeed(double speed){
        intakemotor.set(speed);
    }
}