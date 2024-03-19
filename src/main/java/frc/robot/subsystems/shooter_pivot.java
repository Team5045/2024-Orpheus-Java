package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooter_pivot extends SubsystemBase {

    private TalonFX L;
    private TalonFX R;

    private PIDController pivotPidController = new PIDController(5, 3, 0);
    private ArmFeedforward armFeedforward = new ArmFeedforward(0, 1.3, 0);
    // Values probably need some tuning for now...

    private double target;

    public shooter_pivot(TalonFX a, TalonFX b){
        // Constructs motor properites and target position initialized
        this.L = a;
        this.R = b;
        target = L.getPosition().getValueAsDouble();

    }

    public void set_pivot_voltage(double speedL, double speedR){
        L.setVoltage(speedL);
        R.setVoltage(speedR);
    }

    public void set_pivot_motors(double speedL, double speedR){
        L.set(speedL);
        R.set(speedR);
    }

    public void set_pivot_pos(double req_pos){
        L.setVoltage(armFeedforward.calculate(req_pos, 0) + pivotPidController.calculate(L.getPosition().getValueAsDouble(), req_pos));
        R.setVoltage((armFeedforward.calculate(req_pos, 0) + pivotPidController.calculate(L.getPosition().getValueAsDouble(), req_pos)));
    }

    @Override
    public void periodic(){
        set_pivot_pos(target);
    }

    public void set_target(double target) {
        this.target = target;
    }

    public double get_target() {
        return target;
    }

    public void changeTargetPos(double val) {
        target = MathUtil.clamp(target + val, 0.5, 0.9);
        // SUBSTITUTE THE VALUES FOR THIS, IT IS NOT FINAL 
    }
}
