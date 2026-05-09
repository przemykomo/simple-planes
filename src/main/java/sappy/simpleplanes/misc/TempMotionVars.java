package sappy.simpleplanes.misc;

public class TempMotionVars {
    public float moveForward; // TODO: move to HelicopterEntity?
    public double turnThreshold;
    public float moveStrafing;
    public double maxSpeed;
    public double maxPushSpeed;
    public double takeOffSpeed;
    public float maxLift;
    public double liftFactor;
    public double gravity;
    public double drag;
    public double dragMul;
    public double dragQuad;
    public float push;
    public float groundPush;
    public float passiveEnginePush;
    public float motionToRotation;
    public float pitchToMotion;
    public float yawMultiplayer;

    public TempMotionVars() {
        reset();
    }

    public void reset() {
        moveForward = 0;
        turnThreshold = 0;
        moveStrafing = 0;
        maxSpeed = 3;
        takeOffSpeed = 0.3;
        maxLift = 2;
        liftFactor = 10;
        gravity = -0.03;
        drag = 0.001;
        dragMul = 0.0005;
        dragQuad = 0.001;
        push = 0.0f;
        groundPush = 0.01f;
        passiveEnginePush = 0.025f;
        motionToRotation = 0.05f;
        pitchToMotion = 0.2f;
        yawMultiplayer = 0.5f;
    }
}
