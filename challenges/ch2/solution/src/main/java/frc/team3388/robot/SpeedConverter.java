package frc.team3388.robot;

public class SpeedConverter {

    private final double mShooterMaxSpeedMs;

    public SpeedConverter(double shooterMaxSpeedMs) {
        mShooterMaxSpeedMs = shooterMaxSpeedMs;
    }

    public double convertToRobotSpeeds(double speedMetersPerSecond) {
        return speedMetersPerSecond / mShooterMaxSpeedMs;
    }
}
