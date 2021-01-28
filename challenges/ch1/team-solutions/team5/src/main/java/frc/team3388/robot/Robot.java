package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.base.iterative.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.io.devices.SpeedControllers;
import com.flash3388.flashlib.hid.XboxButton;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;
import edu.wpi.first.wpilibj.DigitalInput;
import com.flash3388.flashlib.hid.XboxController;
import frc.team3388.robot.actions.LowerElevatorAction;
import frc.team3388.robot.actions.RaiseElevatorAction;
import frc.team3388.robot.subsystems.Elevator;

public class Robot extends DelegatingRobotControl implements IterativeFrcRobot {

    private Elevator elevator;

    private XboxController xbox;

    public Robot(FrcRobotControl robotControl) {
        super(robotControl);

        elevator = new Elevator(new DigitalInput(RobotMap.SENSOR1),
                new DigitalInput(RobotMap.SENSOR2),
                new DigitalInput(RobotMap.SENSOR3),
                new DigitalInput(RobotMap.SENSOR4),
                new DigitalInput(RobotMap.SENSOR5),
                new SpeedControllers()
                        .add(new WPI_TalonSRX(RobotMap.FIRSTMOTOR))
                        .add(new WPI_TalonSRX(RobotMap.SECONDMOTOR)).build());

        xbox = getHidInterface().newXboxController(RobotMap.XBOX);

        xbox.getButton(XboxButton.X).whenActive(new RaiseElevatorAction(elevator, 1));
        xbox.getButton(XboxButton.B).whenActive(new LowerElevatorAction(elevator, -1));
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
