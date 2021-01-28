package frc.team3388.robot.actions;

import com.flash3388.flashlib.scheduling.actions.ActionBase;
import frc.team3388.robot.subsystems.Cannon;

public class ShootingAction extends ActionBase {

    private Cannon cannon;

    private double x;

    public ShootingAction(Cannon cannon, double x) {
        this.cannon = cannon;
        this.x = x;

        requires(cannon);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        cannon.shoot(x);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean wasInterrupted) {
        cannon.stop();
    }
}
