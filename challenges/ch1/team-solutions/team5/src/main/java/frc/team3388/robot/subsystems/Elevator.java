package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;

public class Elevator extends Subsystem {

    private int currentLevel = 1;

    private DigitalInput sensor1;
    private DigitalInput sensor2;
    private DigitalInput sensor3;
    private DigitalInput sensor4;
    private DigitalInput sensor5;

    private SpeedController motorControllers;

    public Elevator(DigitalInput sensor1, DigitalInput sensor2, DigitalInput sensor3,
                    DigitalInput sensor4 ,DigitalInput sensor5 , SpeedController motorControllers) {
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.sensor3 = sensor3;
        this.sensor4 = sensor4;
        this.sensor5 = sensor5;
        this.motorControllers = motorControllers;
    }

    public boolean hasReached(DigitalInput sensor) {
        return sensor.get();
    }

    public void levelUp() { currentLevel++; }
    public void levelDown() { currentLevel--; }

    public DigitalInput getLowerLevelSensor() {
        if(currentLevel == 2) return sensor1;
        else if(currentLevel == 3) return sensor2;
        else if(currentLevel == 4) return sensor3;
        else if(currentLevel == 5) return sensor4;
        else return null;
    }

    public DigitalInput getUpperLevelSensor() {
        if(currentLevel == 1) return sensor2;
        else if(currentLevel == 2) return sensor3;
        else if(currentLevel == 3) return sensor4;
        else if(currentLevel == 4) return sensor5;
        else return null;
    }

    public void move(double speed) {
        motorControllers.set(speed);
    }

    public void stop() {
        motorControllers.stop();
    }
}
