package frc.robot;

import com.frcteam3255.preferences.SN_BooleanPreference;
import com.frcteam3255.preferences.SN_DoublePreference;

public class RobotPreferences {

  public static final boolean useNetworkTables = false;

  public static final class prefDrivetrain {

    public static final SN_DoublePreference driveF = new SN_DoublePreference("driveF", 0.045);
    public static final SN_DoublePreference driveP = new SN_DoublePreference("driveP", 0.1);
    public static final SN_DoublePreference driveI = new SN_DoublePreference("driveI", 0.0);
    public static final SN_DoublePreference driveD = new SN_DoublePreference("driveD", 1.0);

    public static final SN_DoublePreference steerP = new SN_DoublePreference("steerP", 0.3);
    public static final SN_DoublePreference steerI = new SN_DoublePreference("steerI", 0.0);
    public static final SN_DoublePreference steerD = new SN_DoublePreference("steerD", 6.0);

    // percent of max module speed that is required for the module to steer
    // (a stopped wheel shouldn't steer)
    public static final SN_DoublePreference percentToSteer = new SN_DoublePreference("percentToSteer", 0.01);

    public static final SN_BooleanPreference isDriveOpenLoop = new SN_BooleanPreference("isDriveOpenLoop", false);

    // max translational speed in feet per second while driving using a controller
    // 16.3 FPS is maximum due to gearing
    public static final SN_DoublePreference driveSpeed = new SN_DoublePreference("driveSpeed", 16.3);

    // max rotational speed in degrees per second while driving using a controller
    // 943.751 DPS is maximum due to gearing and robot size
    public static final SN_DoublePreference turnSpeed = new SN_DoublePreference("turnSpeed", 360);

    // Value to multiply with translation velocity when trigger is all the way held
    // down.
    public static final SN_DoublePreference triggerValue = new SN_DoublePreference("triggerValue", .2);

  }

  public static final class prefIntake {
    public static final SN_DoublePreference colorMatcherConfidence = new SN_DoublePreference("colorMatcherConfidence",
        0.95);
    // TODO: Find what proximity is needed for the sensor
    public static final SN_DoublePreference gamePieceProximity = new SN_DoublePreference("gamePieceProximity", 100);
  }

  public static final class prefVision {

    public static final SN_DoublePreference measurementStdDevsFeet = new SN_DoublePreference(
        "measurementStdDevsFeet", 1);
    public static final SN_DoublePreference measurementStdDevsDegrees = new SN_DoublePreference(
        "measurementStdDevsDegrees", 10);
  }

  public static final class prefCollector {

    public static final SN_DoublePreference rollerSpeed = new SN_DoublePreference("rollerSpeed", 0.2);
    public static final SN_DoublePreference startingConfigPivotAngle = new SN_DoublePreference(
        "startingConfigPivotAngle",
        0);
    public static final SN_DoublePreference rollerHeightPivotAngle = new SN_DoublePreference("rollerHeightPivotAngle",
        90);
    public static final SN_DoublePreference climbPivotAngle = new SN_DoublePreference("climbPivotAngle", 180);

    public static final SN_DoublePreference collectorP = new SN_DoublePreference("collectorP", 0.09);
    public static final SN_DoublePreference collectorI = new SN_DoublePreference("collectorI", 0);
    public static final SN_DoublePreference collectorD = new SN_DoublePreference("collectorD", 0);

    public static final SN_DoublePreference collectorAllowableClosedLoopErrorDegrees = new SN_DoublePreference(
        "collectorAlowableClosedLoopErrorDegrees", 0.0001);
    public static final SN_DoublePreference collectorClosedLoopPeakOutput = new SN_DoublePreference(
        "collectorClosedLoopPeakOutput", 0.25);

    public static final SN_BooleanPreference collectorForwardSoftLimitEnable = new SN_BooleanPreference(
        "collectorForwardSoftLimitEnable", true);
    public static final SN_BooleanPreference collectorReverseSoftLimitEnable = new SN_BooleanPreference(
        "collectorReverseSoftLimitEnable", true);

  }

  public static final class prefArm {
    public static final SN_DoublePreference shoulderArbitraryFeedForward = new SN_DoublePreference(
        "shoulderArbitraryFeedForward", 0);
    public static final SN_DoublePreference shoulderP = new SN_DoublePreference("shoulderP", 0.1);
    public static final SN_DoublePreference shoulderI = new SN_DoublePreference("shoulderI", 0);
    public static final SN_DoublePreference shoulderD = new SN_DoublePreference("shoulderD", 0);
    public static final SN_DoublePreference shoulderMaxSpeed = new SN_DoublePreference("shoulderMaxSpeed", .2);
    public static final SN_DoublePreference shoulderTolerance = new SN_DoublePreference("shoulderTolerance", 3);

    public static final SN_DoublePreference elbowArbitraryFeedForward = new SN_DoublePreference(
        "elbowArbitraryFeedForward", 0);
    public static final SN_DoublePreference elbowP = new SN_DoublePreference("elbowP", 0.1);
    public static final SN_DoublePreference elbowI = new SN_DoublePreference("elbowI", 0);
    public static final SN_DoublePreference elbowD = new SN_DoublePreference("elbowD", 0);
    public static final SN_DoublePreference elbowMaxSpeed = new SN_DoublePreference("elbowMaxSpeed", .2);
    public static final SN_DoublePreference elbowTolerance = new SN_DoublePreference("elbowTolerance", 3);

    public static final SN_DoublePreference shoulderPreset = new SN_DoublePreference("shoulderPreset", 0);
    public static final SN_DoublePreference elbowPreset = new SN_DoublePreference("elbowPreset", 0);
  }
}
