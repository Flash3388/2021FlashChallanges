package frc.team3388.robot.actions;

import com.flash3388.flashlib.scheduling.actions.ActionBase;
import frc.team3388.robot.subsystems.Elevator;

public class LowerElevatorAction extends ActionBase {

    private Elevator elevator;

    private double speed;

    public LowerElevatorAction(Elevator elevator, double speed) {
        this.elevator = elevator;
        this.speed = speed;

        requires(elevator);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        elevator.move(speed);
    }

    @Override
    public boolean isFinished() {
        return elevator.hasReached(elevator.getLowerLevelSensor());
    }

    @Override
    public void end(boolean wasInterrupted) {
        elevator.stop();
        elevator.levelDown();
    }
}
