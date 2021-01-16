package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.RoboRio;
import com.flash3388.flashlib.hid.HidChannel;

public class RobotMap {

    public static final int ELEVATOR = 4;
    public static final int[] STEP_SENSORS = {1, 2, 3, 4, 5};

    public static final HidChannel JOYSTICK = RoboRio.newHidChannel(2);
}
