package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.io.devices.SpeedControllers;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import com.flash3388.flashlib.scheduling.actions.Action;
import com.jmath.vectors.Vector2;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team3388.robot.actions.ShootAtAction;
import frc.team3388.robot.subsystems.ShooterSystem;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    // The height of our shooter system of the robot.
    // This is the initial height of the shooting algorithm
    private static final double SHOOTER_HEIGHT_M = 0.5;
    // The firing angle of the shooter.
    private static final double SHOOTER_FIRING_ANGLE_DEG = 25;
    // The maximum speed of the shooter. Used for speed conversion.
    private static final double SHOOTER_MAX_SPEED_METERS_SECOND = 2;

    private final ShooterSystem mShooterSystem;
    private final FiringAlgorithm mFiringAlgorithm;
    private final SpeedConverter mSpeedConverter;

    public Robot(FrcRobotControl robotControl) {
        super(robotControl);

        mShooterSystem = new ShooterSystem(
                new SpeedControllers()
                    .add(new VictorSP(RobotMap.SHOOTER))
                    .build()
        );
        mFiringAlgorithm = new FiringAlgorithm(SHOOTER_FIRING_ANGLE_DEG,
                new Vector2(0, SHOOTER_HEIGHT_M));
        mSpeedConverter = new SpeedConverter(SHOOTER_MAX_SPEED_METERS_SECOND);
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {
        Action action = new ShootAtAction(mShooterSystem, mFiringAlgorithm, mSpeedConverter,
                new Vector2(5, 3));
        action.start();
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void robotStop() {

    }
}
