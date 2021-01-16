package frc.team3388.robot.actions;

import com.flash3388.flashlib.scheduling.actions.ActionBase;
import com.jmath.vectors.Vector2;
import frc.team3388.robot.FiringAlgorithm;
import frc.team3388.robot.SpeedConverter;
import frc.team3388.robot.subsystems.ShooterSystem;

public class ShootAtAction extends ActionBase {

    private final ShooterSystem mShooterSystem;
    private final FiringAlgorithm mFiringAlgorithm;
    private final SpeedConverter mSpeedConverter;
    private final Vector2 mTarget;

    public ShootAtAction(ShooterSystem shooterSystem, FiringAlgorithm firingAlgorithm, SpeedConverter speedConverter, Vector2 target) {
        mShooterSystem = shooterSystem;
        mFiringAlgorithm = firingAlgorithm;
        mSpeedConverter = speedConverter;
        mTarget = target;

        requires(mShooterSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // calculate speed in meters per second according to the algorithm
        double speed = mFiringAlgorithm.calculateInitialSpeed(mTarget);
        // convert speed from meters per second to -1 -> 1 speeds we use in the robot.
        speed = mSpeedConverter.convertToRobotSpeeds(speed);

        mShooterSystem.shoot(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean wasInterrupted) {
        mShooterSystem.stop();
    }
}
