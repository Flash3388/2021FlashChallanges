package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.scheduling.Subsystem;

public class ShooterSystem extends Subsystem {

    private final SpeedController mController;

    public ShooterSystem(SpeedController controller) {
        mController = controller;
    }

    public void shoot(double speed) {
        mController.set(speed);
    }

    public void stop() {
        mController.stop();
    }
}
