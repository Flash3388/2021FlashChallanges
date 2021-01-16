package frc.team3388.robot;

import com.flash3388.flashlib.scheduling.actions.ActionBase;
import frc.team3388.robot.subsystems.ElevatorSystem;

public class RaiseElevator extends ActionBase {

    private final ElevatorSystem mElevatorSystem;
    private int m

    public RaiseElevator(ElevatorSystem elevatorSystem) {
        mElevatorSystem = elevatorSystem;

        requires(mElevatorSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean wasInterrupted) {

    }
}
