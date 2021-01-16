package frc.team3388.robot;

import com.jmath.vectors.Vector2;

public class FiringAlgorithm {

    private static final double G_ACCELERATION = 9.8;

    private final double mFiringAngle;
    private final Vector2 mInitialPosition;

    public FiringAlgorithm(double firingAngle, Vector2 initialPosition) {
        mFiringAngle = firingAngle;
        mInitialPosition = initialPosition;
    }

    public double calculateInitialSpeed(Vector2 target) {
        return calculateInitialSpeed(target, mInitialPosition, mFiringAngle);
    }

    private double calculateInitialSpeed(Vector2 target, Vector2 initialPos, double firingAngle) {
        // y = y0 + v0y * t + 0.5a(t^2)
        // x = x0 + v0x * t

        // tan(al) * (x - x0) = y - y0 - 0.5at^2
        // t = sqrt((tan(al) * (x - x0) - y + y0) / -0.5a)
        // v0 = (xt - x0) / t
        firingAngle = Math.toRadians(firingAngle);
        double t = Math.sqrt((Math.tan(firingAngle) * (target.x() - initialPos.x()) - target.y() + initialPos.y())
                / -0.5 * G_ACCELERATION);
        return (target.x() - initialPos.x()) / t;
    }
}
