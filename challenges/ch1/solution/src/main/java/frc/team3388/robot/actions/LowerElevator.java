package frc.team3388.robot.actions;

import com.flash3388.flashlib.scheduling.actions.ActionBase;
import frc.team3388.robot.subsystems.ElevatorSystem;

public class LowerElevator extends ActionBase {

    private final ElevatorSystem mElevatorSystem;
    private int mCurrentStep;

    public LowerElevator(ElevatorSystem elevatorSystem) {
        mElevatorSystem = elevatorSystem;

        requires(mElevatorSystem);
    }

    @Override
    public void initialize() {
        mCurrentStep = mElevatorSystem.getCurrentStep();
    }

    @Override
    public void execute() {
        mElevatorSystem.lower();
    }

    @Override
    public boolean isFinished() {
        if (mCurrentStep == mElevatorSystem.getMinStep()) {
            return true;
        }
        return mElevatorSystem.isAtStep(mCurrentStep - 1);
    }

    @Override
    public void end(boolean wasInterrupted) {
        mElevatorSystem.stop();
    }
}
