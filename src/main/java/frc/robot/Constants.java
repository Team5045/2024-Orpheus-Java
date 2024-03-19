package frc.robot;

public final class Constants {

    // motor ids for the climber :)
    public final static class climbConstants{
        public static final int CLIMB_MOTOR_ID = 19;
    }

    public final static class shooterConstants{
        public static final int kMotorPort_Right = 15;
        public static final int kMotorPort_Left = 16;
    }

    public final static class passthroughConstants{
        public static final int kMotorPort_Right = 13;
        public static final int kMotorPort_Left = 14;
    }

    public final static class intakeConstants {
        public static final int kMotorPort = 19;
    }
    
    public static final class pivotConstants {
        public static final int kMotorPort_Right = 17;
        public static final int kMotorPort_Left = 18;
    
        public static final double kP = 3;
        public static final double kI = 0;
        public static final double kD = 0;
    
        public static final double kSVolts = 0;
        public static final double kGVolts = 0.18;
        public static final double kVVoltSecondPerRad = 0.5;
        public static final double kAVoltSecondSquaredPerRad = 0.1;
    
        public static final double kMaxVelocityRadPerSecond = 3;
        public static final double kMaxAccelerationRadPerSecSquared = 10;

        public static final double kPivotOffsetRads = 0.5;
    }
}
