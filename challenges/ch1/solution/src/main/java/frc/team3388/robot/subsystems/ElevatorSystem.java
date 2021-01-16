package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;

import java.util.List;

public class ElevatorSystem extends Subsystem {

    private static final double RAISE_SPEED = 0.6;
    private static final double LOWER_SPEED = -0.3;

    private final SpeedController mController;
    private final List<DigitalInput> mStepSensors;
    private int mCurrentStep;

    public ElevatorSystem(SpeedController controller, List<DigitalInput> stepSensors) {
        mController = controller;
        mStepSensors = stepSensors;
        mCurrentStep = 0;
    }

    public boolean isAtStep(int step) {
        return mStepSensors.get(step).get();
    }

    public void raise() {
        if (mCurrentStep == mStepSensors.size() - 1) {
            return;
        }
        if (isAtStep(mCurrentStep + 1)) {
            mCurrentStep++;
            if (mCurrentStep == mStepSensors.size() - 1) {
                return;
            }
        }

        mController.set(RAISE_SPEED);
    }

    public void lower() {
        if (mCurrentStep == 0) {
            return;
        }
        if (isAtStep(mCurrentStep - 1)) {
            mCurrentStep--;
            if (mCurrentStep == 0) {
                return;
            }
        }

        mController.set(LOWER_SPEED);
    }

    public void stop() {
        mController.stop();
    }
}
