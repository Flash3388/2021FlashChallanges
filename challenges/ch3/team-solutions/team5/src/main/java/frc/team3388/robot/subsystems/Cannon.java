package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.scheduling.Subsystem;

public class Cannon extends Subsystem {

    private SpeedController controller;

    public Cannon(SpeedController controller) {
        this.controller = controller;
    }

    public void shoot(double x) {
        double sina = Math.sin(25);
        double cosa = Math.cos(25);
        double g = 9.81;
        double y0 = 0.5;
        double firstArg = (0.5 * g * x * x) / y0;
        double secondArg = (0.5 * g * x * cosa) / sina;
        double squaredArg = (firstArg + secondArg) / Math.pow(cosa, 2);
        double v0 = Math.sqrt(squaredArg) / 3;
        controller.set(v0);
    }

    public void stop() {
        controller.stop();
    }
}
