package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.io.devices.SpeedControllers;
import com.flash3388.flashlib.hid.Joystick;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team3388.robot.actions.LowerElevator;
import frc.team3388.robot.actions.RaiseElevator;
import frc.team3388.robot.subsystems.ElevatorSystem;

import java.util.ArrayList;
import java.util.List;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private final ElevatorSystem mElevatorSystem;
    private final Joystick mJoystick;

    public Robot(FrcRobotControl robotControl) {
        super(robotControl);

        List<DigitalInput> stepSensors = new ArrayList<>();
        for (int sensorConnection : RobotMap.STEP_SENSORS) {
            stepSensors.add(new DigitalInput(sensorConnection));
        }

        mElevatorSystem = new ElevatorSystem(
                new SpeedControllers()
                        .add(new WPI_TalonSRX(RobotMap.ELEVATOR))
                        .add(new WPI_TalonSRX(RobotMap.ELEVATOR2))
                    .build(),
                stepSensors
        );

        mJoystick = getHidInterface().newJoystick(RobotMap.JOYSTICK);
        mJoystick.getButton(2).whenActive(new RaiseElevator(mElevatorSystem));
        mJoystick.getButton(3).whenActive(new LowerElevator(mElevatorSystem));
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
